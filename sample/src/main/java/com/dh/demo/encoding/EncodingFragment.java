package com.dh.demo.encoding;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.MainActivity;
import com.dh.demo.R;

/**
 * Created by yancai.liu on 2016/12/7.
 */

public class EncodingFragment extends BaseFragment {

    private Button mTask;

    private String mText =
            "Oqxn9801-TrS6hFSJEvG6FGSLyp-v6Rrwx2o-ctdaXpPfOxcoMd14S-ejScmhcJ7mKFglUOMWbywrIV84e1DwGxJ4sM5S2jNeIaneowsQZXJW-OhxVxcADwk_vuelnyCCEXEZSncqoPjbbBm9L5gtxIckbWCbDHumYSNVvYf0t5uN8jnK7dzObMc30ardMRyoUcY5RanMKCaTLQgmGcGb4en4qYaCwKJADuRDUUYhOHrrAwV1CfYYP92IQ96OqEgFg39t4jcoc_UB9hYLRSba-R6cLLNEOJc4YH4Yp8pWScT2Q2aa5XEdOgG260qjKMGvaFzSJA2RNdQfek9Xpz8Hzq8rGP-r7CPLxU60Msu0ycKvDKy-tLOClfYyuyCdLkXO79AZ082UAGUrHq9QzNKNK5nSxtHgpcoHWK-LDwQC9FztNnce2_e4Lh2yRlEyXI12EZTr1qFGG1XpC4oclEDm6B9DjSkrR4D6dLY-_76cIqjXAkQIxmCSXIpUfZ2GRy-zxbdIsH0odctKkOtcvlRpuVesY2DnCc6V1vS_XhOrZegU1cAsLLSEOCp662QIEtE1eX7KE-bZdIRgJQvgoYB4WXMiL8hNWRKd9jzWwZlJ2M4cvIJ8Tff48KYMqWbDLtDFepfC7ErxVm3BvLMH2828SeS_I2H_CnPtTClFFsmm5smiBKhA7bakG9i8RZUP6xr2WPTt1lXb72wvausUVv9Vr0yriTaj-e5kXxhq4rrwKbOrUI5K24fSuqTvbyPRKhV6P1fr6LUGlg4Nno5nOMN6mt9TrgsNeKANmsScdwCnae40MZIs9KdONHeNTKiiWFLE6uOaxQ1WSi6vBX4rTwFpg==";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_taskline;
    }

    @Override
    public int getTitle() {
        return 0;
    }


    private String decode(String text) {
        return new StrCryptor().decodeString(text);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("yclll", decode(mText));

        mTask = (Button) view.findViewById(R.id.task);
        mTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("yclll", decode(mText));

                addShortcut("kuaijiefangshi");
            }
        });

    }


    private void addShortcut(String name) {
        Intent addShortcutIntent = new Intent(Manifest.permission.INSTALL_SHORTCUT);

        // 不允许重复创建
        addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式

        // 名字
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

        // 图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getActivity(), R.drawable.ic_loading_rotate));

        // 设置关联程序
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.setClass(getActivity(), MainActivity.class);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

        // 发送广播
        getActivity().sendBroadcast(addShortcutIntent);
    }
}
