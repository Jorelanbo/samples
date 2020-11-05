package com.js.sample;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.multidex.MultiDex;

import com.js.sample.Const.Const;
import com.js.sample.gen.DaoMaster;
import com.js.sample.gen.DaoSession;
import com.ktw.ktwlib.util.LogUtils;

import java.io.File;


// Created by JS on 2019/1/28.

public class SampleApplication extends Application {


    public static DaoSession daoSession;
    public static DaoMaster daoMaster;
    public static SampleApplication sSampleApplication;

    public static SampleApplication getInstance(){
        if (sSampleApplication == null) {
            sSampleApplication = new SampleApplication();
        }
        return sSampleApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.isShowLog = BuildConfig.LOG_DEBUG;
        createFolers();
        MultiDex.install(this);
        initGreenDao();
    }

    private void createFolers() {
        File imageFolder = new File(Const.APP_PICS);
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        }
        File videoFolder = new File(Const.APP_VIDEOS);
        if (!videoFolder.exists()) {
            videoFolder.mkdir();
        }
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "sample.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
