package com.js.sample.servicetest.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.js.sample.R;


// Created by JS on 2019/11/1.

public class ServiceTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
    }

    /**
     * 跳转IntentServiceActivity
     *
     * @param view view
     */
    public void goIntentServiceActivity(View view) {
        Intent intent = new Intent(this, IntentServiceActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转NormalServiceActivity
     *
     * @param view view
     */
    public void goNormalServiceActivity(View view) {
        Intent intent = new Intent(this, NormalServiceActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转BindingServiceActivity
     *
     * @param view view
     */
    public void goBindingServiceActivity(View view) {
        Intent intent = new Intent(this, BindingServiceActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转前台服务页面
     * @param view view
     */
    public void goFrontServiceServiceActivity(View view) {
        Intent intent = new Intent(this, FrontGroundServiceActivity.class);
        startActivity(intent);
    }
}
