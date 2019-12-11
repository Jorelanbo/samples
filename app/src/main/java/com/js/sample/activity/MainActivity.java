package com.js.sample.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.js.sample.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int TAG1 = 1;
    private static final String TAG = "MainActivity";
    private static final int SUCCESS_DOWNLOAD = 1;
    private static final int SUCCESS_LOAD = 2;
    private Handler mhandler = null;
    private ImageView iv;
    private ImageView iv2;
    private File file;
    private Button bt2;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "v");
        Log.d(TAG, "d");
        Log.i(TAG, "i");
        Log.w(TAG, "w");
        Log.e(TAG, "e");
        Button bt1 = (Button) findViewById(R.id.bt1);
        Button bt2 = (Button) findViewById(R.id.bt2);
        Button bt3 = (Button) findViewById(R.id.bt3);
        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageView) findViewById(R.id.iv2);

        file = new File(Environment.getExternalStorageDirectory(), "4.jpg");

        mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SUCCESS_DOWNLOAD:
                        Bitmap bitmap = (Bitmap) msg.obj;
                        iv.setImageBitmap(bitmap);
                        break;
                    case SUCCESS_LOAD:
                        Bitmap bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath());
                        iv2.setImageBitmap(bitmap1);
                        Log.d(TAG, "从网上下载了图片然后从内存卡的缓存获取");
                        break;
                    default:
                        break;
                }
            }
        };

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " 第一个按钮被点击");
                Toast.makeText(MainActivity.this, "这是第一个按钮,图片路径是：" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                try {
                    FileOutputStream fileOutputStream = openFileOutput("demo.txt", MODE_APPEND);
                    fileOutputStream.write("你好".getBytes());
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "第二个按钮被点击");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String path = "http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg";
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setReadTimeout(5000);
                            if (conn.getResponseCode() == 200) {
                                InputStream inputStream = conn.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                Message message = new Message();
                                message.obj = bitmap;
                                message.what = SUCCESS_DOWNLOAD;
                                mhandler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "第三个按钮被点击");


                if (file.exists()) {

                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    iv2.setImageBitmap(bitmap);
                    Log.d(TAG, "从内存卡获取了缓存图片");
                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String path = "http://img.zcool.cn/community/019c2958a2b760a801219c77a9d27f.jpg";
                                URL url = new URL(path);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setReadTimeout(5000);
                                if (conn.getResponseCode() == 200) {
                                    InputStream inputStream = conn.getInputStream();
                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                    int len;
                                    byte[] bytes = new byte[1024];
                                    while ((len = inputStream.read(bytes)) != -1) {
                                        fileOutputStream.write(bytes, 0, len);
                                    }
                                    inputStream.close();
                                    fileOutputStream.close();

                                    Message msg_bt3 = new Message();
                                    msg_bt3.what = SUCCESS_LOAD;
                                    mhandler.sendMessage(msg_bt3);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            }
        });
    }

    public void click(View view) {

        OkHttpClient okHttpClient = new OkHttpClient();
    }


    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除");
        builder.setMessage("是否删除？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "您点击了是！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "您点击了否！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void showSingleDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("单选");
        final String[] items = {"A" , "B", "C", "D", "E"};
        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "您选择了：" + items[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void showMultiDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("多选");
        final String[] items = {"A" , "B", "C", "D", "E"};
        final boolean[] checkedItems = {false, true, false, false, true};
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        sb.append(items[i] + " ");
                    }
                }
                String s = sb.toString();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void showProgressDialog(View view) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("下载进度");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 101; i ++) {
                    SystemClock.sleep(50);
                    dialog.setProgress(i);
                }
                dialog.dismiss();
            }
        }).start();
        dialog.show();
    }

    public void test(){
        int TAG = 1;
//        getContentResolver().query()
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 7;
    }

    @Override
    public void onClick(View v) {

    }


}
