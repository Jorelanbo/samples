package com.js.sample.activity.materialDesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.js.sample.R;


// Created by JS on 2020/1/15.

public class TabLayoutSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_sample);
    }

    /**
     * 跳转到基本使用页面
     * @param view view
     */
    public void clickTabLayoutBasicSampleActivity(View view) {
        Intent intent = new Intent(this, TabLayoutBasicSampleActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到与viewpager联动的页面
     * @param view view
     */
    public void clickTabLayoutAndViewPagerActivity(View view) {
        Intent intent = new Intent(this, TabLayoutAndViewPagerActivity.class);
        startActivity(intent);
    }
}
