package com.js.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
}
