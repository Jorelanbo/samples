package com.js.sample.arcgis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.js.sample.R;

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2020/6/19.

public class GraphicsOverlayActivity extends AppCompatActivity {
    private MapView mMapView;

    private RadioGroup mRadioGroup;

    private GraphicsOverlay mGraphicsOverlay;

    //点集合
    private PointCollection mPointCollection = new PointCollection(SpatialReferences.getWebMercator());

    private List<Point> mPointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_overlay);

        findViews();

        addBasemap();

        initListener();
    }

    private void addBasemap() {
        ArcGISMap arcGISMap = new ArcGISMap(Basemap.Type.OCEANS, 56.075844, -2.681572, 11);
        mMapView.setMap(arcGISMap);


        mGraphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay);
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_draw_point: //绘制点
                        drawPoint();
                        break;
                    case R.id.rb_draw_polyline: //绘制线
                        drawPolyline();
                        break;
                    case R.id.rb_draw_scroll_line: //绘制曲线
                        drawScrollline();
                        break;
                    case R.id.rb_draw_polygon: //绘制面
                        drawPolygon();
                        break;
                    case R.id.rb_add_image: //添加图片
                        addImage();
                        break;
                    case R.id.rb_draw_circle: //绘制圆
                        drawCircle();
                        break;
                    case R.id.rb_draw_text: //绘制文字
                        drawText();
                        break;
                }
            }
        });
    }

    /**
     * 绘制点
     */
    private void drawPoint() {
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Point clickPoint = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
                SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 20);
                Graphic graphic = new Graphic(clickPoint, simpleMarkerSymbol);
                //清除上一个点
                mGraphicsOverlay.getGraphics().clear();
                mGraphicsOverlay.getGraphics().add(graphic);

                //使用渲染器
                //                Graphic graphic1 = new Graphic(clickPoint);
                //                SimpleRenderer simpleRenderer = new SimpleRenderer(simpleMarkerSymbol);
                //                mGraphicsOverlay.setRenderer(simpleRenderer);
                //                mGraphicsOverlay.getGraphics().clear();
                //                mGraphicsOverlay.getGraphics().add(graphic1);

                return super.onSingleTapConfirmed(e);
            }
        });
    }

    /**
     * 绘制线
     */
    private void drawPolyline() {
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
                mPointCollection.add(point);

                Polyline polyline = new Polyline(mPointCollection);

                //点
                SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
                Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
                mGraphicsOverlay.getGraphics().add(pointGraphic);

                //线
                SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#FC8145"), 3);
                Graphic graphic = new Graphic(polyline, simpleLineSymbol);
                mGraphicsOverlay.getGraphics().add(graphic);

                return super.onSingleTapConfirmed(e);
            }
        });
    }

    /**
     * 绘制曲线
     */
    private void drawScrollline() {
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Point point1 = mMapView.screenToLocation(new android.graphics.Point(Math.round(e1.getX()), Math.round(e1.getY())));
                Point point2 = mMapView.screenToLocation(new android.graphics.Point(Math.round(e2.getX()), Math.round(e2.getY())));

                mPointCollection.add(point1);
                mPointCollection.add(point2);
                Polyline polyline = new Polyline(mPointCollection);

                Graphic graphic = new Graphic(polyline, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#FC8145"), 3));
                mGraphicsOverlay.getGraphics().add(graphic);
                return true;
            }
        });
    }

    /**
     * 绘制面
     */
    private void drawPolygon() {

        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {

                mGraphicsOverlay.getGraphics().clear();
                Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
                mPointCollection.add(point);

                Polygon polygon = new Polygon(mPointCollection);

                if (mPointCollection.size() == 1) {

                }

                SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
                Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
                mGraphicsOverlay.getGraphics().add(pointGraphic);

                SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GREEN, 3.0f);
                SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#33e97676"), lineSymbol);
                Graphic graphic = new Graphic(polygon, simpleFillSymbol);
                mGraphicsOverlay.getGraphics().add(graphic);

                return super.onSingleTapConfirmed(e);
            }
        });
    }

    /**
     * 添加图片
     */
    private void addImage() {
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {

                Point clickPoint = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(bitmapDrawable);
                final Graphic graphic = new Graphic(clickPoint, pictureMarkerSymbol);

                //涉及到加载图片到符号里，所以需要一个异步监听操作
                pictureMarkerSymbol.loadAsync();
                pictureMarkerSymbol.addDoneLoadingListener(new Runnable() {
                    @Override
                    public void run() {
                        mGraphicsOverlay.getGraphics().clear();
                        mGraphicsOverlay.getGraphics().add(graphic);
                    }
                });

                return super.onSingleTapConfirmed(e);
            }
        });
    }

    /**
     * 绘制文字
     */
    private void drawText() {
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {

                Point clickPoint = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
                TextSymbol textSymbol = new TextSymbol(20, "绘制文字", Color.RED,
                        TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.MIDDLE);
                Graphic graphic = new Graphic(clickPoint, textSymbol);
                mGraphicsOverlay.getGraphics().clear();
                mGraphicsOverlay.getGraphics().add(graphic);

                return super.onSingleTapConfirmed(e);
            }
        });
    }

    /**
     * 绘制圆
     */
    private void drawCircle() {

        mPointList = new ArrayList<>();

        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {

                double radius = 0;

                Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));

                mPointList.add(point);
                if (mPointList.size() == 2) {
                    double x = (mPointList.get(1).getX() - mPointList.get(0).getX());
                    double y = (mPointList.get(1).getY() - mPointList.get(0).getY());
                    radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                }

                getCircle(mPointList.get(0), radius);

                return super.onSingleTapConfirmed(e);
            }
        });
    }


    private void getCircle(Point point, double radius) {
        //        polygon.setEmpty();
        Point[] points = getPoints(point, radius);
        mPointCollection.clear();
        for (Point p : points) {
            mPointCollection.add(p);
        }

        Polygon polygon = new Polygon(mPointCollection);

        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);

        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#FC8145"), 3.0f);
        SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#33e97676"), lineSymbol);

        Graphic graphic = new Graphic(polygon, simpleFillSymbol);

        mGraphicsOverlay.getGraphics().add(graphic);
    }

    /**
     * 通过中心点和半径计算得出圆形的边线点集合
     *
     * @param center
     * @param radius
     * @return
     */
    private static Point[] getPoints(Point center, double radius) {
        Point[] points = new Point[50];
        double sin;
        double cos;
        double x;
        double y;
        for (double i = 0; i < 50; i++) {
            sin = Math.sin(Math.PI * 2 * i / 50);
            cos = Math.cos(Math.PI * 2 * i / 50);
            x = center.getX() + radius * sin;
            y = center.getY() + radius * cos;
            points[(int) i] = new Point(x, y);
        }
        return points;
    }


    /**
     * 清除
     *
     * @param view
     */
    public void clear(View view) {
        mGraphicsOverlay.getGraphics().clear();
        mPointCollection.clear();
        if (mPointList != null) {
            mPointList.clear();
        }
    }

    private void findViews() {
        mMapView = (MapView) findViewById(R.id.mapview);
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
    }
}
