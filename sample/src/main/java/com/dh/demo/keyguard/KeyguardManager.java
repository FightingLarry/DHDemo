package com.dh.demo.keyguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.dh.demo.R;

/**
 * Created by yancai.liu on 2017/1/22.
 */

public class KeyguardManager {

    private final static String TAG = "LockHelper";

    private Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;

    private LockView mLockView;

    private boolean mIsAddToWindow;

    private static Object object = new Object();


    private BroadcastReceiver mReceiver = new LockReceiver();
    private TelephonyManager mTelephonyManager;

    private boolean mIsInterrupt;



    public KeyguardManager(Context context) {
        this.mContext = context;
        initWindowManager(context);

        // int flag =
        // WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        // WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        // | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        // | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        // if (Preferences.isFullScreen(mContext))
        // flag |= WindowManager.LayoutParams.FLAG_FULLSCREEN |
        // WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        // else
        // flag |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
        // mParams.flags = flag;

        // KeyguardManager keyguard = (KeyguardManager)
        // context.getSystemService(Context.KEYGUARD_SERVICE);
        // keyguard.newKeyguardLock(TAG).disableKeyguard();

        initListener(context);
    }


    public void lock() {
        if (!isShowing()) {
            addLockView(getLockView());
        }
    }

    public void unlock() {
        removeLockView();
    }

    public Boolean isShowing() {
        return mIsAddToWindow;
    }

    public void onDestroy() {
        mContext.unregisterReceiver(mReceiver);
        mTelephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
    }


    private void initListener(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        context.registerReceiver(mReceiver, filter);


        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void initWindowManager(Context context) {
        this.mWindowManager =
                ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        mParams = new WindowManager.LayoutParams();

        mParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = height;
        mParams.gravity = Gravity.TOP;
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
    }


    private void addLockView(LockView lockView) {
        synchronized (object) {
            if (!mIsAddToWindow) {
                mLockView = lockView;
                mWindowManager.addView(mLockView, mParams);
                mIsAddToWindow = true;
            }
        }
    }

    private void removeLockView() {
        synchronized (object) {
            if (mIsAddToWindow) {
                mWindowManager.removeViewImmediate(mLockView);
                mIsAddToWindow = false;
                mLockView = null;
            }
        }
    }



    private LockView getLockView() {
        if (mLockView == null) {
            mLockView = LockView.fromXml(mContext);
            Button keyguardClose = (Button) mLockView.findViewById(R.id.keyguard_close);
            keyguardClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO

                    unlock();
                }
            });
        }
        return mLockView;
    }


    private PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (isShowing()) { // remove lock view only lock screen
                        unlock();
                        mIsInterrupt = true;
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mIsInterrupt) {
                        lock();
                        mIsInterrupt = false;
                    }
                    break;
                default:
                    break;
            }
        }

    };



    private boolean isPhoneIDLE() {
        return mTelephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE;
    }

    private class LockReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (Intent.ACTION_SCREEN_ON.equals(action)) {


            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                if (mIsInterrupt || !isPhoneIDLE()) {
                    return;
                }
                lock();
            }
        }
    }


}
