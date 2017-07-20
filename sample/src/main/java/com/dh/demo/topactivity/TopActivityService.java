package com.dh.demo.topactivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * Created by Larry on 2017/7/19.
 */

public class TopActivityService extends Service {

    public static final String TAG = "TopActivityService";
    public static final String ACTION_TOP_ACTIVITY = "ACTION_TOP_ACTIVITY";

    private static final int DISPLAY_TASKS = 20;
    private static final int MAX_TASKS = DISPLAY_TASKS + 1; // allow extra for non-apps

    private static HandlerThread handlerThread = new HandlerThread("TopActivityService");
    static {
        handlerThread.start();
    }

    private static Handler mWorkerThread = new Handler(handlerThread.getLooper());


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        handlerThread.quit();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // 1.通过ActivityManager，>=21后，无法查看其它App的top activity。系统权限可以。
            TopActivityInfo info = getTopActivity(getApplicationContext());
            Log.e(TAG, info.packageName + "/" + info.topActivityName);

            // Hook 通过ActivityManager#getRunningAppProcesses()失败

            // 2.通过ActivityThread，只能查看本App的top activity
            Activity top = getRunningActivity();
            Log.e(TAG, "反射ActivityThread：" + (top == null ? "activity is null" : top.getComponentName()));

            // 3.Usage，需要android.permission.PACKAGE_USAGE_STATS权限，权限需要通过Intent让用户手动打开。
            Log.e(TAG, "Usage：" + getTopAppName(getApplicationContext()));

            // Hook Usage

            // 4.监听AccessibilityService，需要权限
            // public void onAccessibilityEvent(AccessibilityEvent event) {
            // if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            // if(SPHelper.isShowWindow(this)){
            // TasksWindow.show(this, event.getPackageName() + "\n" + event.getClassName());
            // }
            // }
            // }

            // 5.反射com.android.systemui.recent RecentsActivity,调用AMS的getRecentTasks，还是验证了uid
            getInfoFromRecentsActivity();



            mWorkerThread.postDelayed(this, 5000);
        }
    };

    private void getInfoFromRecentsActivity() {
        final PackageManager pm = getApplicationContext().getPackageManager();
        final ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RecentTaskInfo> recentTasks =
                am.getRecentTasks(MAX_TASKS, ActivityManager.RECENT_WITH_EXCLUDED);
        int numTasks = recentTasks.size();

        Log.e(TAG, "getInfoFromRecentsActivity:" + numTasks);
        ActivityInfo homeInfo =
                new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null && !TextUtils.isEmpty(intent.getAction())) {
            return super.onStartCommand(intent, flags, startId);
        }

        if (intent.getAction().equals(ACTION_TOP_ACTIVITY)) {
            mWorkerThread.postDelayed(mRunnable, 5000);
        }



        return super.onStartCommand(intent, flags, startId);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Activity getRunningActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            ArrayMap activities = (ArrayMap) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TopActivityInfo getTopActivity(Context context) {
        TopActivityInfo info = new TopActivityInfo();
        ActivityManager manager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        if (Build.VERSION.SDK_INT >= 21) {
            List<ActivityManager.RunningAppProcessInfo> pis = manager.getRunningAppProcesses();
            ActivityManager.RunningAppProcessInfo topAppProcess = pis.get(0);
            if (topAppProcess != null
                    && topAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                info.packageName = topAppProcess.processName;
                info.topActivityName = "";
            }
        } else {
            // getRunningTasks() is deprecated since API Level 21 (Android 5.0)
            List localList = manager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo localRunningTaskInfo = (ActivityManager.RunningTaskInfo) localList.get(0);
            info.packageName = localRunningTaskInfo.topActivity.getPackageName();
            info.topActivityName = localRunningTaskInfo.topActivity.getClassName();
        }
        return info;
    }


    public static String getTopAppName(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String strName = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                strName = getLollipopFGAppPackageName(context);
            } else {
                strName = mActivityManager.getRunningTasks(1).get(0).topActivity.getClassName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strName;
    }


    /**
     * 需要android.permission.PACKAGE_USAGE_STATS权限，权限需要通过Intent让用户手动打开。
     * 
     * @param ctx
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String getLollipopFGAppPackageName(Context ctx) {

        try {
            UsageStatsManager usageStatsManager = (UsageStatsManager) ctx.getSystemService(Context.USAGE_STATS_SERVICE);
            long milliSecs = 60 * 1000;
            Date date = new Date();
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                    date.getTime() - milliSecs, date.getTime());
            if (queryUsageStats.size() > 0) {
                Log.i(TAG, "queryUsageStats size: " + queryUsageStats.size());
            }
            long recentTime = 0;
            String recentPkg = "";
            for (int i = 0; i < queryUsageStats.size(); i++) {
                UsageStats stats = queryUsageStats.get(i);
                if (i == 0 && !"org.pervacio.pvadiag".equals(stats.getPackageName())) {
                    Log.i(TAG, "PackageName: " + stats.getPackageName() + " " + stats.getLastTimeStamp());
                }
                if (stats.getLastTimeStamp() > recentTime) {
                    recentTime = stats.getLastTimeStamp();
                    recentPkg = stats.getPackageName();
                }
            }
            return recentPkg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

