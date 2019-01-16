package com.js.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.js.sample.utils.BDLocationUtils;


// Created by JS on 2019/1/15.

public class BaiduLocationActivity extends AppCompatActivity {

    private BDLocationUtils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidulocation);
        utils = new BDLocationUtils(BaiduLocationActivity.this);
    }

    public void getLocation(View view) {
        utils.doLocation();
        utils.mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        utils.mLocationClient.stop();
    }
}
