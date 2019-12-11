package com.js.sample.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewStub;
import android.widget.ImageView;

import com.js.sample.R;
import com.js.sample.fragment.SplashFragment;

import java.lang.ref.WeakReference;


// Created by JS on 2019/3/18.

public class SplashActivity extends FragmentActivity {

    private Handler mHandler = new Handler();
    private SplashFragment splashFragment;
    private ViewStub mViewStub;

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mViewStub = findViewById(R.id.content_viewstub);

        // 首先加载并显示splash画面
        splashFragment = new SplashFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, splashFragment);
        transaction.commit();

        // 1、判断当窗体加载完毕时，立马加载真正的布局进来。
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                // 开启延时加载
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mViewStub.inflate(); // 将viewstub加载进来，加载完毕后控件正常使用
                    }
                });
            }
        });

        // 2、判断当窗体加载完毕的时候执行，延时一段时间做动画。
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                // 开启延时加载，实现fragment里面的动画效果
                mHandler.postDelayed(new DelayRunnable(SplashActivity.this, splashFragment), 2000);
                // mHandler.post(new DelayRunnable());//不开启延迟加载
            }
        });
    }

    static class DelayRunnable implements Runnable{

        private WeakReference<Context> contextRef;
        private WeakReference<SplashFragment> fragmentRef;

        DelayRunnable(Context context, SplashFragment splashFragment) {
            contextRef = new WeakReference<Context>(context);
            fragmentRef = new WeakReference<SplashFragment>(splashFragment);
        }

        @Override
        public void run() {
            // 移除splash画面
            if (contextRef != null) {
                SplashFragment splashFragment = fragmentRef.get();
                if (splashFragment == null) {
                    return;
                }
                FragmentActivity activity = (FragmentActivity) contextRef.get();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.remove(splashFragment);
                transaction.commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
