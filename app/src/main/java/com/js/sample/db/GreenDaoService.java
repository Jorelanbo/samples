package com.js.sample.db;

// Created by JS on 2019/1/28.

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.js.sample.SampleApplication;
import com.js.sample.entity.User;
import com.js.sample.gen.DaoSession;
import com.js.sample.gen.UserDao;


public class GreenDaoService {
    private static final String TAG = "GreenDaoService";
    private static GreenDaoService sGreenDaoService;

    private Context mContext;
    private DaoSession mDaoSession;
    private UserDao mUserDao;

    private GreenDaoService(Context context) {
        mContext = context.getApplicationContext();
        mDaoSession = SampleApplication.getDaoSession();
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
        User findUser = mUserDao.queryBuilder().where(UserDao.Properties.Id.eq(3)).build().unique();
        if (findUser != null) {
            mUserDao.delete(findUser);
            Log.i(TAG, "用户已删除！");
            Toast.makeText(mContext, "用户已删除！", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "找不到该用户！");
            Toast.makeText(mContext, "找不到该用户！", Toast.LENGTH_SHORT).show();
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
