package com.js.sample.singleClick.listener;

import android.view.View;


// Created by JS on 2019/6/3.

public abstract class CustomOnClickListener implements View.OnClickListener {
    private Long mLastClickTime = 0L;
    private Long timeInterval = 1000L;

    public CustomOnClickListener() {

    }

    public CustomOnClickListener(long interval) {
        this.timeInterval = interval;
    }
    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick();
            mLastClickTime = currentTime;
        }
        else {
            // 快速点击事件
            onFastClick();
        }
    }

    protected abstract void onSingleClick();

    protected abstract void onFastClick();
}
