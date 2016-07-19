package com.dh.demo.launcher.reflect;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yancai.liu on 2016/7/19.
 */

public class ReflectUtil {

    private static final String TAG = "ReflectUtil";


    static public Object getField(String className, String fieldName) {
        try {
            Class<?> configClass = Class.forName(className);
            Field field = configClass.getField(fieldName);
            field.setAccessible(true);
            return field.get(null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get " + className + "." + fieldName + "\n" + e);
            e.printStackTrace();
        }
        return null;
    }

    static public int getIntField(String className, String fieldName, int def_val) {
        Object field = getField(className, fieldName);
        if (field != null) return (Integer) field;
        return def_val;
    }

    static public Method getMethod(Class<?> clazz, String methodName, Object... args) {
        try {
            Method method;
            if (args.length == 0)
                method = clazz.getMethod(methodName);
            else {
                Class<?>[] param = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    param[i] = args[i].getClass();
                }
                method = clazz.getMethod(methodName, param);
            }
            return method;
        } catch (Exception e) {
            Log.e(TAG, "Failed to get method " + clazz.getName() + "." + methodName + "()\n" + e);
            e.printStackTrace();
        }

        return null;
    }

    static public Object invoke(String className, String methodName, Object obj, Object... args) {
        try {
            Class<?> clazz = Class.forName(className);
            Method method = getMethod(clazz, methodName, args);
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception e) {
            Log.e(TAG, "Failed to invoke " + className + "." + methodName + "()\n" + e);
            e.printStackTrace();
        }

        return null;
    }

}
