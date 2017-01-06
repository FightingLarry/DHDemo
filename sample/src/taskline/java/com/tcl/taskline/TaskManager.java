package com.tcl.taskline;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

/**
 * Created by yancai.liu on 2016/11/25.
 */

public class TaskManager {

    private static final HandlerThread sWorkerThread = new HandlerThread("task-loader");

    static {
        sWorkerThread.start();
    }

    private static final Handler sWorker = new Handler(sWorkerThread.getLooper());

    private DeferredHandler mHandler = new DeferredHandler();


    public static void runOnWorkerThread(Task t) {
        if (sWorkerThread.getThreadId() == Process.myTid()) {
            t.run();
        } else {
            // If we are not on the worker thread, then post to the worker
            // handler
            sWorker.post(t);
        }
    }

    private void runOnMainThread(Task t) {
        if (sWorkerThread.getThreadId() == Process.myTid()) {
            // If we are on the worker thread, post onto the main handler
            mHandler.post(t);
        } else {
            t.run();
        }
    }

}
