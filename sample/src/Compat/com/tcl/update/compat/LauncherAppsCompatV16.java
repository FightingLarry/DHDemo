/*
 * Copyright (C) 2014 The Android Open Source Project
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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Version of {@link LauncherAppsCompat} for devices with API level 16. Devices Pre-L don't support
 * multiple profiles in one launcher so user parameters are ignored and all methods operate on the
 * current user.
 */
public class LauncherAppsCompatV16 extends LauncherAppsCompat {

    private PackageManager mPm;
    private Context mContext;

    LauncherAppsCompatV16(Context context) {
        mPm = context.getPackageManager();
        mContext = context;
    }

    public List<LauncherActivityInfoCompat> getActivityList(String packageName, UserHandleCompat user) {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mainIntent.setPackage(packageName);
        List<ResolveInfo> infos = mPm.queryIntentActivities(mainIntent, 0);
        List<LauncherActivityInfoCompat> list = new ArrayList<LauncherActivityInfoCompat>(infos.size());
        for (ResolveInfo info : infos) {
            list.add(new LauncherActivityInfoCompatV16(mContext, info));
        }
        return list;
    }

}
