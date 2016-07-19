package com.dh.demo.launcher.reflect;

import android.util.Log;

import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;

/**
 * Created by yancai.liu on 2016/7/19.
 */

public class LauncherContentProviderManager {

    public static Object fangke() {
        try {
            return ReflectUtil.invoke("com.tcl.launcher3.mode.ModeManager", "onModeChange", 1);
        } catch (Exception e) {
            Log.e(TAG, "Failed to invoke manager fangke()\n" + e);
            e.printStackTrace();
        }
        return null;
    }

    public static Object jizhu() {
        try {
            return ReflectUtil.invoke("com.tcl.launcher3.mode.ModeManager", "onModeChange", 0);
        } catch (Exception e) {
            Log.e(TAG, "Failed to invoke manager jizhu()\n" + e);
            e.printStackTrace();
        }
        return null;
    }
}
