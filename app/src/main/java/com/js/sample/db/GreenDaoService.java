package com.js.sample.db;

// Created by JS on 2019/1/28.

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.js.sample.SampleApplication;
import com.js.sample.entity.User;
import com.js.sample.gen.DaoSession;
import com.js.sample.gen.UserDao;

import java.util.List;

public class GreenDaoService {
    private static final String TAG = "GreenDaoService";
    private static GreenDaoService sGreenDaoService;

    private Context mContext;
    private DaoSession mDaoSession;
    private UserDao mUserDao;

    private GreenDaoService(Context context) {
        mContext = context;
        mDaoSession = ((SampleApplication)((Activity)mContext).getApplication()).getDaoSession();
        mUserDao = mDaoSession.getUserDao();
    }

    public static GreenDaoService getInstance(Context context) {
        if (sGreenDaoService == null) {
            sGreenDaoService = new GreenDaoService(context);
        }
        return sGreenDaoService;
    }

    public void insertUser(User user) {
        mUserDao.insert(user);
    }

    public void deleteUser() {
        User findUser = mDaoSession.getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(3)).build().unique();
        Log.i(TAG, findUser.toString());
        if (findUser != null) {
            mUserDao.delete(findUser);
        }
    }

    public void updateUser() {
        User findUser = mUserDao.queryBuilder().where(UserDao.Properties.Id.eq(1)).build().unique();
        if(findUser != null) {
            findUser.setName("Jorelanbo");
            mUserDao.update(findUser);
            Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public User queryUser() {
        User findUser = mUserDao.queryBuilder().where(UserDao.Properties.Id.eq(2)).build().unique();
        Log.i(TAG, findUser.toString());
        return findUser;
    }
}
