package com.js.sample.commonviewpager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.js.sample.R;
import com.js.sample.commonviewpager.adapter.CommonViewPagerAdapter;
import com.js.sample.commonviewpager.entity.DataEntry;
import com.js.sample.commonviewpager.holder.ViewPagerHolder;
import com.js.sample.commonviewpager.holder.ViewPagerHolderCreator;
import com.js.sample.commonviewpager.view.CommonViewPager;
import com.js.sample.utils.Util;

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2020/5/12.

public class CommonViewPagerActivity extends AppCompatActivity {

    private CommonViewPager mCommonViewPager;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_common_view_pager);
        initView();
    }

    private void initView() {
        mCommonViewPager = findViewById(R.id.cvp);
        // 设置数据
        mCommonViewPager.setPages(mockData(), new ViewPagerHolderCreator() {
            @Override
            public ViewPagerHolder createViewHolder() {
                return new ViewImageHolder();
            }
        });
        mCommonViewPager.setPageClickListener(new CommonViewPagerAdapter.PageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Util.showToast((Activity) mContext, "点击了" + (position + 1) + "号");
            }
        });
        mCommonViewPager.start();
    }

    private List<DataEntry> mockData() {
        List<DataEntry> entries = new ArrayList<>();
        DataEntry entry = new DataEntry();
        entry.imageResId = R.mipmap.image1;
        entry.desc = "1号";
        entries.add(entry);
        DataEntry entry2 = new DataEntry();
        entry2.imageResId = R.mipmap.image2;
        entry2.desc = "2号";
        entries.add(entry2);
        DataEntry entry3 = new DataEntry();
        entry3.imageResId = R.mipmap.image3;
        entry3.desc = "3号";
        entries.add(entry3);
        DataEntry entr4 = new DataEntry();
        entr4.imageResId = R.mipmap.image4;
        entr4.desc = "4号";
        entries.add(entr4);
        DataEntry entr5 = new DataEntry();
        entr5.imageResId = R.mipmap.image5;
        entr5.desc = "5号";
        entries.add(entr5);
        DataEntry entr6 = new DataEntry();
        entr6.imageResId = R.mipmap.image6;
        entr6.desc = "6号";
        entries.add(entr6);
        DataEntry entry7 = new DataEntry();
        entry7.imageResId = R.mipmap.image7;
        entry7.desc = "7号";
        entries.add(entry7);
        DataEntry entry8 = new DataEntry();
        entry8.imageResId = R.mipmap.image8;
        entry8.desc = "8号";
        entries.add(entry8);
        return entries;
    }

    /**
     * 提供ViewPager展示的ViewHolder
     * <P>用于提供布局和绑定数据</P>
     */
    public static class ViewImageHolder implements ViewPagerHolder<DataEntry> {

        private ImageView mImageView;
        private TextView mTextView;

        @Override
        public View createView(Context context) {
            // 返回viewPager 页面展示布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_common_view_pager, null);
            mImageView = view.findViewById(R.id.iv_view_pager_item);
            mTextView = view.findViewById(R.id.tv_item_desc);
            return view;
        }

        @Override
        public void onBind(Context context, int position, DataEntry data) {
            // 数据绑定
            // 自己绑定数据，灵活度很大
            mImageView.setImageResource(data.imageResId);
            mTextView.setText(data.desc);
        }
    }
}
