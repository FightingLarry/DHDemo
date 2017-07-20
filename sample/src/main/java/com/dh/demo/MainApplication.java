package com.dh.demo;


import android.app.Application;

import com.dh.demo.hook.HookUtil;
import com.dh.demo.hook.HookPmsUtil;

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

        HookPmsUtil.hookPackageManager(this);
    }
}
