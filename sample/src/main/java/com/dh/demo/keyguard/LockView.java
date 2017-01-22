package com.dh.demo.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.dh.demo.R;

public class LockView extends FrameLayout {


    public LockView(Context context) {
        this(context, null);
        init();
    }

    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {


    }


    static LockView fromXml(Context context) {
        LockView layout = (LockView) LayoutInflater.from(context).inflate(R.layout.layout_keyguard, null, false);
        return layout;
    }

}
