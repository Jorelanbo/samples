package com.js.sample.utils;

// Created by JS on 2019/1/15.

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.js.sample.Const.Const;

import java.util.ArrayList;
import java.util.List;

public class BDLocationUtils {
    public Context context;
    public LocationClient mLocationClient = null; // LocationClient类是定位SDK的核心类
    public MyLocationListener mListener = new MyLocationListener();

    public BDLocationUtils(Context context) {
        this.context = context;
    }

    public void doLocation() {
        // 声明LocationClient类
        mLocationClient = new LocationClient(context.getApplicationContext());
        // 注册监听函数
        mLocationClient.registerLocationListener(mListener);
        // 初始化定位
        initLocation();
    }

    private void initLocation() {

        LocationClientOption option = new LocationClientOption();

        // 可选，默认高精度，设置定位模式，高精度、低功耗、仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setOpenAutoNotifyMode();
        option.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);

        // 可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd0911");

        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        int span = 0;
        option.setScanSpan(span);

        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);

        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(false);

        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，
        // 结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);

        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，
        // 设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(true);

        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);

        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            StringBuilder sb = new StringBuilder(256);
            sb.append("time:");
            sb.append(bdLocation.getTime()); // 获取定位时间

            sb.append("\n ERROR CODE:");
            sb.append(bdLocation.getLocType()); // 获取定位类型

            sb.append("\nlatitude:");
            sb.append(bdLocation.getLatitude()); // 获取纬度

            sb.append("\nlongitude:");
            sb.append(bdLocation.getLongitude()); // 获取经度

            sb.append("\nradius:");
            sb.append(bdLocation.getRadius()); // 获取定位精度

            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {

                //  GPS定位结果
                sb.append("\nspeed:");
                sb.append(bdLocation.getSpeed()); // 获取速度，公里每小时

                sb.append("\nsatellite:");
                sb.append(bdLocation.getSatelliteNumber()); // 获取卫星数

                sb.append("\nheight:");
                sb.append(bdLocation.getAltitude()); // 获取海拔高度，单位米

                sb.append("\ndirection:");
                sb.append(bdLocation.getDirection()); // 获取方向，单位度

                sb.append("\naddress:");
                sb.append(bdLocation.getAddrStr()); // 获取地址

                sb.append("\ndescribe");
                sb.append("GPS定位成功！");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {

                // 网络定位结果
                sb.append("\naddress:");
                sb.append(bdLocation.getAddrStr()); // 获取地址

                sb.append("\noperationers:");
                sb.append(bdLocation.getOperators()); // 获取运营商

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            }  else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，" +
                        "处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }

            sb.append("\nlocationdescribe:");
            sb.append(bdLocation.getLocationDescribe()); // 位置语义化信息

            List<Poi> list = bdLocation.getPoiList(); // POI数据
            if (list != null) {
                sb.append("\npoilist size:");
                sb.append(list.size());

                for (Poi p : list) {
                    sb.append("\npoi=:");
                    sb.append(p.getId() + " " + p.getId() + " " + p.getRank());
                }
            }

            Log.i("BaiduLocation", sb.toString());
            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();

            // 现在已经定位成功，可以将定位的数据保存下来，这里我新建的一个Const类，保存全局静态变量
            Const.LONGITUDE = bdLocation.getLongitude();
            Const.LATITUDE = bdLocation.getLatitude();
            Const.ADDRESS = bdLocation.getAddrStr();
        }

    }
}
