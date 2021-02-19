package com.js.sample.takePhoto;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.js.sample.Const.Const;
import com.js.sample.R;
import com.ktw.ktwlib.util.ImageUtils;
import com.ktw.ktwlib.util.LogUtils;
import com.ktw.ktwlib.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Created by JS on 2020/7/1.

public class TakePhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TakePhotoActivity";

    private Button btnTakePhoto;
    private ImageView ivImage;
    private boolean isPhotoExist = false;
    private static final int TAKE_PHOTO = 1;
    private Uri imageUri;
    private File gpsPhotoFile;

    //1、首先声明一个数组permissions，将需要的权限都放在里面
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
//            Manifest.permission.SYSTEM_ALERT_WINDOW, // 显示悬浮窗的权限
            Manifest.permission.READ_PHONE_STATE, // 获取手机状态的权限
            Manifest.permission.CALL_PHONE // 打电话的权限
    };
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();

    private final int mRequestCode = 100;//权限请求码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        initView();
        initDir();
        if (Build.VERSION.SDK_INT >= 23) {
            initPermission();
        }
    }

    private void initDir() {
        // 保存照片路径（含创建新文件夹）
        String picsDir = Const.APP_PICS;
        File file = new File(picsDir);
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        // 保存视频的路径
        String videoDir = Const.APP_VIDEOS;
        File file1 = new File(videoDir);
        if (!file1.exists()) {
            file1.mkdir();
        }
    }

    private void initView() {
        btnTakePhoto = findViewById(R.id.btn_take_photo);
        btnTakePhoto.setOnClickListener(this);
        ivImage = findViewById(R.id.iv_image);
    }

    //权限判断和申请
    private void initPermission() {
        LogUtils.d(TAG, "initPermission");
        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        LogUtils.d(TAG, "mPermissionList.size()：" + mPermissionList.size());

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                clickTakePhoto();
                break;
            default:
                break;
        }
    }

    private void clickTakePhoto() {
        takePhoto(TAKE_PHOTO);
    }

    private void takePhoto(int type) {
        // 创建File对象，用于存储拍照后的照片
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(this, "com.js.sample.fileprovider", outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, type);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        setImage();
                    }
                    break;
            }
        }
    }

    private void setImage() {
        try {
            // 将拍摄的照片显示出来
            if (imageUri == null) return;
            Bitmap bitmap = ImageUtils.getBitmapFromUri(this, imageUri);
            if (bitmap == null) {
                ToastUtil.showToast(this, "拍摄出错!");
                return;
            }
//            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            LogUtils.d(this, imageUri.toString());
            if (gpsPhotoFile != null && gpsPhotoFile.exists()) {
                gpsPhotoFile.delete();
            }
            String gpsPhotoName = "samp_" + System.currentTimeMillis() + ".jpg";
            gpsPhotoFile = new File(Const.APP_PICS, gpsPhotoName);
            gpsPhotoFile.createNewFile();
            LogUtils.d(this, "gpsPhotoFile：" + gpsPhotoFile.getPath());
            qualityCompress(bitmap, gpsPhotoFile);
            Glide.with(this)
                    .load(gpsPhotoFile)
                    .into(ivImage);
            isPhotoExist = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 质量压缩
     * 设置bitmap options属性，降低图片的质量，像素不会减少
     * 第一个参数为需要压缩的bitmap图片对象，第二个参数为压缩后图片保存的位置
     * 设置options 属性0-100，来实现压缩（因为png是无损压缩，所以该属性对png是无效的）
     *
     * @param bmp
     * @param file
     */
    public static void qualityCompress(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int quality = 20;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                showPermissionDialog();//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
            }
        }

    }

    /**
     * 不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;
    String mPackName = "com.js.sample";

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予，然后重新进入应用")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            finish();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }
}
