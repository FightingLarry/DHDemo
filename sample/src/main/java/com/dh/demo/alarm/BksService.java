package com.dh.demo.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yancai.liu on 2016/12/21.
 */

public class BksService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.w("AlarmTag", intent.getAction());

        return super.onStartCommand(intent, flags, startId);
    }
}
