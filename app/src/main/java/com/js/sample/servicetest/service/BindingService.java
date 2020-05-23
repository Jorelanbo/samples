package com.js.sample.servicetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.widget.Toast;

import com.js.sample.servicetest.listener.OnProgressListener;

import java.util.Random;

// 当服务需要与前台进行数据交互时使用bind服务的方式

// Created by JS on 2019/11/1.

public class BindingService extends Service {
    private static final String TAG = "BindingService";
    // Binder given to clients
    private final IBinder binder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * 进度条的最大进度
     */
    public static final int MAX_PROGRESS = 100;
    /**
     * 进度条的进度值
     */
    private int progress = 0;

    /**
     * 是否在暂停中
     */
    private boolean isPausing = false;

    /**
     *  进度更新的回调接口
     */
    private OnProgressListener onProgressListener;
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public BindingService getService() {
            // Return this instance of LocalService so clients can call public methods
            return BindingService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /** method for clients */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    /**
     * 返回进度值
     * @return 下载的进度
     */
    public int getProgress() {
        return progress;
    }

    /**
     *  暂停下载
     */
    public void pauseDownload() {
        isPausing = true;
    }

    /**
     *  允许下载
     */
    public void resumeDownload() {
        isPausing = false;
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    /**
     * 模拟下载任务，每秒更新一次
     */
    public void startDownload() {
        progress = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress < MAX_PROGRESS) {
                    if (!isPausing) {
                        progress += 2;
                        if (onProgressListener != null) {
                            onProgressListener.onProgress(progress);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "BindingService destroyed", Toast.LENGTH_SHORT).show();
    }
}
