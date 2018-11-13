package com.js.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LifeCycleActivity extends AppCompatActivity {
    private static final String TAG = "LifeCycleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
        Log.i(TAG, "LifeCycleActivity: onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "LifeCycleActivity: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "LifeCycleActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "LifeCycleActivity: onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "LifeCycleActivity: onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "LifeCycleActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "LifeCycleActivity: onDestory");
    }
}
