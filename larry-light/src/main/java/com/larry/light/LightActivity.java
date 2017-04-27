package com.larry.light;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Larry on 2017/4/11.
 */

public abstract class LightActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource());
        }
        initToolbar();
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    protected abstract int getLayoutResource();

}
