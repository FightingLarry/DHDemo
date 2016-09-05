package com.dh.demo;

import android.view.View;

import com.dh.baseactivity.AdapterClickListener;
import com.dh.baseactivity.BaseRecycleViewFragment;
import com.dh.baseactivity.FragmentUtils;
import com.dh.demo.broadcast.SystemUIIfLauncherActionFragment;
import com.dh.demo.launcher.LauncherContentProviderFragment;
import com.dh.demo.phoneinfo.PhoneInfoFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yancai.liu on 2016/7/8.
 */

public class MainFragment extends BaseRecycleViewFragment implements AdapterClickListener<MainModel> {

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

        getAdapter().addItem(list);
        getAdapter().notifyDataSetChanged();
    }
}
