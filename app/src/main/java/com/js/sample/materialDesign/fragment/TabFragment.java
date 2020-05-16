package com.js.sample.materialDesign.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.js.sample.R;


// Created by JS on 2020/1/14.

public class TabFragment extends Fragment {

    private Bitmap bitmap;
    private TextView tvBg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int tab = getArguments().getInt("TAB");
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ImageView imageView = view.findViewById(R.id.iv_img);
        tvBg = view.findViewById(R.id.tv_bg);
        TextView tvDescription = view.findViewById(R.id.tv_description);
        if (tab == 1) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.jt);
            tvDescription.setText("你说你喜欢听吉他，我就在你回家的路上弹吉他等你。");
        } else if (tab == 2) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fx);
            tvDescription.setText("那燃烧的枫叶，都比不过我对远方的期盼。");
        } else if (tab == 3) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xsea);
            tvDescription.setText("期待有一天我能和你“面朝大海，春暖花开。”");
        } else if (tab == 4) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pg);
            tvDescription.setText("说好要一起旅行，是你如今，唯一坚持的任性。");
        }
        imageView.setImageBitmap(bitmap);
        return view;
    }

    public void setContent(int rgb) {
        tvBg.setBackgroundColor(rgb);
        tvBg.setAlpha(0.5f);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
