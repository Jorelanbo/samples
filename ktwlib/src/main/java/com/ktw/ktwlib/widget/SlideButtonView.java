package com.ktw.ktwlib.widget;

// Created by JS on 2021/2/20.

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.ktw.ktwlib.R;
import com.ktw.ktwlib.util.DisplayUtil;

public class SlideButtonView extends FrameLayout {

    private static final String TAG = "SlideButtonView";

    public static final int STATE_UN_ACTIVATE = 1; // 未激活
    public static final int STATE_ACTIVATING = 2; // 激活中
    public static final int STATE_ACTIVATE_SUCCESS = 3; // 激活成功
    public static final int STATE_ACTIVATE_FAIL = 4; // 激活失败

    private View slideView = new View(getContext());
    private LinearGradientTextView textView = new LinearGradientTextView(getContext());
    private ViewDragHelper dragHelper = ViewDragHelper.create(this, new DragHelperCallBack());

    private int state = STATE_UN_ACTIVATE;

    /****一下为支持属性****/
    /*背景*/
    private int enableBackgroundRes = R.drawable.rect_ff000000_r4;
    private int unEnableBackgroundRes = R.drawable.rect_ffaaaaaa_r4;
    /*文字*/
    // 文字开始的颜色
    private int textStartColor = Color.parseColor("#62FFFFFF");
    // 文字结束的颜色
    private int textEndColor = Color.parseColor("#FFFFFFFF");
    // unEnable时的颜色
    private int unEnableTextColor = Color.parseColor("#FFFFFFFF");
    // 未激活时的文字文本
    private String textUnActivate = "向右滑动以激活";
    // 激活中的文字文本
    private String textActivating = "正在激活...";
    // 激活成功的文字文本
    private String textActivateSuccess = "激活成功";
    // 激活失败的文字文本
    private String textActivateFail = "激活失败";
    // 文本大小
    private float textSize = 17f;
    /*滑块*/
    // 滑块背景，可以是shape或图片
    private int slideViewBgRes = R.drawable.rect_ffffffff_r4;
    // 滑块的宽度
    private float slideViewWidth = DisplayUtil.dip2px(getContext(), 50);
    // 滑块的高度
    private float slideViewHeight = DisplayUtil.dip2px(getContext(), 40);
     // 滑块的左边距
    private float slideViewLeftMargin = DisplayUtil.dip2px(getContext(), 2.5f);
    // 滑块的右边距
    private float slideViewRightMargin = DisplayUtil.dip2px(getContext(), 2.5f);


    public SlideButtonView(@NonNull Context context) {
        this(context, null);
    }

