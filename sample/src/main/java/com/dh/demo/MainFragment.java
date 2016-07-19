package com.dh.demo;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dh.baseactivity.AdapterClickListener;
import com.dh.baseactivity.ArbitraryFragmentActivity;
import com.dh.baseactivity.BaseRecycleViewFragment;
import com.dh.baseactivity.FragmentUtils;
import com.dh.demo.broadcast.SystemUIIfLauncherActionFragment;
import com.dh.demo.launcher.LauncherContentProviderActivity;
import com.dh.demo.launcher.LauncherContentProviderFragment;
import com.dh.demo.launcher.contentprovider.launcher.LauncherSettings;

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

            // Intent intent = new Intent(getActivity(), LauncherContentProviderActivity.class);
            // intent.setData(LauncherSettings.Favorites.CONTENT_BACKUP_URI);
            // intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // startActivity(intent);

            FragmentUtils.navigateToInNewActivity(getActivity(), LauncherContentProviderFragment.class, null, view);

            // Bundle bundle = new Bundle();
            // Intent intent = new Intent(getActivity(), ArbitraryFragmentActivity.class);
            // intent.putExtra(ArbitraryFragmentActivity.EXTRAS_FRAGMENT_CLASS_NAME,
            // LauncherContentProviderFragment.class);
            // intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //
            // if (view != null && FragmentUtils.hasJellyBean()) {
            // int location[] = new int[2];
            // view.getLocationOnScreen(location);
            // ActivityOptions activityOptions = ActivityOptions.makeScaleUpAnimation(view,
            // location[0], location[1],
            // view.getWidth(), view.getHeight());
            // if (bundle == null) {
            // bundle = new Bundle();
            // }
            // intent.putExtra(ArbitraryFragmentActivity.EXTRAS_BUNDLE, bundle);
            // getActivity().startActivity(intent, activityOptions.toBundle());
            // } else {
            // intent.putExtra(ArbitraryFragmentActivity.EXTRAS_BUNDLE, bundle);
            // getActivity().startActivity(intent);
            // }

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

        getAdapter().addItem(list);
        getAdapter().notifyDataSetChanged();
    }
}
