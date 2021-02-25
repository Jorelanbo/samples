package com.ktw.ktwlib.widget;

// Created by JS on 2021/2/23.

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import com.ktw.ktwlib.util.DisplayUtil;
import com.ktw.ktwlib.util.LogUtils;

public class TestView extends LinearLayout {

    private float mLastY = -1;
    private int mActivePointerId;
    private float sumOffSet;
    private static final float DRAG_RATE = 2.0f;
    private int measuredHeight = 0;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        initView();
    }

    private void initView() {
        measuredHeight = DisplayUtil.dip2px(getContext(), 200f);
        LogUtils.d("===", "测量的高度：" + measuredHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLastY == -1) {
            mLastY = event.getY();
            mActivePointerId = event.getPointerId(0);
            sumOffSet = 0;
        }
        LogUtils.d("===", "1");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                mActivePointerId = event.getPointerId(0);
                sumOffSet = 0;
                LogUtils.d("===", "2");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                final int index = event.getActionIndex();
                mActivePointerId = event.getPointerId(index);
                mLastY = (int) event.getY(index);
                LogUtils.d("===", "3");
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(mActivePointerId);
                if (pointerIndex == -1) {
                    pointerIndex = 0;
                    mActivePointerId = event.getPointerId(pointerIndex);
                }
                final int moveY = (int) event.getY(pointerIndex);
                final float deltaY = (moveY - mLastY) / DRAG_RATE;
                mLastY = moveY;
                sumOffSet += deltaY;
                setVisibleHeight((int) (deltaY + getVisibleHeight()));
                LogUtils.d("===", "4");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.d("===", "5");
                smoothScrollTo(measuredHeight);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setVisibleHeight(int height) {
        LogUtils.d("===", height + "");
        if (height < 0) height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
        lp.height = height;
        setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
        return lp.height;
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}
