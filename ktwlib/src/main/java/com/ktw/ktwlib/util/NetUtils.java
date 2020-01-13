package com.ktw.ktwlib.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by viroyal-android01 on 2016/7/21.
 * 跟网络相关的工具类
 */
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 检查网络
     *
     * @param context
     * @return
     */
    public static boolean checkNet(Context context) {
        // 判断是不是WIFI在连接
        boolean isWifi = isWifiConnection(context);
        // 判断是否是APN中的某项进行连接
        boolean isApn = isApnConnection(context);

        // 如果两个都不能联网，需要提示用户
        if (!isWifi && !isApn) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否APN连接（通过sim卡连接基站）
     *
     * @param context
     * @return
     */
    private static boolean isApnConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断是否WIFI连接
     *
     * @param context
     * @return
     */
    private static boolean isWifiConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断手机的连接状态
     *
     * @param context
     * @return
     */
    public static boolean isConnectedAndToastType(Context context) {
        // 连接管理器
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取连接的网络信息
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {// 2g_3g_4g连上
            Toast.makeText(context, "当前为SIM卡连接", Toast.LENGTH_SHORT).show();
            return true;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {// wifi连上
            Toast.makeText(context, "当前为WiFi连接", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    // 判断网络连接是否正常
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断网络是否连接
     *
     * @param context 上下文
     * @return 是否连接 true|false
     */
    public static boolean isNetWorkConnected(Context context)
    {
        if(null != context)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if(null != mNetworkInfo)
            {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断ip、端口是否可连接
     * @param host
     * @param port
     * @return
     */
    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port),15000);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
