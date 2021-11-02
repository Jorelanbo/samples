package com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.view;

// Created by JS on 2021/2/25.

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.ktw.ktwlib.R;
import com.ktw.ktwlib.util.LogUtils;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.interfaces.IRefreshHeader;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.util.WeakHandler;

public class BaiDuRefreshHeader extends LinearLayout implements IRefreshHeader {

    private static final String TAG = "BaiDuRefreshHeader";

    private RelativeLayout mContainer;
    private ImageView ivWheel1,ivWheel2;    //轮组图片组件
    private ImageView ivRider;  //骑手图片组件
    private ImageView ivSun,ivBack1,ivBack2;    //太阳、背景图片1、背景图片2
    private Animation wheelAnimation,sunAnimation;  //轮子、太阳动画
    private Animation backAnimation1,backAnimation2;    //两张背景图动画

    private int mMeasuredHeight;

    private int mState = STATE_NORMAL;

    private WeakHandler mHandler = new WeakHandler();

    private boolean isAnimating = false; // 是否正在动画

    public BaiDuRefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public BaiDuRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        mContainer = (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.headview, null);
        addView(mContainer, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        mContainer.setGravity(Gravity.BOTTOM);

        //获取头布局图片组件
        ivRider = (ImageView) mContainer.findViewById(R.id.iv_rider);
        ivSun = (ImageView) mContainer.findViewById(R.id.ivsun);
        ivWheel1 = (ImageView) mContainer.findViewById(R.id.wheel1);
        ivWheel2 = (ImageView) mContainer.findViewById(R.id.wheel2);
        ivBack1 = (ImageView) mContainer.findViewById(R.id.iv_back1);
        ivBack2 = (ImageView) mContainer.findViewById(R.id.iv_back2);
        //获取动画
        wheelAnimation = AnimationUtils.loadAnimation(context, R.anim.tip);
        sunAnimation = AnimationUtils.loadAnimation(context, R.anim.tip1);
        LinearInterpolator lir = new LinearInterpolator();
        wheelAnimation.setInterpolator(lir);
        sunAnimation.setInterpolator(lir);

        backAnimation1 = AnimationUtils.loadAnimation(context, R.anim.a);
        backAnimation2 = AnimationUtils.loadAnimation(context, R.anim.b);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
        LogUtils.d(TAG, "测得的初始高度：" + mMeasuredHeight);
    }

    private void setState(int state) {
        if (state == mState) return;
        switch (state) {
            case STATE_NORMAL:
                LogUtils.d(TAG, "刷新归位");
                stopAnim();
                break;
            case STATE_RELEASE_TO_REFRESH:
                LogUtils.d(TAG, "准备刷新");
                startAnim(0);
                break;
            case STATE_REFRESHING:
                LogUtils.d(TAG, "刷新中");
                startAnim(1);
                break;
            case STATE_DONE:
                LogUtils.d(TAG, "刷新完成");
                //设置headerView的padding为隐藏
                mContainer.setPadding(0, -mMeasuredHeight, 0, 0);
                break;
        }
        mState = state;
    }

    @Override
    public void onReset() {
        setState(STATE_NORMAL);
    }

    @Override
    public void onPrepare() {
        setState(STATE_RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshing() {
        setState(STATE_REFRESHING);
    }

    @Override
    public void onMove(float offSet, float sumOffSet) {
        int top = getTop();
        if (offSet > 0 && top == 0) {
            setVisibleHeight((int) offSet + getVisibleHeight());
        } else if (offSet < 0 && getVisibleHeight() > 0) {
            layout(getLeft(), 0, getRight(), getHeight());
            setVisibleHeight((int) offSet + getVisibleHeight());
        }
        if (mState <= STATE_RELEASE_TO_REFRESH) {
            if (getVisibleHeight() > mMeasuredHeight) {
                onPrepare();
            } else {
                onReset();
            }
        }
    }

    @Override
    public boolean onRelease() {
        boolean isOnRefresh = false;
        int visibleHeight = getVisibleHeight();
        if (visibleHeight == 0) {
            isOnRefresh = false;
        }

        if (visibleHeight > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }

        if (visibleHeight > mMeasuredHeight && mState == STATE_REFRESHING) {
            smoothScrollTo(mMeasuredHeight);
        }

        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0);
        }

        if (mState == STATE_REFRESHING) {
            smoothScrollTo(mMeasuredHeight);
        }
        return isOnRefresh;
    }

    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 200);
    }

    private void reset() {
        smoothScrollTo(0);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);
    }

    @Override
    public View getHeaderView() {
        return this;
    }

    private void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        ViewGroup.LayoutParams lp = mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    @Override
    public int getVisibleHeight() {
        ViewGroup.LayoutParams lp = mContainer.getLayoutParams();
        return lp.height;
    }

    @Override
    public int getVisibleWidth() {
        return 0;
    }

    @Override
    public int getType() {
        return TYPE_HEADER_NORMAL;
    }

    /**
     * 开启动画
     */
    public void startAnim(int i){
        LogUtils.d(TAG, "开始动画");
        if (i == 0) {
            LogUtils.d(TAG, "刷新前的动画0");
        }
        if (i == 1) {
            LogUtils.d(TAG, "刷新中的动画1");
        }
        ivBack1.clearAnimation();
        ivBack2.clearAnimation();
        ivSun.clearAnimation();
        ivWheel1.clearAnimation();
        ivWheel2.clearAnimation();

        ivBack1.startAnimation(backAnimation1);
        ivBack2.startAnimation(backAnimation2);
        ivSun.startAnimation(sunAnimation);
        ivWheel1.startAnimation(wheelAnimation);
        ivWheel2.startAnimation(wheelAnimation);
    }

    /**
     * 关闭动画
     */
    public void stopAnim(){
        ivBack1.clearAnimation();
        ivBack2.clearAnimation();
        ivSun.clearAnimation();
        ivWheel1.clearAnimation();
        ivWheel2.clearAnimation();
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
