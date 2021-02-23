package com.ktw.ktwlib.widget;

// Created by JS on 2021/2/19.

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

public class FiveNodesToggleButton extends FrameLayout {

    private int dp1 = DisplayUtil.dp2px(getContext(), 1);
    private int viewWidth = 0;
    private int viewHeight = 0;

    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private int bgColor = Color.parseColor("#ff8e8e93");
    private int bgLineHeight = dp1 * 2;

    private ImageView toggleButtonIcon = new ImageView(getContext());
    private int toggleButtonWidth = dp1 * 44;

    private float[] nodesX = {0f, 0f, 0f, 0f, 0f};
    private float dx = 0f;

    private ViewDragHelper dragHelper = ViewDragHelper.create(this, new DragHelperCallBack());

    private int toggleImageRes = R.mipmap.pic_knob;

    private int currentNodePosition = 1; // 当前节点，注意从 1 开始

    public FiveNodesToggleButton(@NonNull Context context) {
        super(context);
        toggleButtonIcon.setImageResource(toggleImageRes);
        init();
    }

    public FiveNodesToggleButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FiveNodesToggleButton);
        toggleImageRes = typedArray.getResourceId(R.styleable.FiveNodesToggleButton_toggle_img_res, R.mipmap.pic_knob);
        toggleButtonIcon.setImageResource(toggleImageRes);
        typedArray.recycle();
        init();
    }

    private void init() {
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);
        bgPaint.setStrokeWidth((float) bgLineHeight);

        LayoutParams layoutParams = new LayoutParams(toggleButtonWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        toggleButtonIcon.setLayoutParams(layoutParams);
        toggleButtonIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(toggleButtonIcon);
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == toggleButtonIcon;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int tempLeft = left;
            if (left < 0) {
                tempLeft = 0;
            } else if (left > getWidth() - toggleButtonWidth) {
                tempLeft = getWidth() - toggleButtonWidth;
            }
            return tempLeft;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int originalNode = currentNodePosition;
            int centerX = toggleButtonIcon.getLeft() + toggleButtonIcon.getWidth() / 2;
            if (centerX <= nodesX[0] + dx / 2f) { // 第一个节点
                currentNodePosition = 1;
                dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) nodesX[0] - toggleButtonIcon.getWidth() / 2, 0);
                ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
            } else if (centerX > nodesX[0] + dx / 2f && centerX <= nodesX[1] + dx / 2f) {
                currentNodePosition = 2;
                dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) nodesX[1] - toggleButtonIcon.getWidth() / 2, 0);
                ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
            } else if (centerX > nodesX[1] + dx / 2f && centerX <= nodesX[2] + dx / 2f) {
                currentNodePosition = 3;
                dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) nodesX[2] - toggleButtonIcon.getWidth() / 2, 0);
                ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
            } else if (centerX > nodesX[2] + dx / 2f && centerX <= nodesX[3] + dx / 2f) {
                currentNodePosition = 4;
                dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) nodesX[3] - toggleButtonIcon.getWidth() / 2, 0);
                ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
            } else {
                currentNodePosition = 5;
                dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) nodesX[4] - toggleButtonIcon.getWidth() / 2, 0);
                ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
            }

            if (originalNode != currentNodePosition && nodesChangeListener != null) {
                nodesChangeListener.onNodesChange(currentNodePosition);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev != null) {
            return dragHelper.shouldInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    float centerX = event.getX();
                    if (centerX > nodesX[0] - (float) toggleButtonWidth / 2 && centerX < nodesX[0] + (float) toggleButtonWidth / 2) {
                        currentNodePosition = 1;
                        dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) (nodesX[0] - toggleButtonIcon.getWidth() / 2), 0);
                        ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
                    } else if (centerX > nodesX[1] - (float) toggleButtonWidth / 2 && centerX < nodesX[1] + (float) toggleButtonWidth / 2) {
                        currentNodePosition = 2;
                        dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) (nodesX[1] - toggleButtonIcon.getWidth() / 2), 0);
                        ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
                    } else if (centerX > nodesX[2] - (float) toggleButtonWidth / 2 && centerX < nodesX[2] + (float) toggleButtonWidth / 2) {
                        currentNodePosition = 3;
                        dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) (nodesX[2] - toggleButtonIcon.getWidth() / 2), 0);
                        ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
                    } else if (centerX > nodesX[3] - (float) toggleButtonWidth / 2 && centerX < nodesX[3] + (float) toggleButtonWidth / 2) {
                        currentNodePosition = 4;
                        dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) (nodesX[3] - toggleButtonIcon.getWidth() / 2), 0);
                        ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
                    } else if (centerX > nodesX[4] - (float) toggleButtonWidth / 2 && centerX < nodesX[4] + (float) toggleButtonWidth / 2){
                        currentNodePosition = 5;
                        dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) (nodesX[4] - toggleButtonIcon.getWidth() / 2), 0);
                        ViewCompat.postInvalidateOnAnimation(FiveNodesToggleButton.this);
                    }
                    if (nodesChangeListener != null) {
                        nodesChangeListener.onNodesChange(currentNodePosition);
                    }
                    break;
            }

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != 0 && h != 0) {
            viewWidth = w;
            viewHeight = h;
            dx = (viewWidth - toggleButtonWidth) / 4f;
            nodesX[0] = toggleButtonWidth / 2f;
            nodesX[1] = toggleButtonWidth / 2f + dx;
            nodesX[2] = toggleButtonWidth / 2f + dx * 2;
            nodesX[3] = toggleButtonWidth / 2f + dx * 3;
            nodesX[4] = viewWidth - toggleButtonWidth / 2f;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawBg(canvas);
        super.dispatchDraw(canvas);
    }

    private void drawBg(Canvas canvas) {
        if (canvas  == null) return;

        // 划线
        bgPaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(toggleButtonWidth / 2f, getHeight() / 2f, viewWidth - toggleButtonWidth / 2f, getHeight() / 2f, bgPaint);

        bgPaint.setStyle(Paint.Style.FILL);

        // 画第一个圆直径7dp
        canvas.drawCircle(nodesX[0], getHeight() / 2f, dp1 * 3.5f, bgPaint);
        // 画第二个圆直径6dp
        canvas.drawCircle(nodesX[1], getHeight() / 2f, dp1 * 3f, bgPaint);
        // 画第三个圆直径7dp
        canvas.drawCircle(nodesX[2], getHeight() / 2f, dp1 * 3.5f, bgPaint);
        // 画第四个圆直径6dp
        canvas.drawCircle(nodesX[3], getHeight() / 2f, dp1 * 3f, bgPaint);
        // 画第五个圆直径7dp
        canvas.drawCircle(nodesX[4], getHeight() / 2f, dp1 * 3.5f, bgPaint);
    }

    public void setNodePosition(int nodePosition) {
        if (nodePosition < 1 || nodePosition > 5) return;
        currentNodePosition = nodePosition;
        dragHelper.smoothSlideViewTo(toggleButtonIcon, (int) (nodesX[nodePosition - 1] - toggleButtonIcon.getWidth() / 2), 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setToggleImageRes(int imageRes) {
        toggleButtonIcon.setImageResource(imageRes);
    }

    public int getCurrentNode() {
        return currentNodePosition;
    }

    /***********以下为设置监听***********/
    private NodesChangeListener nodesChangeListener = null;

    public void setNodesChangeListener(NodesChangeListener nodesChangeListener) {
        this.nodesChangeListener = nodesChangeListener;
    }

    public interface NodesChangeListener {
        void onNodesChange(int currentNode);
    }
}
