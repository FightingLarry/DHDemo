package com.dh.demo.hook;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AMSBinderHookHandler implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    // 原始的Service对象 (IInterface)
    Object base;

    public AMSBinderHookHandler(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            // IClipboard.Stub.asInterface(base);
            this.base = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException("hooked failed!");
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // // 把剪切版的内容替换为 "you are hooked"
        // if ("getPrimaryClip".equals(method.getName())) {
        // Log.d(TAG, "hook getPrimaryClip");
        // return ClipData.newPlainText(null, "you are hooked");
        // }
        // // 欺骗系统,使之认为剪切版上一直有内容
        // if ("hasPrimaryClip".equals(method.getName())) {
        // return true;
        // }

        if ("checkPermission".equals(method.getName())) {
            Log.w("HookUtil", "checkPermission:PackageManager.PERMISSION_GRANTED");
            return PackageManager.PERMISSION_GRANTED;
        }
        if ("isGetTasksAllowed".equals(method.getName())) {
            Log.w("HookUtil", "isGetTasksAllowed:true");
            return true;
        }


        return method.invoke(base, args);
    }
}
