package com.js.sample;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.js.sample.gen.DaoMaster;
import com.js.sample.gen.DaoSession;


// Created by JS on 2019/1/28.

public class SampleApplication extends Application {

    private static DaoSession daoSession;
    private static DaoMaster daoMaster;
    private static SampleApplication sSampleApplication;

    public static SampleApplication getInstance(){
        if (sSampleApplication == null) {
            sSampleApplication = new SampleApplication();
        }
        return sSampleApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
