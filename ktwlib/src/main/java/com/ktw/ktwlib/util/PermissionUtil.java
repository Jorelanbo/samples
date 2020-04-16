package com.ktw.ktwlib.util;

// Created by JS on 2019/1/30.

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PermissionUtil {

    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_LOCATION = 2;
    public static final int REQUEST_CAMERA = 3;
    public static final int REQUEST_IMEI = 4;
    public static final int REQUEST_INTERNET = 5;


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

    private static String[] PERMISSION_INTERNET = {
            Manifest.permission.INTERNET
    };

    /**
     * 检查内部存储读写权限
     * @param context c
     */
    public static boolean checkExternalStoragePermission (Context context) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查拍照权限
     * @param context c
     */
    public static boolean checkCameraPermission (Context context) {
        try {
            //检测是否有使用相机的权限
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有使用相机的权限，去申请权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_CAMERA, REQUEST_CAMERA);
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查定位权限
     * @param context c
     */
    public static boolean checkLocationPermission (Context context) {
        try {
            //检测是否有获取位置的权限
            int permission1 = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int permission2 = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permission1 != PackageManager.PERMISSION_GRANTED && permission2 != PackageManager.PERMISSION_GRANTED) {
                // 没有获取位置的权限，去申请获取位置的权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_LOCATION, REQUEST_LOCATION);
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查手机状态信息的权限
     * @param context c
     */
    public static boolean checkGetIMEIPermission (Context context) {
        try {
            //检测是否有获取手机状态的权限
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有获取手机状态的权限，去申请权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSION_IMEI, REQUEST_IMEI);
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkInternetPermission (Context context) {
        try {
            //检测是否有获取手机状态的权限
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.INTERNET);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有获取手机状态的权限，去申请权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSION_INTERNET, REQUEST_INTERNET);
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                        .getPackageName());
                return Settings.canDrawOverlays(context) || mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }
}
