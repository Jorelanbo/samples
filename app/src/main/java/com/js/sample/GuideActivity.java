package com.js.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    public void clickMain(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickActivityLifeCycle(View view) {
        Intent intent = new Intent(this, LifeCycleActivity.class);
        startActivity(intent);
    }

    public void clickFragmentLifeCycle(View view) {
        Intent intent = new Intent(this, FragmentLifeCycleActivity.class);
        startActivity(intent);
    }

    /**
     *
     *  窗口模式的activity
     * @param view
     */
    public void clickDialogActivity(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }
}
