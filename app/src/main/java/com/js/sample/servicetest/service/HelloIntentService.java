package com.js.sample.servicetest.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.js.sample.servicetest.activity.IntentServiceActivity;


// Created by JS on 2019/11/1.

/**
 * 1、IntentService 是继承自 Service 并处理异步请求的一个类，在 IntentService 内有一个工作线程来处理耗时操作。
 * 2、当任务执行完后，IntentService 会自动停止，不需要我们去手动结束。
 * 3、如果启动 IntentService 多次，那么每一个耗时操作会以工作队列的方式在 IntentService 的 onHandleIntent 回调方法中执行，
 *    依次去执行，使用串行的方式，执行完自动结束。
 */

public class HelloIntentService extends IntentService {

    private static final String TAG = "HelloIntentService";
    private int flag = 0;

    /**
     * 是否在运行
     */
    private boolean isRunning;

    /**
     * 进度
     */
    private int count;

    /**
     * 广播
     */
    private LocalBroadcastManager mLocalBroadcastManager;

    public HelloIntentService() {
        super("HelloIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId) {
        Toast.makeText(this, "HelloIntentService starting", Toast.LENGTH_SHORT).show();
        String id = intent.getStringExtra("ID");
        String code = intent.getStringExtra("code");
        Log.i(TAG, "onStartCommand: ID=" + id + ",code=" + code + ",flag=" + flag);
        flag += 1;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        assert intent != null;
        String id = intent.getStringExtra("ID");
        String code = intent.getStringExtra("code");
        try {
            Thread.sleep(1000);
            isRunning = true;
            count = 0;
            while (isRunning) {
                count ++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendThreadStatus("线程运行中...", count);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onHandleIntent: ID=" + id + ",code=" + code + ",flag=" + flag);
    }

    private void sendThreadStatus(String status, int progress) {
        Intent intent = new Intent(IntentServiceActivity.ACTION_TYPE_THREAD);
        intent.putExtra("status", status);
        intent.putExtra("progress", progress);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
