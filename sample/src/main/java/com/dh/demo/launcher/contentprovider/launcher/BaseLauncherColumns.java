package com.dh.demo.launcher.contentprovider.launcher;

/**
 * Created by yancai.liu on 2016/7/19.
 */

public interface BaseLauncherColumns extends ChangeLogColumns {
    /**
     * Descriptive name of the gesture that can be displayed to the user.
     * <P>
     * Type: TEXT
     * </P>
     */
    static final String TITLE = "title";

    /**
     * The Intent URL of the gesture, describing what it points to. This value is given to
     * {@link android.content.Intent#parseUri(String, int)} to create an Intent that can be
     * launched.
     * <P>
     * Type: TEXT
     * </P>
     */
    static final String INTENT = "intent";

    /**
     * The type of the gesture
     *
     * <P>
     * Type: INTEGER
     * </P>
     */
    static final String ITEM_TYPE = "itemType";

    /**
     * The gesture is an application
     */
    static final int ITEM_TYPE_APPLICATION = 0;

    /**
     * The gesture is an application created shortcut
     */
    static final int ITEM_TYPE_SHORTCUT = 1;

    /**
     * The icon type.
     * <P>
     * Type: INTEGER
     * </P>
     */
    static final String ICON_TYPE = "iconType";

    /**
     * The icon is a resource identified by a package name and an integer id.
     */
    static final int ICON_TYPE_RESOURCE = 0;

    /**
     * The icon is a bitmap.
     */
    static final int ICON_TYPE_BITMAP = 1;

    /**
     * The icon package name, if icon type is ICON_TYPE_RESOURCE.
     * <P>
     * Type: TEXT
     * </P>
     */
    static final String ICON_PACKAGE = "iconPackage";

    /**
     * The icon resource id, if icon type is ICON_TYPE_RESOURCE.
     * <P>
     * Type: TEXT
     * </P>
     */
    static final String ICON_RESOURCE = "iconResource";

    /**
     * The custom icon bitmap, if icon type is ICON_TYPE_BITMAP.
     * <P>
     * Type: BLOB
     * </P>
     */
    static final String ICON = "icon";
}