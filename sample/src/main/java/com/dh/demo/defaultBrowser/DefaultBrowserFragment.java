package com.dh.demo.defaultBrowser;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;

/**
 * Created by yancai.liu on 2016/12/29.
 */

public class DefaultBrowserFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "DefaultBrowserFragment";
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    private int index = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dafaultbrowser;
    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) view.findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) view.findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) view.findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn4:
                Intent intentGithub = new Intent(Intent.ACTION_VIEW);
                intentGithub.setData(Uri.parse("http://www.baidu.com"));
                intentGithub.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intentGithub);
                break;
            case R.id.btn1:
                Toast.makeText(getActivity(), "默认桌面=" + BrowserUtils.isDefaultBrowser(getActivity()),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                BrowserUtils.clearDefaultBrowser(getActivity());
                break;
            case R.id.btn3:
                ComponentName component =
                        new ComponentName("com.baidu.searchbox", "com.baidu.searchbox.BoxBrowserActivity");
                BrowserUtils.setDefaultBrowser(getActivity(), component);
                break;
            case R.id.btn5:
                // "com.baidu.searchbox", "com.baidu.searchbox.BoxBrowserActivity"
                // com.android.browser/com.tencent.mtt.SplashActivity
                // com.hawk.android.browser/.BrowserActivity
                // com.quick.android.browser/com.hawk.android.browser.BrowserActivity
                String[] pkgs = {"com.baidu.searchbox", "com.android.browser", "com.hawk.android.browser",
                        "com.quick.android.browser"};
                Log.w(TAG, pkgs[index]);
                BrowserUtils.setDefaultBrowserPackageName(getActivity(), pkgs[index]);
                if (index < 3) {
                    index++;
                } else {
                    index = 0;
                }
                break;
        }
    }
}
