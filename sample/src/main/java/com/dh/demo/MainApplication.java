package com.dh.demo;


import android.app.Application;

import com.dh.demo.hook.HookUtil;

/**
 * Created by yancai.liu on 2016/12/23.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // UpdateSdkManager.init(getApplicationContext());

        HookUtil hookUtil = new HookUtil(null, this);
        hookUtil.hookAms();
    }
}