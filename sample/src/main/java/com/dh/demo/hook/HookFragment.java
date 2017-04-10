package com.dh.demo.hook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;
import com.dh.demo.taskline.UpdateTask;
import com.tcl.taskline.TaskManager;

/**
 * Created by Larry on 2017/4/10.
 */

public class HookFragment extends BaseFragment {
    private Button mTask;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_normal;
    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTask = (Button) view.findViewById(R.id.button1);
        mTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
