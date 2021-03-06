package com.js.sample.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.js.sample.R;
import com.js.sample.utils.BDLocationUtils;
import com.js.sample.utils.PermissionUtil;


// Created by JS on 2019/1/15.

public class BaiduLocationActivity extends AppCompatActivity {

    private BDLocationUtils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidulocation);
        PermissionUtil.checkLocationPermission(this);
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
