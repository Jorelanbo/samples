package com.js.sample.materialDesign.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.js.sample.R;


// Created by JS on 2020/1/14.

public class PaletteSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_sample);
    }

    /**
     * 跳转到调色板的基本用法页面
     * @param view view
     */
    public void clickPaletteBasicSampleActivity(View view) {
        Intent intent = new Intent(this, PaletteBasicSampleActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到调色板的示例用法页面
     * @param view view
     */
    public void clickPaletteAdvanceSampleActivity(View view) {
        Intent intent = new Intent(this, PaletteAdvanceSampleActivity.class);
        startActivity(intent);
    }
}
