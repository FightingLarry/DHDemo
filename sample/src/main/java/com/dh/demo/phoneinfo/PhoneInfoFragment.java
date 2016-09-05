package com.dh.demo.phoneinfo;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.view.View;

import com.dh.baseactivity.AdapterClickListener;
import com.dh.baseactivity.BaseRecycleViewFragment;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by yancai.liu on 2016/9/5.
 */
@RuntimePermissions
public class PhoneInfoFragment extends BaseRecycleViewFragment implements AdapterClickListener<PhoneInfo> {

    private PhoneInfoAdapter mAdapter;
    ClipboardManager mClipboardManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        getRecyclerView().setPullRefreshEnabled(false);
        getRecyclerView().setLoadingMoreEnabled(false);
    }

    @Override
    protected void readCacheOrExcuteRequest() {
        PhoneInfoFragmentPermissionsDispatcher.readPhoneStateWithCheck(this);
    }


    @Override
    protected PhoneInfoAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new PhoneInfoAdapter(getActivity(), this);
        }
        return mAdapter;
    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public void onItemClick(View view, PhoneInfo phoneInfo, int position) {
        // 将文本内容放到系统剪贴板里。
        mClipboardManager.setText(phoneInfo.getInfo());
    }


    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void readPhoneState() {

        List<PhoneInfo> list = new ArrayList<>();
        PhoneInfo info = new PhoneInfo();
        info.setName("IMEI");
        info.setInfo(PhoneInfoUtils.getIMEI(getActivity()));
        list.add(info);

        getAdapter().addItem(list);
        getAdapter().notifyDataSetChanged();

    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void readPhoneStateShowRationale(PermissionRequest request) {}

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void readPhoneStateDenied() {}

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void readPhoneStateNeverAsk() {}

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhoneInfoFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
