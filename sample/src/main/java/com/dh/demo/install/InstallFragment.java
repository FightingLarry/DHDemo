package com.dh.demo.install;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;

/**
 * Created by yancai.liu on 2017/1/10.
 */

public class InstallFragment extends BaseFragment {

    private Button button;
    private String mApkPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            new AsyncTask<Void, Void, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(Void... params) {
                    String uiFileName = "apk";
                    String result = WallpaperMineDefault.deepFile(getActivity(), uiFileName);
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                    mApkPath = result;
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = (Button) getRootView().findViewById(R.id.button1);
        button.setText("Install apk");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mApkPath) && !"-1".equals(mApkPath)) {
                    ApkUtil.install(getActivity(), mApkPath, "");
                }
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_normal;
    }

    @Override
    public int getTitle() {
        return 0;
    }
}
