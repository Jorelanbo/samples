package com.js.sample.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.js.sample.R;

import java.lang.reflect.Method;


// Created by JS on 2020/5/16.

public class CheckStoragePathActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "StoragePathActivity";

    private Button btnCheckStoragePath;
    private TextView tvPathText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_storage_path);
        initView();
    }

    private void initView() {
        btnCheckStoragePath = findViewById(R.id.btn_check_storage_path);
        tvPathText = findViewById(R.id.tv_path_text);
        btnCheckStoragePath.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check_storage_path:
                String[] data = getExtSDCardPath();
                if (data.length>0){
                    StringBuilder pathText = new StringBuilder();
                    for (String tmp:data){
                        pathText.append(tmp).append("：")
                                .append(checkMounted(this, tmp))
                                .append("\n");
                        Log.d(TAG,tmp+"："+checkMounted(this,tmp));
                    }
                    tvPathText.setText(pathText.toString());
                } else {
                    Log.d(TAG,"未挂载");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取所有存储点：
     * @return
     */
    public String[] getExtSDCardPath() {
        StorageManager storageManager = (StorageManager)getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
            return (String[])invoke;
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据路径来判断具体sd卡是否挂载：
    public static boolean checkMounted(Context context, String mountPoint) {
        if (mountPoint == null) {
            return false;
        }
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Method getVolumeState = storageManager.getClass().getMethod("getVolumeState", String.class);
            String state = (String) getVolumeState.invoke(storageManager, mountPoint);
            return Environment.MEDIA_MOUNTED.equals(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
