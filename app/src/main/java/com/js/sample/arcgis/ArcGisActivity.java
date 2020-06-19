package com.js.sample.arcgis;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.io.RequestConfiguration;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.location.LocationDataSource;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.WrapAroundMode;
import com.js.sample.R;
import com.js.sample.arcgis.tianditu.TianDiTuMethodsClass;


// Created by JS on 2019/1/25.

public class ArcGisActivity extends AppCompatActivity {

    private static final String TAG = "ArcGisActivity";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };
    private MapView mapView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcgis);
        mapView = (MapView) findViewById(R.id.mapView);
//        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.056295, -117.195800, 16);
//        // set the map to be displayed in this view
//        mapView.setMap(map);
        // 去除水印
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud7736782273,none,4N5X0H4AH5AH2T8AG076");
//         去除“powered by Esri”
        mapView.setAttributionTextVisible(false);

        // 禁止旋转
        mapView.setOnTouchListener(new DefaultMapViewOnTouchListener(ArcGisActivity.this, mapView) {
            @Override
            public boolean onRotate(MotionEvent event, double rotationAngle) {
                return false;
            }
        });

        // 设置环绕模式
        mapView.setWrapAroundMode(WrapAroundMode.DISABLED);

        BackgroundGrid mainBackgroundGrid = new BackgroundGrid();
        mainBackgroundGrid.setColor(0xffffffff);
        mainBackgroundGrid.setGridLineColor(0xffffffff);
        mainBackgroundGrid.setGridLineWidth(0);
        mapView.setBackgroundGrid(mainBackgroundGrid);

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
//        String url = Environment.getExternalStorageDirectory().getPath() + "/KINGTOPWARE.tpk";
//        TileCache mainTileCache = new TileCache(url);
//        ArcGISMap arcGISMap = new ArcGISMap();
//        ArcGISTiledLayer layer = new ArcGISTiledLayer(mainTileCache);
//        Basemap basemap = new Basemap(layer);
//        arcGISMap.setBasemap(basemap);
//        mapView.setMap(arcGISMap);

//        WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_2000);
//        WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);
//
//        //注意：在100.2.0之后要设置RequestConfiguration
//        RequestConfiguration requestConfiguration = new RequestConfiguration();
//        requestConfiguration.getHeaders().put("referer", "http://www.arcgis.com");
//        webTiledLayer.setRequestConfiguration(requestConfiguration);
//        webTiledLayer1.setRequestConfiguration(requestConfiguration);
//        webTiledLayer.loadAsync();
//        webTiledLayer1.loadAsync();
//        Basemap basemap1 = new Basemap(webTiledLayer);
//        basemap1.getBaseLayers().add(webTiledLayer1);
//        mapView.getMap().setBasemap(basemap1);

        WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_2000);
        WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
        webTiledLayer.loadAsync();
        webTiledLayer.addDoneLoadingListener(() -> {
            if (webTiledLayer.getLoadStatus() == LoadStatus.LOADED) {
                // use web tiled layer as Basemap
                ArcGISMap map = new ArcGISMap(new Basemap(webTiledLayer));
                map.getBasemap().getBaseLayers().add(webTiledLayer1);
                mapView.setMap(map);
            }
        });
        startLocation();

//        String theURLString = "http://map.geoq.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer";
//        ArcGISTiledLayer mainArcGISTiledLayer = new ArcGISTiledLayer(theURLString);
//        Basemap mainBasemap = new Basemap(mainArcGISTiledLayer);
//        ArcGISMap arcGISMap = new ArcGISMap(mainBasemap);
//        mapView.setMap(arcGISMap);
    }

    private LocationDisplay mLocationDisplay;
    private void startLocation() {
        mLocationDisplay = mapView.getLocationDisplay();
        mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
        mLocationDisplay.setShowAccuracy(false);
        //当我们执行LocationDisplay.startAsync()方法时候，会在地图上显示出我们当前位置
        mLocationDisplay.startAsync();

        //获取的点是基于当前地图坐标系的点
        Point point = mLocationDisplay.getMapLocation();
        Log.d("xyh", "point: " + point.toString());

        //获取基于GPS的位置信息
        LocationDataSource.Location location = mLocationDisplay.getLocation();
        //基于WGS84的经纬度坐标。
        Point point1 = location.getPosition();
        if (point1 != null) {
            Log.d("xyh", "point1: " + point1.toString());
        }

        //如果要在LocationDisplay里进行位置信息的自动监听，方法也很简单，只需要LocationDisplay.addLocationChangedListener即可
        mLocationDisplay.addLocationChangedListener(new LocationDisplay.LocationChangedListener() {
            @Override
            public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
                LocationDataSource.Location location = locationChangedEvent.getLocation();
                Log.d("xyh", "onLocationChanged: " + location.getPosition().toString());
            }
        });
    }
}
