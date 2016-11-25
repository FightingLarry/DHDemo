package com.dh.demo.taskline;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;
import com.tcl.taskline.TaskManager;

/**
 * Created by yancai.liu on 2016/11/25.
 */

public class TaskFragment extends BaseFragment {
    private Button mTask;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_taskline;
    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTask = (Button) view.findViewById(R.id.task);
        mTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    UpdateTask task = new UpdateTask(i);
                    TaskManager.runOnWorkerThread(task);
                }
            }
        });
    }
}
