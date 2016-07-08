package com.dh.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.dh.baseactivity.AdapterClickListener;
import com.dh.baseactivity.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void initializeStartingFragment() {
        try {
            Fragment fragment = new MainFragment();
            loadFragment(fragment, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
