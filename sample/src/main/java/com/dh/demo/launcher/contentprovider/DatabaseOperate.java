package com.dh.demo.launcher.contentprovider;


import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.dh.demo.BuildConfig;
import com.dh.demo.launcher.contentprovider.launcher.LauncherSettings;
import com.dh.demo.launcher.contentprovider.launcher.ProviderConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wenjun.zhong on 2016/5/19.
 */
public class DatabaseOperate {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "ModeChange";

    public static boolean backup(Context context) {
        if (context == null) {
            return false;
        }
        boolean result = true;
        try {
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            // step1：删除备份表Favorites
            ops.add(ContentProviderOperation.newDelete(LauncherSettings.Favorites.CONTENT_BACKUP_URI).build());
            // step2：备份 表Favorites
            List<ContentValues> contentValuesFavorites = queryFavorites(context, LauncherSettings.Favorites.CONTENT_URI);
            if (contentValuesFavorites != null && contentValuesFavorites.size() > 0) {
                for (ContentValues value : contentValuesFavorites) {
                    ops.add(ContentProviderOperation.newInsert(LauncherSettings.Favorites.CONTENT_BACKUP_URI)
                            .withValues(value).build());
                }
            }
            // step3：删除备份表WorkspaceScreens
            ops.add(ContentProviderOperation.newDelete(LauncherSettings.WorkspaceScreens.CONTENT_BACKUP_URI).build());
            // step4：备份 表WorkspaceScreens
            List<ContentValues> contentValuesWorkspaceScreens = queryWorkspaceScreens(context, LauncherSettings.WorkspaceScreens.CONTENT_URI);
            if (contentValuesWorkspaceScreens != null && contentValuesWorkspaceScreens.size() > 0) {
                for (ContentValues value : contentValuesWorkspaceScreens) {
                    ops.add(ContentProviderOperation.newInsert(LauncherSettings.WorkspaceScreens.CONTENT_BACKUP_URI)
                            .withValues(value).build());
                }
            }

            context.getContentResolver().
                    applyBatch(ProviderConfig.AUTHORITY, ops);

            query(context, LauncherSettings.Favorites.CONTENT_BACKUP_URI);

        } catch (SQLiteException e) {
            e.printStackTrace();
            result = false;
        } catch (RemoteException e) {
            e.printStackTrace();
            result = false;
        } catch (OperationApplicationException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }


    public static boolean restore(Context context) {
        if (context == null) {
            return false;
        }

        // 如果备份数据表不存在或为空，则退出
        if (isFavoritesEmpty(context)) {
            return true;
        }
        boolean result = true;
        try {

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            // step1：清除表Favorites
            ops.add(ContentProviderOperation.newDelete(LauncherSettings.Favorites.CONTENT_URI).build());
            // step2：还原备份表Favorites
            List<ContentValues> contentValuesFavorites = queryFavorites(context, LauncherSettings.Favorites.CONTENT_BACKUP_URI);
            if (contentValuesFavorites != null && contentValuesFavorites.size() > 0) {
                for (ContentValues value : contentValuesFavorites) {
                    ops.add(ContentProviderOperation.newInsert(LauncherSettings.Favorites.CONTENT_URI)
                            .withValues(value).build());
                }
            }
            // step3：删除备份表Favorites
            ops.add(ContentProviderOperation.newDelete(LauncherSettings.Favorites.CONTENT_BACKUP_URI).build());

            // step4：删除表WorkspaceScreens
            ops.add(ContentProviderOperation.newDelete(LauncherSettings.WorkspaceScreens.CONTENT_URI).build());
            // step5：还原表WorkspaceScreens
            List<ContentValues> contentValuesWorkspaceScreens = queryWorkspaceScreens(context, LauncherSettings.WorkspaceScreens.CONTENT_BACKUP_URI);
            if (contentValuesWorkspaceScreens != null && contentValuesWorkspaceScreens.size() > 0) {
                for (ContentValues value : contentValuesWorkspaceScreens) {
                    ops.add(ContentProviderOperation.newInsert(LauncherSettings.WorkspaceScreens.CONTENT_URI)
                            .withValues(value).build());
                }
            }
            // step6：删除备份表WorkspaceScreens
            ops.add(ContentProviderOperation.newDelete(LauncherSettings.WorkspaceScreens.CONTENT_BACKUP_URI).build());

            context.getContentResolver().
                    applyBatch(ProviderConfig.AUTHORITY, ops);

        } catch (SQLiteException e) {
            e.printStackTrace();
            result = false;
        } catch (RemoteException e) {
            e.printStackTrace();
            result = false;
        } catch (OperationApplicationException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private static void query(Context context, Uri uri) {
        final ContentResolver cr = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = cr.query(uri,
                    new String[]{LauncherSettings.Favorites.INTENT, LauncherSettings.Favorites.PROFILE_ID},
                    LauncherSettings.Favorites.ITEM_TYPE + "=?",
                    new String[]{LauncherSettings.Favorites.ITEM_TYPE_APPLICATION + ""}, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String intentString =
                            cursor.getString(cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.INTENT));
                    long serialNumber =
                            cursor.getInt(cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.PROFILE_ID));
                    Log.d("yckk", intentString);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private static List<ContentValues> queryFavorites(Context context, Uri uri) {
        if (context == null) {
            return null;
        }
        final ContentResolver cr = context.getContentResolver();
        Cursor cursor = null;
        List<ContentValues> contentValues = new ArrayList();
        try {
            cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    final int idIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites._ID);
                    final int titleIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.TITLE);
                    final int intentIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.INTENT);
                    final int containerIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.CONTAINER);
                    final int screenIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.SCREEN);
                    final int cellXIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.CELLX);
                    final int cellYIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.CELLY);
                    final int spanXIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.SPANX);
                    final int spanYIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.SPANY);
                    final int itemTypeIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.ITEM_TYPE);
                    final int appWidgetIdIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.APPWIDGET_ID);
                    final int iconTypeIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.ICON_TYPE);
                    final int iconPackageIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.ICON_PACKAGE);
                    final int iconResourceIndex =
                            cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.ICON_RESOURCE);
