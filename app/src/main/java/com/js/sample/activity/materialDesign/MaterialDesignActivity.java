package com.js.sample.activity.materialDesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.js.sample.R;


// Created by JS on 2020/1/11.

public class MaterialDesignActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
    }

    /**
     * 跳转到BottomSheetSample页面
     * @param view view
     */
    public void clickBottomSheetSampleActivity(View view) {
        Intent intent = new Intent(this, BottomSheetSampleActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到CollapsingToolbarLayout页面
     * @param view view
     */
    public void clickCollapsingToolbarLayoutActivity(View view) {
        Intent intent = new Intent(this, CollapsingToolbarLayoutActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到CoordinatorLayout滑动使用页面
     * @param view view
     */
    public void clickCoordinatorLayoutScrollActivity(View view) {
        Intent intent = new Intent(this, CoordinatorLayoutScrollActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到浮动按钮使用页面
     * @param view view
     */
    public void clickFloatActionButtonActivity(View view) {
        Intent intent = new Intent(this, FloatActionButtonActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到NavigationView界面
     * @param view view
     */
    public void clickNavigationViewActivity(View view) {
        Intent intent = new Intent(this, NavigationViewActivity.class);
        startActivity(intent);
    }
}
