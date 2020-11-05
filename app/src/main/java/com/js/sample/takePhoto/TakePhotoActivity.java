package com.js.sample.takePhoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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


// Created by JS on 2020/7/1.

public class TakePhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnTakePhoto;
    private ImageView ivImage;
    private boolean isPhotoExist = false;
    private static final int TAKE_PHOTO = 1;
    private Uri imageUri;
    private File gpsPhotoFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        initView();
        initDir();
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
}
