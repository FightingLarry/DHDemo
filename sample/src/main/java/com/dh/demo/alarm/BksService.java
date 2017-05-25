package com.dh.demo.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by yancai.liu on 2016/12/21.
 */

public class BksService extends Service {
    private static final String TAG = "BksService";
    public static final String ACTION1 = "com.dh.demo.alarm.BksService.action1";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.w("AlarmTag", intent.getAction());

        if (TextUtils.isEmpty(intent.getAction()) && ACTION1.equals(intent.getAction())) {
            Log.d(TAG, String.format("%s is called", ACTION1));
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
