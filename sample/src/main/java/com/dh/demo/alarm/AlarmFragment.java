package com.dh.demo.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;

/**
 * Created by yancai.liu on 2016/7/19.<br/>
 * 注意：两个PendingIntent对等是指它们的operation一样, 且其它们的Intent的action, data, categories, components和flags都一样。但是它们的Intent的Extra可以不一样。
 * <br/>主要常量
 * <br/>FLAG_CANCEL_CURRENT:如果当前系统中已经存在一个相同的PendingIntent对象，那么就将先将已有的PendingIntent取消，然后重新生成一个PendingIntent对象。
 * <br/>FLAG_NO_CREATE:如果当前系统中不存在相同的PendingIntent对象，系统将不会创建该PendingIntent对象而是直接返回null。
 * <br/>FLAG_ONE_SHOT:该PendingIntent只作用一次。在该PendingIntent对象通过send()方法触发过后，PendingIntent将自动调用cancel()进行销毁，那么如果你再调用send()方法的话，系统将会返回一个SendIntentException。
 * <br/>FLAG_UPDATE_CURRENT:如果系统中有一个和你描述的PendingIntent对等的PendingInent，那么系统将使用该PendingIntent对象，但是会使用新的Intent来更新之前PendingIntent中的Intent对象数据，例如更新Intent中的Extras。
 */

public class AlarmFragment extends BaseFragment {

    private static final long TIME_HOUR = 1;
    private Button btn1;
    private Button btn2;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_launcher_content_provider;
    }

    @Override
    public int getTitle() {
        return R.string.title_alarm;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn1 = (Button) getRootView().findViewById(R.id.btn1);
        btn1.setText("SetAlarm");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarmSchedule(getActivity(), "action1", null, 100, System.currentTimeMillis() + 100, 5000, false);

                Bundle bundle = new Bundle();
                bundle.putInt("id", 10000);
                startAlarmSchedule(getActivity(), "action2-1", bundle, 101, System.currentTimeMillis() + 1000, 5000, false);

                Bundle bundle2 = new Bundle();
                bundle2.putInt("id", 10001);
                startAlarmSchedule(getActivity(), "action2-1", bundle2, 102, System.currentTimeMillis() + 2000, 5000, false);

                startAlarmSchedule(getActivity(), "action4", null, 103, System.currentTimeMillis() + 3000, 5000, false);

            }
        });

        btn2 = (Button) getRootView().findViewById(R.id.btn2);
        btn2.setText("CancelAlarm");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                bundle2.putInt("id", 10001);
                stopAlarmSchedule(getActivity(), "action2-1", bundle2, 100, System.currentTimeMillis() + 100, 5000);

//                Bundle bundle = new Bundle();
//                bundle.putInt("id", 10000);
//                stopAlarmSchedule(getActivity(), "action2", bundle, 101, System.currentTimeMillis() + 1000, 5000);
//                Bundle bundle2 = new Bundle();
//                bundle2.putInt("id", 10001);
//                stopAlarmSchedule(getActivity(), "action3", bundle2, 102, System.currentTimeMillis() + 2000, 5000);
//
//                stopAlarmSchedule(getActivity(), "action4", null, 103, System.currentTimeMillis() + 3000, 5000);
            }
        });
    }


    public static void startAlarmSchedule(Context context, String action, Bundle bundle, int requestCode,
                                          long startTime, long repeatPeriod, boolean needImmediately) {
        try {
            Intent intent = new Intent(context, BksService.class);
            intent.setAction(action);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            PendingIntent sender =
                    PendingIntent.getService(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            boolean needSet = true;
            if (startTime < System.currentTimeMillis()) {
                if (needImmediately) {
                    startTime = System.currentTimeMillis();
                } else {
                    needSet = false;
                }
            }
            if (needSet) {
                if (repeatPeriod > 0) {
                    if (repeatPeriod < TIME_HOUR) {
                        repeatPeriod = TIME_HOUR;
                    }
                    am.cancel(sender);
                    am.setRepeating(AlarmManager.RTC, startTime, repeatPeriod, sender);
                } else {
                    // 时间过去了，则立即执行
                    am.cancel(sender);
                    am.set(AlarmManager.RTC, startTime, sender);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void stopAlarmSchedule(Context context, String action, Bundle bundle, int requestCode,
                                         long startTime, long repeatPeriod) {
        try {
            Intent intent = new Intent(context, BksService.class);
            intent.setAction(action);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            PendingIntent sender =
                    PendingIntent.getService(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.cancel(sender);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
