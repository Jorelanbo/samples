package com.js.sample.utils;

// Created by JS on 2019/1/30.

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionUtil {

    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_LOCATION = 2;
    public static final int REQUEST_CAMERA = 3;
    public static final int REQUEST_IMEI = 4;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    private static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA
    };

    private static String[] PERMISSION_IMEI = {
            Manifest.permission.READ_PHONE_STATE
    };

    /**
     * 检查内部存储读写权限
     * @param context c
     */
    public static void checkExternalStoragePermission (Context context) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查拍照权限
     * @param context c
     */
    public static void checkCameraPermission (Context context) {
        try {
            //检测是否有使用相机的权限
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有使用相机的权限，去申请权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_CAMERA, REQUEST_CAMERA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查定位权限
     * @param context c
     */
    public static void checkLocationPermission (Context context) {
        try {
            //检测是否有获取位置的权限
            int permission1 = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int permission2 = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permission1 != PackageManager.PERMISSION_GRANTED && permission2 != PackageManager.PERMISSION_GRANTED) {
                // 没有获取位置的权限，去申请获取位置的权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_LOCATION, REQUEST_LOCATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查手机状态信息的权限
     * @param context c
     */
    public static void checkGetIMEIPermission (Context context) {
        try {
            //检测是否有获取手机状态的权限
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有获取手机状态的权限，去申请权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSION_IMEI, REQUEST_IMEI);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
