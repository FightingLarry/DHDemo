/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.tcl.update.compat;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Cache of application icons. Icons can be made from any thread.
 */
public class IconCache {

    private static final String TAG = "Launcher.IconCache";

    private static final int INITIAL_ICON_CACHE_CAPACITY = 50;

    private static final String EMPTY_CLASS_NAME = ".";

    private static final boolean DEBUG = false;


    public static class CacheEntry {
        public Bitmap icon;
        public CharSequence title = "";
        public CharSequence contentDescription = "";
    }

    private final HashMap<UserHandleCompat, Bitmap> mDefaultIcons = new HashMap<>();

    private final Context mContext;
    private final PackageManager mPackageManager;
    final UserManagerCompat mUserManager;
    private final LauncherAppsCompat mLauncherApps;
    private final HashMap<ComponentKey, CacheEntry> mCache = new HashMap<>(INITIAL_ICON_CACHE_CAPACITY);
    private final int mIconDpi;

    private UserHandleCompat mUserHandleCompat;

    public IconCache(Context context) {
        mContext = context;
        mPackageManager = context.getPackageManager();
        mUserManager = UserManagerCompat.getInstance(mContext);
        mUserHandleCompat = UserHandleCompat.myUserHandle();
        mLauncherApps = LauncherAppsCompat.getInstance(mContext);

        DisplayMetrics dm = new DisplayMetrics();
        mIconDpi = dm.densityDpi;
    }

    public synchronized CacheEntry getIconCache(String pkgName) {
        ComponentName component = getComponent(pkgName);
        CacheEntry entry = cacheLocked(component, mUserHandleCompat);
        return entry;
    }


    private CacheEntry cacheLocked(ComponentName componentName, UserHandleCompat user) {
        ComponentKey cacheKey = new ComponentKey(componentName, user);
        CacheEntry entry = mCache.get(cacheKey);
        if (entry == null) {
            entry = new CacheEntry();
            mCache.put(cacheKey, entry);

            List<LauncherActivityInfoCompat> infos =
                    mLauncherApps.getActivityList(componentName.getPackageName(), user);
            LauncherActivityInfoCompat info = null;
            if (infos != null && infos.size() > 0) {
                info = infos.get(0);
            }
            if (info != null) {
                entry.icon = drawableToBitmap(info.getIcon(mIconDpi));
            } else {
                CacheEntry packageEntry = getEntryForPackageLocked(componentName.getPackageName(), user);
                if (packageEntry != null) {
                    if (DEBUG) Log.d(TAG, "using package default icon for " + componentName.toShortString());
                    entry.icon = packageEntry.icon;
                    entry.title = packageEntry.title;
                    entry.contentDescription = packageEntry.contentDescription;
                }
                if (entry.icon == null) {
                    if (DEBUG) Log.d(TAG, "using default icon for " + componentName.toShortString());
                    entry.icon = getDefaultIcon(user);
                }
            }
            if (TextUtils.isEmpty(entry.title) && info != null) {
                entry.title = info.getLabel();
                entry.contentDescription = mUserManager.getBadgedLabelForUser(entry.title, user);
            }
        }
        return entry;
    }

    Bitmap drawableToBitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;

        return bd.getBitmap();
    }

    private CacheEntry getEntryForPackageLocked(String packageName, UserHandleCompat user) {
        ComponentKey cacheKey = getPackageKey(packageName, user);
        CacheEntry entry = mCache.get(cacheKey);

        if (entry == null) {
            entry = new CacheEntry();
            boolean entryUpdated = true;
            try {
                int flags = UserHandleCompat.myUserHandle().equals(user) ? 0 : PackageManager.GET_UNINSTALLED_PACKAGES;
                PackageInfo info = mPackageManager.getPackageInfo(packageName, flags);
                ApplicationInfo appInfo = info.applicationInfo;
                if (appInfo == null) {
                    throw new NameNotFoundException("ApplicationInfo is null");
                }

                Bitmap icon = drawableToBitmap(appInfo.loadIcon(mPackageManager));
                entry.title = appInfo.loadLabel(mPackageManager);
                entry.contentDescription = mUserManager.getBadgedLabelForUser(entry.title, user);
                entry.icon = icon;

            } catch (NameNotFoundException e) {
                if (DEBUG) Log.d(TAG, "Application not installed " + packageName);
                entryUpdated = false;
            }
            // Only add a filled-out entry to the cache
            if (entryUpdated) {
                mCache.put(cacheKey, entry);
            }
        }
        return entry;
    }


    public synchronized Bitmap getDefaultIcon(UserHandleCompat user) {
        if (!mDefaultIcons.containsKey(user)) {
            mDefaultIcons.put(user, makeDefaultIcon(user));
        }
        return mDefaultIcons.get(user);
    }

    /**
     * Adds a default package entry in the cache. This entry is not persisted and will be removed
     * when the cache is flushed.
     */
    public synchronized void cachePackageInstallInfo(String packageName, UserHandleCompat user, Bitmap icon,
            CharSequence title) {
        removeFromMemCacheLocked(packageName, user);

        ComponentKey cacheKey = getPackageKey(packageName, user);
        CacheEntry entry = mCache.get(cacheKey);

        // For icon caching, do not go through DB. Just update the in-memory entry.
        if (entry == null) {
            entry = new CacheEntry();
            mCache.put(cacheKey, entry);
        }
        if (!TextUtils.isEmpty(title)) {
            entry.title = title;
        }
        if (icon != null) {
            entry.icon = icon;
        }
    }

    private static ComponentKey getPackageKey(String packageName, UserHandleCompat user) {
        ComponentName cn = new ComponentName(packageName, packageName + EMPTY_CLASS_NAME);
        return new ComponentKey(cn, user);
    }

    private static ComponentName getComponent(String packageName) {
        return new ComponentName(packageName, packageName + EMPTY_CLASS_NAME);
    }

    private Drawable getFullResDefaultActivityIcon() {
        return getFullResIcon(Resources.getSystem(), android.R.mipmap.sym_def_app_icon);
    }

    private Drawable getFullResIcon(Resources resources, int iconId) {
        Drawable d;
        try {
            d = resources.getDrawableForDensity(iconId, mIconDpi);
        } catch (Resources.NotFoundException e) {
            d = null;
        }

        return (d != null) ? d : getFullResDefaultActivityIcon();
    }


    private Bitmap makeDefaultIcon(UserHandleCompat user) {
        Drawable unbadged = getFullResDefaultActivityIcon();
        return drawableToBitmap(unbadged);
    }

    /**
     * Remove any records for the supplied ComponentName.
     */
    public synchronized void remove(ComponentName componentName, UserHandleCompat user) {
        mCache.remove(new ComponentKey(componentName, user));
    }

    /**
     * Remove any records for the supplied package name from memory.
     */
    private void removeFromMemCacheLocked(String packageName, UserHandleCompat user) {
        HashSet<ComponentKey> forDeletion = new HashSet<ComponentKey>();
        for (ComponentKey key : mCache.keySet()) {
            if (key.componentName.getPackageName().equals(packageName) && key.user.equals(user)) {
                forDeletion.add(key);
            }
        }
        for (ComponentKey condemned : forDeletion) {
            mCache.remove(condemned);
        }
    }


}
