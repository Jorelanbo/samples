package com.js.sample.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "BindingService destroyed", Toast.LENGTH_SHORT).show();
    }
}
