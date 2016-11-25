package com.tcl.tasklinedemo;

import android.util.Log;

import com.tcl.taskline.Task;

/**
 * Created by yancai.liu on 2016/11/25.
 */

public class UpdateTask extends Task {

    private static final String TAG = UpdateTask.class.getSimpleName();

    private int index;

    public UpdateTask(int i) {
        index = i;
    }

    @Override
    public void run() {
        super.run();

        try {
            Log.i(TAG, index + ":start");
            int time = (int) (Math.random() * 2000);
            Thread.sleep(time);
            Log.i(TAG, index + ":end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
