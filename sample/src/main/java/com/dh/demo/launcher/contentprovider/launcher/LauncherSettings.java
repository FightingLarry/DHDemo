package com.dh.demo.launcher.contentprovider.launcher;

import android.net.Uri;

import com.dh.demo.launcher.contentprovider.BackupUtil;

/**
 * Created by yancai.liu on 2016/7/19.
 */

public class LauncherSettings {

    public static final class WorkspaceScreens implements ChangeLogColumns {
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + LauncherProvider.AUTHORITY + "/" + LauncherProvider.TABLE_WORKSPACE_SCREENS);

        public static final Uri CONTENT_BACKUP_URI =
                Uri.parse("content://" + LauncherProvider.AUTHORITY + "/" + BackupUtil.BACKUP_WORKSPACE_SCREEN);

        public static final String SCREEN_RANK = "screenRank";

    }


    public static class Favorites implements BaseLauncherColumns {

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + LauncherProvider.AUTHORITY + "/" + LauncherProvider.TABLE_FAVORITES);

        public static final Uri CONTENT_BACKUP_URI =
                Uri.parse("content://" + LauncherProvider.AUTHORITY + "/" + BackupUtil.BACKUP_FAVORITES);

        /**
         * The container holding the favorite
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String CONTAINER = "container";

        /**
         * The icon is a resource identified by a package name and an integer id.
         */
        public static final int CONTAINER_DESKTOP = -100;
        public static final int CONTAINER_HOTSEAT = -101;

        public static final String containerToString(int container) {
            switch (container) {
                case CONTAINER_DESKTOP:
                    return "desktop";
                case CONTAINER_HOTSEAT:
                    return "hotseat";
                default:
                    return String.valueOf(container);
            }
        }

        /**
         * The screen holding the favorite (if container is CONTAINER_DESKTOP)
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String SCREEN = "screen";

        /**
         * The X coordinate of the cell holding the favorite (if container is CONTAINER_HOTSEAT or
         * CONTAINER_HOTSEAT)
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String CELLX = "cellX";

        /**
         * The Y coordinate of the cell holding the favorite (if container is CONTAINER_DESKTOP)
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String CELLY = "cellY";

        /**
         * The X span of the cell holding the favorite
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String SPANX = "spanX";

        /**
         * The Y span of the cell holding the favorite
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String SPANY = "spanY";

        /**
         * The profile id of the item in the cell.
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String PROFILE_ID = "profileId";

        /**
         * The favorite is a user created folder
         */
        public static final int ITEM_TYPE_FOLDER = 2;

        /**
         * The favorite is a live folder
         *
         * Note: live folders can no longer be added to Launcher, and any live folders which exist
         * within the launcher database will be ignored when loading. That said, these entries in
         * the database may still exist, and are not automatically stripped.
         */
        public static final int ITEM_TYPE_LIVE_FOLDER = 3;

        /**
         * The favorite is a widget
         */
        public static final int ITEM_TYPE_APPWIDGET = 4;

        /**
         * The favorite is a clock
         */
        public static final int ITEM_TYPE_WIDGET_CLOCK = 1000;

        /**
         * The favorite is a search widget
         */
        public static final int ITEM_TYPE_WIDGET_SEARCH = 1001;

        /**
         * The favorite is a photo frame
         */
        public static final int ITEM_TYPE_WIDGET_PHOTO_FRAME = 1002;

        /**
         * The appWidgetId of the widget
         *
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String APPWIDGET_ID = "appWidgetId";

        /**
         * The ComponentName of the widget provider
         *
         * <P>
         * Type: STRING
         * </P>
         */
        public static final String APPWIDGET_PROVIDER = "appWidgetProvider";

        /**
         * Indicates whether this favorite is an application-created shortcut or not. If the value
         * is 0, the favorite is not an application-created shortcut, if the value is 1, it is an
         * application-created shortcut.
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String IS_APP_SHORTCUT = "isAppShortcut";

        public static final String FOLDER_TYPE = "folderType";

        /**
         * The URI associated with the favorite. It is used, for instance, by live folders to find
         * the content provider.
         * <P>
         * Type: TEXT
         * </P>
         */
        public static final String URI = "uri";

        /**
         * The display mode if the item is a live folder.
         * <P>
         * Type: INTEGER
         * </P>
         *
         * @see android.provider.LiveFolders#DISPLAY_MODE_GRID
         * @see android.provider.LiveFolders#DISPLAY_MODE_LIST
         */
        public static final String DISPLAY_MODE = "displayMode";

        /**
         * Boolean indicating that his item was restored and not yet successfully bound.
         * <P>
         * Type: INTEGER
         * </P>
         */
        public static final String RESTORED = "restored";
    }
}
