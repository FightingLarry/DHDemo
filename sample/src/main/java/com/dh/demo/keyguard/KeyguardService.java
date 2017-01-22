package com.dh.demo.keyguard;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class KeyguardService extends Service {

    private KeyguardManager mHelper;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mHelper = new KeyguardManager(this);

        mHelper.lock();

    }

    public void init(Context context) {

    }

    public void onDestroy() {
        super.onDestroy();
        mHelper.onDestroy();
    }

}
