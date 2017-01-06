package com.dh.demo.userstate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;

/**
 * Created by yancai.liu on 2017/1/5.
 */

public class UserStateFragment extends BaseFragment {
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_userstate;
    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPkgUsageStats();
    }

    public static void getPkgUsageStats() {
        Log.d(TAG, "[getPkgUsageStats]");
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService", java.lang.String.class);
            Object oRemoteService = mGetService.invoke(null, "usagestats");

            // IUsageStats oIUsageStats =
            // IUsageStats.Stub.asInterface(oRemoteService)
            Class<?> cStub = Class.forName("com.android.internal.app.IUsageStats$Stub");
            Method mUsageStatsService = cStub.getMethod("asInterface", android.os.IBinder.class);
            Object oIUsageStats = mUsageStatsService.invoke(null, oRemoteService);

            // PkgUsageStats[] oPkgUsageStatsArray =
            // mUsageStatsService.getAllPkgUsageStats();
            Class<?> cIUsageStatus = Class.forName("com.android.internal.app.IUsageStats");
            Method mGetAllPkgUsageStats = cIUsageStatus.getMethod("getAllPkgUsageStats", (Class[]) null);
            Object[] oPkgUsageStatsArray = (Object[]) mGetAllPkgUsageStats.invoke(oIUsageStats, (Object[]) null);
            Log.d(TAG, "[getPkgUsageStats] oPkgUsageStatsArray = " + oPkgUsageStatsArray);

            Class<?> cPkgUsageStats = Class.forName("com.android.internal.os.PkgUsageStats");

            StringBuffer sb = new StringBuffer();
            sb.append("nerver used : ");
            for (Object pkgUsageStats : oPkgUsageStatsArray) {
                // get pkgUsageStats.packageName, pkgUsageStats.launchCount,
                // pkgUsageStats.usageTime
                String packageName = (String) cPkgUsageStats.getDeclaredField("packageName").get(pkgUsageStats);
                int launchCount = cPkgUsageStats.getDeclaredField("launchCount").getInt(pkgUsageStats);
                long usageTime = cPkgUsageStats.getDeclaredField("usageTime").getLong(pkgUsageStats);
                if (launchCount > 0)
                    Log.d(TAG,
                            "[getPkgUsageStats] " + packageName + "  count: " + launchCount + "  time:  " + usageTime);
                else {
                    sb.append(packageName + "; ");
                }
            }
            Log.d(TAG, "[getPkgUsageStats] " + sb.toString());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
