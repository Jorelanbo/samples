package com.js.sample.Const;

// Created by JS on 2019/1/15.

import android.os.Environment;

public class Const {
    public static double LONGITUDE = 0;// 经度
    public static double LATITUDE = 0;// 纬度
    public static String ADDRESS = "";// 地址

    // 文件位置
    public static final String APP_FOLDER = Environment.getExternalStorageDirectory().getPath() + "/SAMPLES";
    // 照片位置
    public static final String APP_PICS = APP_FOLDER + "/images";
    // 视频位置
    public static final String APP_VIDEOS = APP_FOLDER + "/videos";
}
