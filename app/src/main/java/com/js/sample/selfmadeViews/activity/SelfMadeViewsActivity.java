package com.js.sample.selfmadeViews.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.js.sample.R;


// Created by JS on 2020/5/19.

public class SelfMadeViewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_made_views);
    }

    /**
     * 进入自绘控件演示页面
     * @param view view
     */
    public void clickSelfDrawingViewsActivity(View view) {
        Intent intent = new Intent(this, SelfDrawingViewsActivity.class);
        startActivity(intent);
    }

    /**
     * 进入组合控件演示页面
     * @param view view
     */
    public void clickCombineViewsActivity(View view) {
        Intent intent = new Intent(this, CombinationViewActivity.class);
        startActivity(intent);
    }

    /**
     * 进入继承控件演示页面
     * @param view view
     */
    public void clickInheritedViewsActivity(View view) {
        Intent intent = new Intent(this, InheritedViewActivity.class);
        startActivity(intent);
    }
}
