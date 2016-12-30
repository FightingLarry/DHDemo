package com.dh.demo.defaultBrowser;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yancai.liu on 2016/12/29.
 */

public class BrowserUtils {

    public static String isDefaultBrowser(Context context) {

        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        ResolveInfo localResolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if ((localResolveInfo != null) && (localResolveInfo.activityInfo != null)
                && (!localResolveInfo.activityInfo.packageName.equals("android"))) {
            return localResolveInfo.activityInfo.packageName;
        }
        return "null";
    }

    public static void clearDefaultBrowser(Context context) {
        try {

            PackageManager packageManager = context.getPackageManager();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.google.com"));

            // 找出手机当前安装的所有浏览器程序
            List<ResolveInfo> resolveInfoList =
                    packageManager.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);

            int size = resolveInfoList.size();
            for (int i = 0; i < size; i++) {
                ActivityInfo activityInfo = resolveInfoList.get(i).activityInfo;
                String packageName = activityInfo.packageName;
                // 清除之前的默认设置
                packageManager.clearPackagePreferredActivities(packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultBrowser(Context context, ComponentName component) {
        try {

            PackageManager packageManager = context.getPackageManager();

            String str1 = "android.intent.category.DEFAULT";
            String str2 = "android.intent.category.BROWSABLE";
            String str3 = "android.intent.action.VIEW";

            // 设置默认项的必须参数之一,用户的操作符合该过滤器时,默认设置起效
            IntentFilter filter = new IntentFilter(str3);
            filter.addCategory(str1);
            filter.addCategory(str2);
            filter.addDataScheme("http");

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.google.com"));

            // 找出手机当前安装的所有浏览器程序
            List<ResolveInfo> resolveInfoList =
                    packageManager.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);

            int size = resolveInfoList.size();
            ComponentName[] arrayOfComponentName = new ComponentName[size];
            for (int i = 0; i < size; i++) {
                ActivityInfo activityInfo = resolveInfoList.get(i).activityInfo;
                String packageName = activityInfo.packageName;
                String className = activityInfo.name;
                // 清除之前的默认设置
                packageManager.clearPackagePreferredActivities(packageName);
                ComponentName componentName = new ComponentName(packageName, className);
                arrayOfComponentName[i] = componentName;
            }
            packageManager.addPreferredActivity(filter, IntentFilter.MATCH_CATEGORY_SCHEME, arrayOfComponentName,
                    component);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setDefaultLauncher(Context context) {
        try {
            // 设置默认launcher
            String packageName = "com.tcl.launcherpro"; // 默认launcher包名
            String className = "com.tcl.launcherpro.LauncherActivity"; // 默认launcher入口
            ComponentName defaultLauncher = new ComponentName(packageName, className);

            PackageManager pm = context.getPackageManager();
            // 判断指定的launcher是否存在
            // 清除当前默认launcher
            ArrayList<IntentFilter> intentList = new ArrayList<IntentFilter>();
            ArrayList<ComponentName> cnList = new ArrayList<ComponentName>();
            context.getPackageManager().getPreferredActivities(intentList, cnList, null);
            IntentFilter dhIF = null;
            for (int i = 0; i < cnList.size(); i++) {
                dhIF = intentList.get(i);
                if (dhIF.hasAction(Intent.ACTION_MAIN) && dhIF.hasCategory(Intent.CATEGORY_HOME)) {
                    context.getPackageManager().clearPackagePreferredActivities(cnList.get(i).getPackageName());
                }
            }

            // 获取所有launcher activity
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            List<ResolveInfo> list = new ArrayList<ResolveInfo>();
            list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

            IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
            filter.addAction(Intent.ACTION_MAIN);
            filter.addCategory(Intent.CATEGORY_HOME);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            final int N = list.size();
            ComponentName[] set = new ComponentName[N];
            int defaultMatch = 0;
            for (int i = 0; i < N; i++) {
                ResolveInfo r = list.get(i);
                set[i] = new ComponentName(r.activityInfo.packageName, r.activityInfo.name);
                if (defaultLauncher.getClassName().equals(r.activityInfo.name)) {
                    defaultMatch = r.match;
                }
            }
            // 设置默认方法一
            pm.addPreferredActivity(filter, defaultMatch, set, defaultLauncher);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
