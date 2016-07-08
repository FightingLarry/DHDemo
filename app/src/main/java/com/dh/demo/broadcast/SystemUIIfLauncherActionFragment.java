package com.dh.demo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dh.baseactivity.BaseFragment;
import com.dh.baseactivity.BaseRecycleViewFragment;
import com.dh.demo.R;


/**
 * Created by yancai.liu on 2016/7/8.
 */

public class SystemUIIfLauncherActionFragment extends BaseFragment {

    private static final String BROADCAST = "com.tcl.mie.tlauncher.notify.SystemUIIfLauncherAction";


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BROADCAST.equals(action)) {
                Toast.makeText(getActivity(), BROADCAST, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST);
        filter.setPriority(1000);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main_item;
    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }
}
