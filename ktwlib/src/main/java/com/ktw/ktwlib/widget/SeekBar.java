package com.ktw.ktwlib.widget;

// Created by JS on 2021/2/20.

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.ktw.ktwlib.R;
import com.ktw.ktwlib.util.DisplayUtil;

public class SeekBar extends FrameLayout {

    private int dp1 = DisplayUtil.dp2px(getContext(), 1);
    private int viewWidth = 0;
    private int viewHeight = 0;

    // 进度条部分
    private Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private int progressColor = 0;
    private int progressLineHeight = dp1 * 2;

    // 进度条宽度
    private float progressWidth = 0f;

    // 切换的图片
    private ImageView toggleIcon = new ImageView(getContext());
    private int toggleIconWidth = dp1 * 44;
    private int toggleImgRes = R.mipmap.pic_knob;

    private ViewDragHelper dragHelper = ViewDragHelper.create(this, new DragHelperCallBack());

    // 当前进度，百分数比如：60%，此值为60
    private float progress = 0f;

    public SeekBar(@NonNull Context context) {
        this(context, null);
    }

    public SeekBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SeekBar);
        toggleImgRes = typedArray.getResourceId(R.styleable.SeekBar_img_res, R.mipmap.pic_knob);
        progressColor = typedArray.getColor(R.styleable.SeekBar_line_color, Color.parseColor("#ff2c2d2e"));
        toggleIcon.setImageResource(toggleImgRes);
        typedArray.recycle();
        init();
    }

    private void init() {
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth((float) progressLineHeight);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        LayoutParams layoutParams = new LayoutParams(toggleIconWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        toggleIcon.setLayoutParams(layoutParams);
        toggleIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(toggleIcon);
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == toggleIcon;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int tempLeft = left;
            if (left < 0) {
                tempLeft = 0;
            } else if (left > getWidth() - toggleIconWidth){
                tempLeft = getWidth() - toggleIconWidth;
            }
            progress = tempLeft / progressWidth;
            if (progressChangedListener != null) {
                progressChangedListener.onProgressChange(progress);
            }
            return tempLeft;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            // 停止滚动之后
        }
    }

    @Override
    public void computeScroll() {
        // 回弹时刷新界面
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != 0 && h != 0) {
            viewWidth = w;
            viewHeight = h;
            progressWidth = viewWidth - toggleIconWidth;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // 画背景线
        drawLine(canvas);
        super.dispatchDraw(canvas);
    }

    private void drawLine(Canvas canvas) {
        if (canvas == null) return;
        // 划线
        canvas.drawLine(toggleIconWidth / 2f, getHeight() / 2f,
                viewWidth - toggleIconWidth / 2f, getHeight() / 2f,
                progressPaint);
    }

    public void setToggleImgRes(int imgRes) {
        toggleIcon.setImageResource(imgRes);
    }

    public void setProgress(float progressValue) {
        progress = progressValue;
        post(new Runnable() {
            @Override
            public void run() {
                dragHelper.smoothSlideViewTo(toggleIcon, (int) (progressWidth * progress / 100f), 0);
                ViewCompat.postInvalidateOnAnimation(SeekBar.this);
            }
        });
    }

    public float getProgress() {
        return progress;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev != null) {
            return dragHelper.shouldInterceptTouchEvent(ev);
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX() - toggleIconWidth / 2f;
                    if (event.getX() < 0) {
                        x = 0f;
                    } else if (event.getX() > getWidth() - toggleIconWidth) {
                        x = getWidth() - toggleIconWidth;
                    }
                    dragHelper.smoothSlideViewTo(toggleIcon, (int) x, 0);
                    ViewCompat.postInvalidateOnAnimation(SeekBar.this);
                    if (progressChangedListener != null) {
                        progressChangedListener.onProgressChange(x / progressWidth * 100f);
                    }
                    break;
            }
            dragHelper.processTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }

    /******以下为设置监听*******/
    private OnProgressChangedListener progressChangedListener = null;

    public void setProgressChangedListener(OnProgressChangedListener progressChangedListener) {
        this.progressChangedListener = progressChangedListener;
    }

    public interface OnProgressChangedListener {
        void onProgressChange(float progress);
    }
}
