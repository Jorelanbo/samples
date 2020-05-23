package com.js.sample.notifycation;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.js.sample.R;
import com.js.sample.activity.MainActivity;
import com.js.sample.utils.Util;


// Created by JS on 2020/5/18.

public class NotificationTestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnClear;
    private Button btnShow;
    private Button btnPrint;
    private TextView tx1;
    private TextView tx2;

    public static final String ACTION_BUTTON = "intent_action";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder interactionBuilder;
    private RemoteViews mRemoteViews;
    private ButtonBroadcastReceiver bReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_notification_test);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "interaction";
            channelName = "互动消息";
            importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
        }
        initView();
        initBttonReceiver(); // 注册广播接受者
    }

    private void initView() {
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        btnClear = findViewById(R.id.btn_clear);
        btnPrint = findViewById(R.id.btn_print);
        btnShow = findViewById(R.id.btn_show);
        btnClear.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true); // 设置显示应用图标的角标
        mNotificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show:
                if (interactionBuilder == null) {
                    return;
                }
                mNotificationManager.notify(3, interactionBuilder.build());
                break;
            case R.id.btn_clear:
                mNotificationManager.cancel(3);
                break;
            case R.id.btn_print:
                if (interactionBuilder == null) {
                    return;
                }
                k ++;
                mRemoteViews.setTextViewText(R.id.showTv, k + "");
                mNotificationManager.notify(3, interactionBuilder.build());
                break;
            default:
                break;
        }
    }

    /**
     * 发送聊天消息
     * @param view view
     */
    public void sendChatMsg(View view) {
        // 检查是否开启聊天通知，没有开启则提示进入设置页面开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = mNotificationManager.getNotificationChannel("chat");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Util.showToast(this, "请手动将通知打开");
            }
        }
        Intent chatIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, chatIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.better)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.better))
                .setAutoCancel(true)
                .setNumber(5) // 设置角标数字
                .setContentIntent(pendingIntent) // 点击进入的页面
                .build();
        mNotificationManager.notify(1, notification);
    }

    /**
     * 发送订阅消息
     * @param view view
     */
    public void sendSubscribeMsg(View view) {
        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.better)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.better))
                .setAutoCancel(true)
                .setNumber(3) // 设置角标数字
                .build();
        mNotificationManager.notify(2, notification);
    }

    /**
     * 发送互动消息
     * @param view view
     */
    public void sendInteractionMsg(View view) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        interactionBuilder = new NotificationCompat.Builder(this, "interaction");

        interactionBuilder.setAutoCancel(false);//点击是否后消失
        interactionBuilder.setSmallIcon(R.mipmap.better);//设置通知栏消息标题的头像
        interactionBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        interactionBuilder.setTicker("互动通知");
        interactionBuilder.setContentTitle("收到一条互动通知");
        interactionBuilder.setOngoing(true);

        // 设置点击通知跳转
        Intent intent = new Intent(this, MainActivity.class);//将要跳转的界面
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//activity启动模式为：singleTop
        // 假如在同一个requestCode下，使用FLAG_CANCEL_CURRENT，会把旧的广播消息Intent 中的extra清除掉
        PendingIntent intentPend = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        interactionBuilder.setContentIntent(intentPend);


        // 设置通知的自定义样式
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        mRemoteViews.setViewVisibility(R.id.btn, View.VISIBLE);
        Intent buttonIntent =new Intent(ACTION_BUTTON);
        buttonIntent.putExtra("ButtonId", 1);
        //假如在同一个requestCode下，使用FLAG_UPDATE_CURRENT，最新接收的广播消息中的Intent的extra会替换掉旧的广播消息Intent的extra
        PendingIntent intent_btn = PendingIntent.getBroadcast(this,1,buttonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn,intent_btn);

        buttonIntent.putExtra("ButtonId",2);
        intent_btn = PendingIntent.getBroadcast(this,2,buttonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn2,intent_btn);

        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        interactionBuilder.setContent(mRemoteViews);

        //刷新通知
        mNotificationManager.notify(3, interactionBuilder.build());
    }

    //初始化广播接收器
    public void initBttonReceiver() {
        bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        registerReceiver(bReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bReceiver);
    }

    private int i=0;
    private int j=0;
    private int k=0;

    //广播接收类
    public  class ButtonBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(ACTION_BUTTON)){
                int buttonId=intent.getIntExtra("ButtonId",0);
                switch (buttonId){
                    case 1:
                        i++;
                        tx1.setText("点击了←"+i+"次");
                        break;
                    case 2:
                        j++;
                        tx2.setText("点击了→"+j+"次");
                    default:
                        break;
                }
            }
        }
    }
}
