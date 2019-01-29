package com.js.sample;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.js.sample.gen.DaoMaster;
import com.js.sample.gen.DaoSession;
import com.js.sample.gen.UserDao;


// Created by JS on 2019/1/28.

public class SampleApplication extends Application {

    private DaoSession daoSession;
    private DaoMaster daoMaster;

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

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
