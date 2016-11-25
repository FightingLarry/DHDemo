package com.tcl.tasklinedemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.tcl.taskline.TaskManager;

/**
 * Created by yancai.liu on 2016/11/25.
 */

public class MainActivity extends Activity {

    private Button mTask;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.taskdemo_main);
        mTask = (Button) findViewById(R.id.task);
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
