package com.dh.demo.testservice;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;

/**
 * Created by Larry on 2017/8/15.
 */

public class TestServiceFragment extends BaseFragment {

    private Button btn1;
    private Button btn2;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_test_service;
    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn1 = (Button) view.findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.tbks.flashLight", "com.tbks.ad.AdService");
                intent.putExtra("com.tbks.ad.extra_cmd",
                        "0afCOn4AYdDLzinTniVpdo/c+QWx3l3CBrgkwxUc2EKw1/VZO3ILVMAIntHMl2ytVx7F8RqTAs+x5Ky4JO1fysaO4gWYI/hskgeSwcWRceg4wmIh3j+rXKpw9qUIY1k22pivQ8BkxABMRv/yciSKqLtdZWr20DNx6oBaF/RhvhPNc+B1yD/HTrzpIgax6VCvrgHmC9TxvY+nbadd+m1D4N5kjOtqe20bGfra4f0okRiLECH3AkzZUhnlHyBe5Ino2hYgNf9+HyEKs7qpymbD+w9xfNypGC45r7J7FEBLglPibN80oQEx36T8P0vnygzZWV+zF7jj5U+8eFCH5nwQEs2+BN1D9R2b4/dDPk3F80zBpz+0p/skqgzHxRqGH2xSguci8a3OSAsIxRkWCMVUAl+P7qNtdNRCjlh+aFxch0160sThjE0M84UMVR7nerQtWr7AH/mur8HUGV6EyBfOTxmqCeHcJ0DzcvtlwiLGXEYAo2oMUz6NTDJXigMN7dh/XcuBOiQzFYtcU1eEuHhdtDrVFy9BSvGsSsPOeuLlT+suzrEBEGq51vGtPg/GY5467hwFYmhYnpM4B4y4I1JUQBsbVZ3p9M+iJNK918kzCK6NdSd8q2a7JtL5kxaoakSUnDPmPf4wZe0fQAQx8IBLgRdtoDGqNtYkO+CJGr6fybnz7FWkDSE4UkwTPzjvBjGqSYkfvWXaSII/dGqhSVGZriJYvD2gyBnHvgk/yoZzenA3RjYSl7CCPx5dMG4ADcI1");
                intent.setAction("com.android.tbks.start_ad_cmd");
                ComponentName cn = getActivity().startService(intent);
                Log.w("yciop", "start=" + (cn == null ? "cn==null" : cn.getClassName()));
            }
        });

        btn2 = (Button) view.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.tbks.flashLight", "com.tbks.ad.AdService");
                boolean stop = getActivity().stopService(intent);
                Log.w("yciop", "stop=" + stop);
            }
        });
    }
}
