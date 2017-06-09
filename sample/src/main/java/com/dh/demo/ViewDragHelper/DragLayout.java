package com.dh.demo.ViewDragHelper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Larry on 2017/6/9.
 */

public class DragLayout extends LinearLayout {

    private ViewDragHelper mDragger;
    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewDragHelper();
    }

    private void initViewDragHelper() {
        mDragger = ViewDragHelper.create(this, myCallback);
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    ViewDragHelper.Callback myCallback = new ViewDragHelper.Callback() {
        @Override
        // child为当前触摸区域下的View,如果返回true,就可以拖拽.
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView || child == mAutoBackView;
        }

        // 松手时的回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mAutoBackView) {
                mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                invalidate();
            }
        }

        // 边缘触摸开始时的回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragger.captureChildView(mEdgeTrackerView, pointerId);
        }

        // 获取水平方向允许拖拽的区域,这里是父布局的宽-子控件的宽
        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        // 获取垂直方向允许拖拽的范围
        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        // left为child即将移动到的水平位置的值,但是返回值会最终决定移动到的值
        // 这里直接返回了left
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        // 同上只是这里是垂直方向
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }
    };

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }
}
