package com.js.sample.servicetest.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.js.sample.R;
import com.js.sample.activity.GuideActivity;


// Created by JS on 2020/5/15.

public class FrontGroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, GuideActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        NotificationCompat.Builder builder ;
        int channelId = 1 ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){        //Android 8.0适配
            NotificationChannel channel = new NotificationChannel(String.valueOf(channelId),
                    "channel_name",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this,String.valueOf(channelId));
        }else{
            builder = new NotificationCompat.Builder(this);
        }
        builder.setContentTitle("this is content title")            //指定通知栏的标题内容
                .setContentText("this is content text")             //通知的正文内容
                .setWhen(System.currentTimeMillis())                //通知创建的时间
                .setSmallIcon(R.mipmap.ic_launcher)    //通知显示的小图标，只能用alpha图层的图片进行设置
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                .setContent() // 设置自定义布局
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
//        startForeground(1, notification);
        manager.notify(channelId, notification);;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