    public SlideButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SlideButtonView);
            enableBackgroundRes = attributes.getResourceId(R.styleable.SlideButtonView_enable_background_res, enableBackgroundRes);
            textStartColor = attributes.getColor(R.styleable.SlideButtonView_text_start_color, textStartColor);
            textEndColor = attributes.getColor(R.styleable.SlideButtonView_text_end_color, textEndColor);
            if (attributes.getString(R.styleable.SlideButtonView_text_un_activate) != null) {
                textUnActivate = attributes.getString(R.styleable.SlideButtonView_text_un_activate);
            }
            if (attributes.getString(R.styleable.SlideButtonView_text_activating) != null) {
                textActivating = attributes.getString(R.styleable.SlideButtonView_text_activating);
            }
            if (attributes.getString(R.styleable.SlideButtonView_text_activate_success) != null) {
                textActivateSuccess = attributes.getString(R.styleable.SlideButtonView_text_activate_success);
            }
            if (attributes.getString(R.styleable.SlideButtonView_text_activate_fail) != null) {
                textActivateFail = attributes.getString(R.styleable.SlideButtonView_text_activate_fail);
            }
            textSize = attributes.getDimension(R.styleable.SlideButtonView_text_size, textSize);
            slideViewBgRes = attributes.getResourceId(R.styleable.SlideButtonView_slide_view_bg, slideViewBgRes);
            slideViewWidth = attributes.getDimension(R.styleable.SlideButtonView_slide_view_width, slideViewWidth);
            slideViewHeight = attributes.getDimension(R.styleable.SlideButtonView_slide_view_height, slideViewHeight);
            slideViewLeftMargin = attributes.getDimension(R.styleable.SlideButtonView_slide_view_left_margin, slideViewLeftMargin);
            slideViewRightMargin = attributes.getDimension(R.styleable.SlideButtonView_slide_view_right_margin, slideViewRightMargin);
            attributes.recycle();
        }
        initView();
    }

    private void initView() {
        // 初始化背景
        setBackgroundResource(enableBackgroundRes);

        // 初始化文字
        textView.setTextSize(textSize);
        LayoutParams textLP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLP.gravity = Gravity.CENTER;
        textView.setLayoutParams(textLP);
        addView(textView);

        // 初始化滑块
        LayoutParams slideViewLp = new LayoutParams((int) slideViewWidth, (int) slideViewHeight);
        slideViewLp.gravity = Gravity.CENTER_VERTICAL;
        slideViewLp.leftMargin = (int) slideViewLeftMargin;
        slideView.setLayoutParams(slideViewLp);
        slideView.setBackgroundResource(slideViewBgRes);
        addView(slideView);

        setStateUI(state);
    }

    public void setState(int state) {
        this.state = state;
        setStateUI(state);
    }

    private void setStateUI(int state) {
        switch (state) {
            case STATE_UN_ACTIVATE:
                reset();
                setBackgroundResource(enableBackgroundRes);
                slideView.setVisibility(VISIBLE);
                textView.setText(textUnActivate);
                textView.horizontalGradient(textStartColor, textEndColor, true);
                break;
            case STATE_ACTIVATING:
                setBackgroundResource(unEnableBackgroundRes);
                slideView.setVisibility(INVISIBLE);
                textView.setText(textActivating);
                textView.horizontalGradient(unEnableTextColor, unEnableTextColor, true);
                break;
            case STATE_ACTIVATE_SUCCESS:
                setBackgroundResource(unEnableBackgroundRes);
                slideView.setVisibility(INVISIBLE);
                textView.setText(textActivateSuccess);
                textView.horizontalGradient(unEnableTextColor, unEnableTextColor, true);
                break;
            case STATE_ACTIVATE_FAIL:
                setBackgroundResource(unEnableBackgroundRes);
                slideView.setVisibility(INVISIBLE);
                textView.setText(textActivateFail);
                textView.horizontalGradient(unEnableTextColor, unEnableTextColor, true);
                break;
        }
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == slideView;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int tempLeft = left;
            if (left < slideViewLeftMargin) {
                tempLeft = (int) slideViewLeftMargin;
            } else if (left > getWidth() - slideViewRightMargin - slideViewWidth) {
                tempLeft = (int) (getWidth() - slideViewRightMargin - slideViewWidth);
            }
            return tempLeft;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return (int) (getHeight() / 2 - slideViewHeight / 2);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int left = slideView.getLeft();
            if (left < getWidth() / 2) {
                dragHelper.smoothSlideViewTo(slideView, (int) slideViewLeftMargin, slideView.getTop());
                ViewCompat.postInvalidateOnAnimation(SlideButtonView.this);
            } else {
                dragHelper.smoothSlideViewTo(slideView, (int) (getWidth() - slideViewRightMargin - slideViewWidth), slideView.getTop());
                ViewCompat.postInvalidateOnAnimation(SlideButtonView.this);
                if (onSlideFinishListener != null) {
                    onSlideFinishListener.onSlideFinish();
                }
            }
        }
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
            dragHelper.processTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 回弹时刷新界面
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void reset() {
        post(new Runnable() {
            @Override
            public void run() {
                dragHelper.smoothSlideViewTo(slideView, (int) slideViewLeftMargin, slideView.getTop());
                ViewCompat.postInvalidateOnAnimation(SlideButtonView.this);
            }
        });
    }

    /*********设置监听*********/
    private OnSlideFinishListener onSlideFinishListener = null;

    public void setOnSlideFinishListener(OnSlideFinishListener onSlideFinishListener) {
        this.onSlideFinishListener = onSlideFinishListener;
    }

    public interface OnSlideFinishListener {
        void onSlideFinish();
    }
}
