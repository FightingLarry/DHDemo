package com.dh.demo.topactivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;

/**
 * Created by Larry on 2017/7/19.
 */

public class TopActivityFragment extends BaseFragment {



    private Button mButton1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_top_activity;
    }

    @Override
    public int getTitle() {
        return 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButton1 = (Button) view.findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopActivityService.class);
                intent.setAction(TopActivityService.ACTION_TOP_ACTIVITY);
                ComponentName cn = getActivity().startService(intent);
                if (cn != null) {
                    Log.w(TopActivityService.TAG, "TopActivityService成功.");
                } else {
                    Log.e(TopActivityService.TAG, "TopActivityService失败！！");
                }
            }
        });
        return view;
    }

}
