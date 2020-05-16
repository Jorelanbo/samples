package com.js.sample.servicetest.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.js.sample.R;
import com.js.sample.servicetest.listener.OnProgressListener;
import com.js.sample.servicetest.service.BindingService;


public class BindingServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BindingServiceActivity";

    private static final int ON_RECEIVE_PROGRESS = 1;

    private Button btnStartService;
    private Button btnPauseService;
    private Button btnRestartService;
    private ProgressBar progressbar;
    private TextView tvProgress;

    BindingService mService;
    boolean mBound = false;
    private int mProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_service);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, BindingService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    private void initView() {
        btnStartService = findViewById(R.id.btn_start_service);
        btnPauseService = findViewById(R.id.btn_pause_service);
        btnRestartService = findViewById(R.id.btn_restart_service);
        progressbar = findViewById(R.id.progressbar);
        tvProgress = findViewById(R.id.tv_progress);
        btnStartService.setOnClickListener(this);
        btnPauseService.setOnClickListener(this);
        btnRestartService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                startDownload();
                break;
            case R.id.btn_pause_service:
                pauseDownload();
                break;
            case R.id.btn_restart_service:
                restartDownload();
                break;
        }
    }

    /**
     *  开始下载
     */
    private void startDownload() {
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            int num = mService.getRandomNumber();
            Toast.makeText(BindingServiceActivity.this, "number: " + num, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "number: " + num);
            mProgress = 0;
            mService.setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(int progress) {
                    mProgress = progress;
                    // 因为onProgress方法是在服务中开启的线程中运行的，所以不能在这里修改UI
                    uiHandler.sendEmptyMessage(ON_RECEIVE_PROGRESS);
                }
            });
            mService.resumeDownload();
            mService.startDownload();
//            listenProgress();
        }
    }

    /**
     *  暂停下载
     */
    private void pauseDownload() {
        if (mBound) mService.pauseDownload();
    }

    /**
     *  继续下载
     */
    private void restartDownload() {
        if (mBound) mService.resumeDownload();
    }

    /**
     *  监听进度，每秒钟获取调用BindingService的getProgress()方法来获取进度，更新UI
     */
    private void listenProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress < BindingService.MAX_PROGRESS) {
                    mProgress = mService.getProgress();
                    uiHandler.sendEmptyMessage(ON_RECEIVE_PROGRESS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ON_RECEIVE_PROGRESS:
                    progressbar.setProgress(mProgress);
                    tvProgress.setText("进度：" + mProgress + "%");
                    break;
            }
        }
    };

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BindingService.LocalBinder binder = (BindingService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };
}
