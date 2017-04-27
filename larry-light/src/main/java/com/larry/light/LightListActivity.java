package com.larry.light;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Larry on 2017/4/11.
 */

public abstract class LightListActivity<T> extends LightActivity {

    protected RecyclerView mRecyclerView;

    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected int getLayoutResource() {
        return R.layout.light_activity_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(getLayoutManager());

        initAdapter();

        readCacheOrExcuteRequest();

    }

    protected void readCacheOrExcuteRequest() {

    }


    protected abstract RecyclerView.Adapter getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        return mLayoutManager;
    }

    private void initAdapter() {
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(getAdapter());
        }
    }


}
