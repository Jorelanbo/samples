package com.js.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    /**
     *  主页面
     * @param view view
     */
    public void clickMain(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * activity 生命周期
     * @param view view
     */
    public void clickActivityLifeCycle(View view) {
        Intent intent = new Intent(this, LifeCycleActivity.class);
        startActivity(intent);
    }

    /**
     * fragment 生命周期
     * @param view view
     */
    public void clickFragmentLifeCycle(View view) {
        Intent intent = new Intent(this, FragmentLifeCycleActivity.class);
        startActivity(intent);
    }

    /**
     *
     *  窗口模式的activity
     * @param view view
     */
    public void clickDialogActivity(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    /**
     *
     * logger框架
     * @param view view
     */
    public void clickLoggerActivity(View view) {
        Intent intent = new Intent(this, LoggerActivity.class);
        startActivity(intent);
    }

    public void clickAsyncTask(View view) {
        Intent intent = new Intent(this, AsyncTaskActivity.class);
        startActivity(intent);
    }
}
