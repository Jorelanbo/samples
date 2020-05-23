package com.js.sample.materialDesign.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import com.js.sample.R;


// Created by JS on 2020/1/13.

public class FloatActionButtonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_action_button);
        FloatingActionButton fab = findViewById(R.id.fab_four);
        fab.setOnClickListener(v -> Snackbar.make(v, "正在打开邮件...", Snackbar.LENGTH_LONG).show());
    }
}
