package com.js.sample.views.load;

// Created by JS on 2021/2/22.

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.js.sample.R;
import com.ktw.ktwlib.util.NavigationBarUtil;
import com.ktw.ktwlib.util.StatusBarUtil;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        StatusBarUtil.setStatusBarColor(this, R.color.black);
        NavigationBarUtil.setSysNavigationColor(this, Color.parseColor("#000000"));
    }
}
