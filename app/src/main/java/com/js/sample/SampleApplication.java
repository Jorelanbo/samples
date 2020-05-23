package com.js.sample;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.multidex.MultiDex;

import com.js.sample.gen.DaoMaster;
import com.js.sample.gen.DaoSession;


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
        MultiDex.install(this);
        initGreenDao();
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
