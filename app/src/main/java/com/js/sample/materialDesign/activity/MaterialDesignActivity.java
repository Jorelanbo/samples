package com.js.sample.materialDesign.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

    /**
     * 跳转到调色板使用页面
     * @param view view
     */
    public void clickPaletteSimpleActivity(View view) {
        Intent intent = new Intent(this, PaletteSampleActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到snackBar示例页面
     * @param view view
     */
    public void clickSnackBarSimpleActivity(View view) {
        Intent intent = new Intent(this, SnackBarSampleActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到TabLayout示例页面
     * @param view view
     */
    public void clickTabLayoutSimpleActivity(View view) {
        Intent intent = new Intent(this, TabLayoutSampleActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到TextInputLayout示例页面
     * @param view view
     */
    public void clickTextInputLayoutSimpleActivity(View view) {
        Intent intent = new Intent(this, TextInputLayoutSampleActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到ToolBar示例页面
     * @param view view
     */
    public void clickToolBarSimpleActivity(View view) {
        Intent intent = new Intent(this, ToolBarSampleActivity.class);
        startActivity(intent);
    }
}
