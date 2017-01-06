package com.dh.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.dh.baseactivity.AdapterClickListener;
import com.dh.baseactivity.BaseRecycleViewFragment;
import com.dh.baseactivity.FragmentUtils;
import com.dh.demo.alarm.AlarmFragment;
import com.dh.demo.broadcast.SystemUIIfLauncherActionFragment;
import com.dh.demo.defaultBrowser.DefaultBrowserFragment;
import com.dh.demo.encoding.EncodingFragment;
import com.dh.demo.launcher.LauncherContentProviderFragment;
import com.dh.demo.phoneinfo.PhoneInfoFragment;
import com.dh.demo.taskline.TaskFragment;
import com.dh.demo.thread.WaitThread;
import com.dh.demo.userstate.UserStateFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yancai.liu on 2016/7/8.
 */

public class MainFragment extends BaseRecycleViewFragment implements AdapterClickListener<MainModel> {

    private static final String TAG = "DHDemoMain";
    private MainAdapter mAdapter;

    @Override
    protected MainAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new MainAdapter(getActivity(), this);
        }
        return mAdapter;
    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        getRecyclerView().setPullRefreshEnabled(false);
        getRecyclerView().setLoadingMoreEnabled(false);
    }

    @Override
    public void onItemClick(View view, MainModel o, int position) {
        if (o.getType() == MainModel.Type.SystemUIIfLauncher) {
            FragmentUtils.navigateToInNewActivity(getActivity(), SystemUIIfLauncherActionFragment.class, null, view);

        } else if (o.getType() == MainModel.Type.LauncherContentProvider) {
            FragmentUtils.navigateToInNewActivity(getActivity(), LauncherContentProviderFragment.class, null, view);
        } else if (o.getType() == MainModel.Type.PhoneInfo) {
            FragmentUtils.navigateToInNewActivity(getActivity(), PhoneInfoFragment.class, null, view);
        } else if (o.getType() == MainModel.Type.WaitThread) {
            WaitThread.execute();
        } else if (o.getType() == MainModel.Type.TaskLine) {
            FragmentUtils.navigateToInNewActivity(getActivity(), TaskFragment.class, null, view);
        } else if (o.getType() == MainModel.Type.Decode) {
            FragmentUtils.navigateToInNewActivity(getActivity(), EncodingFragment.class, null, view);
        } else if (o.getType() == MainModel.Type.TrackerService) {
            startTwoService(getActivity());
            for (int i = 0; i < 100; i++) {
                Log.i("aaaa", "" + (Math.random() * 86400000 / 3600000));
            }

            Intent intent = new Intent();
            intent.setClassName("com.tct.gallery3d", "com.tct.gallery3d.app.PermissionActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);

            Log.e("ycllll", intent.toUri(0));

        } else if (o.getType() == MainModel.Type.Alarm) {
            FragmentUtils.navigateToInNewActivity(getActivity(), AlarmFragment.class, null, view);
        } else if (o.getType() == MainModel.Type.DefaultBrowser) {
            FragmentUtils.navigateToInNewActivity(getActivity(), DefaultBrowserFragment.class, null, view);
        } else if (o.getType() == MainModel.Type.UserState) {
            FragmentUtils.navigateToInNewActivity(getActivity(), UserStateFragment.class, null, view);
        }

    }


    private static final String PACKAGE_NAME = "com.android.tbks.debug";
    private static final String BKS_SERVICE = "com.android.tbks.service.BksService";
    private static final String POLLING_SERVICE = "com.tcl.activate.service.PollingService";

    private static void startTwoService(Context context) {

        // Android 5.0以后需显示启动service
        Intent pollingIntent = new Intent();
        pollingIntent.setClassName(PACKAGE_NAME, POLLING_SERVICE);
        Intent bksIntent = new Intent();
        bksIntent.setClassName(PACKAGE_NAME, BKS_SERVICE);
        ComponentName cn_polling = context.startService(pollingIntent);
        ComponentName cn_bks = context.startService(bksIntent);
        if (cn_bks != null && cn_polling != null) {
            Log.w("TrackerLyc", "success");
        } else {
            Log.w("TrackerLyc", "start tracker failed");
        }
    }

    @Override
    protected void readCacheOrExcuteRequest() {
        super.readCacheOrExcuteRequest();
        List<MainModel> list = new ArrayList<>();
        MainModel model = new MainModel();
        model.setType(MainModel.Type.SystemUIIfLauncher);
        model.setTitle("SystemUIIfLauncher");
        model.setDes("SystemUIIfLauncher");
        list.add(model);

        model = new MainModel();
        model.setType(MainModel.Type.LauncherContentProvider);
        model.setTitle(getString(R.string.title_launcher_content_provider));
        model.setDes("LauncherContentProvider");
        list.add(model);

        model = new MainModel();
        model.setType(MainModel.Type.PhoneInfo);
        model.setTitle("手机信息");
        model.setDes("手机详细信息");
        list.add(model);

        model = new MainModel();
        model.setType(MainModel.Type.WaitThread);
        model.setTitle("测试现成等待");
        model.setDes("测试现成等待");
        list.add(model);

        model = new MainModel();
        model.setType(MainModel.Type.TaskLine);
        model.setTitle("流水线工作");
        model.setDes("流水线工作测试");
        list.add(model);

        model = new MainModel();
        model.setType(MainModel.Type.Decode);
        model.setTitle("解密");
        model.setDes("解密TR");
        list.add(model);

        model = new MainModel();
        model.setType(MainModel.Type.TrackerService);
        model.setTitle("TrackerService");
        model.setDes("TrackerService");
        list.add(model);

        model = new MainModel();
        model.setType(MainModel.Type.Alarm);
        model.setTitle("AlarmManager管理测试");
        list.add(model);

        model = new MainModel();
        model.setType(MainModel.Type.DefaultBrowser);
        model.setTitle("默认浏览器");
        model.setDes("默认浏览器");
        list.add(model);
        getAdapter().addItem(list);

        model = new MainModel();
        model.setType(MainModel.Type.UserState);
        model.setTitle("数据收集");
        model.setDes("数据收集");
        list.add(model);
        getAdapter().addItem(list);


        getAdapter().notifyDataSetChanged();
    }
}
