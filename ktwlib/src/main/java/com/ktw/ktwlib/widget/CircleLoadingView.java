package com.ktw.ktwlib.widget;

// Created by JS on 2021/2/22.

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.ktw.ktwlib.R;

public class CircleLoadingView extends View {

    private static final String TAG = "CircleLoading";

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private float strokeWidth = 50f;

    private RectF rect = new RectF();
    private int startColor = Color.parseColor("#00000000");
    private int endColor = Color.parseColor("#FFE60012");

    private int[] colors = {startColor, endColor};

    private int viewWidth = 0;
    private int viewHeight = 0;

    /**** 动画配置 ****/
    private boolean isAnimating = false; // 是否正在动画中
    private ValueAnimator animator = null;
    private long animatorDuration = 5000L;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleLoadingView);
        strokeWidth = attributes.getFloat(R.styleable.CircleLoadingView_stroke_width, strokeWidth);
        startColor = attributes.getColor(R.styleable.CircleLoadingView_start_color, startColor);
        endColor = attributes.getColor(R.styleable.CircleLoadingView_end_color, endColor);
        animatorDuration = attributes.getInt(R.styleable.CircleLoadingView_anim_duration, (int) animatorDuration);
        attributes.recycle();
        init();
        startAnimation();
    }

    private void init() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        circlePaint.setColor(endColor);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != 0) {
            viewWidth = w;
        }
        if (h != 0) {
            viewHeight = h;
        }
        if (viewWidth != viewHeight) {
            Log.d(TAG, "宽高不一样");
        }
        Shader shader = new SweepGradient(viewWidth / 2f, viewHeight / 2f, startColor, endColor);
        paint.setShader(shader);

        rect.set(strokeWidth / 2, strokeWidth / 2, viewWidth - strokeWidth / 2, viewHeight - strokeWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            canvas.drawArc(rect, 0f, 360f, false, paint);
//            canvas.drawCircle(viewWidth / 2f, viewHeight / 2f, (float) viewWidth / 2 - strokeWidth / 2, paint);
            canvas.drawCircle(viewWidth - strokeWidth / 2, viewHeight / 2f, strokeWidth / 2, circlePaint);
        }
    }

    private void startAnimation() {
        if (isAnimating) return;
        isAnimating = true;
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, 360f);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setDuration(animatorDuration);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Float rotation = (Float) animation.getAnimatedValue();
                    setRotation(rotation);
                }
            });
        }
        animator.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    private void stopAnimation() {
        if (!isAnimating) return;
        isAnimating = false;
        animator.cancel();
    }
}
