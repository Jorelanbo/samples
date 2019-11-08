package com.js.sample.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


// Created by JS on 2019/11/1.

public class HelloIntentService extends IntentService {

    private static final String TAG = "HelloIntentService";
    private int flag = 0;

    public HelloIntentService() {
        super("HelloIntentService");
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId) {
        Toast.makeText(this, "HelloIntentService starting", Toast.LENGTH_SHORT).show();
        flag += 1;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        assert intent != null;
        String id = intent.getStringExtra("ID");
        String code = intent.getStringExtra("code");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Log.i(TAG, "onHandleIntent: ID=" + id + ",code=" + code + ",flag=" + flag);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
