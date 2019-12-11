package com.js.sample.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.GeoView;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.js.sample.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


// Created by JS on 2019/1/25.

public class ArcGisActivity extends AppCompatActivity {

    private static final String TAG = "ArcGisActivity";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcgis);
        MapView mapView = (MapView) findViewById(R.id.mapView);
//        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.056295, -117.195800, 16);
//        // set the map to be displayed in this view
//        mapView.setMap(map);
        // 去除水印
//        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud7736782273,none,4N5X0H4AH5AH2T8AG076");
        // 去除“powered by Esri”
//        mapView.setAttributionTextVisible(false);



        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//
        String url = Environment.getExternalStorageDirectory().getPath() + "/YZTPK/OneMapAndroid.tpk";
        TileCache mainTileCache = new TileCache(url);
        ArcGISMap arcGISMap = new ArcGISMap();
        ArcGISTiledLayer layer = new ArcGISTiledLayer(mainTileCache);
        Basemap basemap = new Basemap(layer);
        arcGISMap.setBasemap(basemap);
        mapView.setMap(arcGISMap);

    }
}