//                    final int iconIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.ICON);
                    final int uriIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.URI);
                    final int displayModeIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.DISPLAY_MODE);
                    final int appWidgetProviderIndex =
                            cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.APPWIDGET_PROVIDER);
                    final int modifiedIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.MODIFIED);
                    final int restoredIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.RESTORED);
                    final int isAppShortcutIndex =
                            cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.IS_APP_SHORTCUT);
                    final int folderTypeIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.FOLDER_TYPE);
                    final int profileIdIndex = cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.PROFILE_ID);

                    ContentValues values = new ContentValues(cursor.getColumnCount());
                    values.put(LauncherSettings.Favorites._ID, cursor.getLong(idIndex));
                    values.put(LauncherSettings.Favorites.TITLE, cursor.getString(titleIndex));
                    values.put(LauncherSettings.Favorites.INTENT, cursor.getString(intentIndex));
                    values.put(LauncherSettings.Favorites.ICON_TYPE, cursor.getInt(iconTypeIndex));
//                    values.put(LauncherSettings.Favorites.ICON, cursor.getBlob(iconIndex));
                    values.put(LauncherSettings.Favorites.ICON_PACKAGE, cursor.getString(iconPackageIndex));
                    values.put(LauncherSettings.Favorites.ICON_RESOURCE, cursor.getString(iconResourceIndex));
                    values.put(LauncherSettings.Favorites.CONTAINER, cursor.getInt(containerIndex));
                    values.put(LauncherSettings.Favorites.ITEM_TYPE, cursor.getInt(itemTypeIndex));
                    values.put(LauncherSettings.Favorites.SCREEN, cursor.getLong(screenIndex));
                    values.put(LauncherSettings.Favorites.CELLX, cursor.getInt(cellXIndex));
                    values.put(LauncherSettings.Favorites.CELLY, cursor.getInt(cellYIndex));
                    values.put(LauncherSettings.Favorites.URI, cursor.getString(uriIndex));
                    values.put(LauncherSettings.Favorites.DISPLAY_MODE, cursor.getInt(displayModeIndex));
                    values.put(LauncherSettings.Favorites.SPANX, cursor.getInt(spanXIndex));
                    values.put(LauncherSettings.Favorites.SPANY, cursor.getInt(spanYIndex));
                    values.put(LauncherSettings.Favorites.APPWIDGET_ID, cursor.getInt(appWidgetIdIndex));
                    values.put(LauncherSettings.Favorites.APPWIDGET_PROVIDER, cursor.getString(appWidgetProviderIndex));
                    values.put(LauncherSettings.Favorites.MODIFIED, cursor.getLong(modifiedIndex));
                    values.put(LauncherSettings.Favorites.RESTORED, cursor.getLong(restoredIndex));
                    values.put(LauncherSettings.Favorites.IS_APP_SHORTCUT, cursor.getInt(isAppShortcutIndex));
                    values.put(LauncherSettings.Favorites.FOLDER_TYPE, cursor.getString(folderTypeIndex));
                    values.put(LauncherSettings.Favorites.PROFILE_ID, cursor.getLong(profileIdIndex));

                    contentValues.add(values);
                }
            }
            return contentValues;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private static List<ContentValues> queryWorkspaceScreens(Context context, Uri uri) {
        if (context == null) {
            return null;
        }
        final ContentResolver cr = context.getContentResolver();
        Cursor cursor = null;
        List<ContentValues> contentValues = new ArrayList();
        try {
            cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    final int idIndex = cursor.getColumnIndexOrThrow(LauncherSettings.WorkspaceScreens._ID);
                    final int screenRankIndex = cursor.getColumnIndexOrThrow(LauncherSettings.WorkspaceScreens.SCREEN_RANK);
                    final int modifiedIndex = cursor.getColumnIndexOrThrow(LauncherSettings.WorkspaceScreens.MODIFIED);

                    ContentValues values = new ContentValues(cursor.getColumnCount());
                    values.put(LauncherSettings.WorkspaceScreens._ID, cursor.getLong(idIndex));
                    values.put(LauncherSettings.WorkspaceScreens.SCREEN_RANK, cursor.getLong(screenRankIndex));
                    values.put(LauncherSettings.WorkspaceScreens.MODIFIED, cursor.getLong(modifiedIndex));

                    contentValues.add(values);
                }
            }
            return contentValues;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private static boolean isFavoritesEmpty(Context context) {
        if (context == null) {
            return true;
        }
        final ContentResolver cr = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = cr.query(LauncherSettings.Favorites.CONTENT_BACKUP_URI, new String[]{LauncherSettings.Favorites._ID}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return true;
    }


}
