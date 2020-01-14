package com.js.sample.activity.materialDesign;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.js.sample.R;
import com.js.sample.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取6种色调的样本
 * Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();//获取有活力的颜色样本
 * Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();//获取有活力 亮色的样本
 * Palette.Swatch drakVibrantSwatch = palette.getDarkVibrantSwatch();//获取有活力 暗色的样本
 * Palette.Swatch mutedSwatch = palette.getMutedSwatch();//获取柔和的颜色样本
 * Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();//获取柔和 亮色的样本
 * Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();//获取柔和 暗色的样本
 *
 * 样本对象的一些API
 * int rgb = vibrantSwatch.getRgb();//获取对应样本的rgb
 * float[] hsl = vibrantSwatch.getHsl();//获取hsl颜色
 * int population = vibrantSwatch.getPopulation();//获取像素的数量
 * int titleTextColor = vibrantSwatch.getTitleTextColor();//获取适合标题文字的颜色
 * int bodyTextColor = vibrantSwatch.getBodyTextColor();//获取适配内容文字的颜色
 */


// Created by JS on 2020/1/14.

public class PaletteAdvanceSampleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabFragment tab1;
    private TabFragment tab2;
    private TabFragment tab3;
    private TabFragment tab4;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_advance_sample);
        toolbar = findViewById(R.id.t_palette_advance);
        tabLayout = findViewById(R.id.tl_palette_advance);
        viewPager = findViewById(R.id.vp_palette_advance);
        setSupportActionBar(toolbar);
        initFragments();

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        addPageChangeListener();
    }

    private void initFragments() {
        tab1 = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TAB", 1);
        tab1.setArguments(bundle);

        tab2 = new TabFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("TAB", 2);
        tab2.setArguments(bundle2);

        tab3 = new TabFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("TAB", 3);
        tab3.setArguments(bundle3);

        tab4 = new TabFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("TAB", 4);
        tab4.setArguments(bundle4);

        fragments.add(tab1);
        fragments.add(tab2);
        fragments.add(tab3);
        fragments.add(tab4);
    }

    private boolean isInit = false;

    private void addPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 这个方法在setAdapter之后会调用一次，在这里初始化第一个界面，而且只调用一次
                if (!isInit) {
                    setPaletteColor(position);
                    isInit = true;
                }
            }

            @Override
            public void onPageSelected(int position) {
                setPaletteColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setPaletteColor(int position) {
        Bitmap bitmap = null;
        if (position == 0) {
            bitmap = tab1.getBitmap();
        } else if (position == 1) {
            bitmap = tab2.getBitmap();
        } else if (position == 2) {
            bitmap = tab3.getBitmap();
        } else if (position == 3) {
            bitmap = tab4.getBitmap();
        }
        if (bitmap == null) {
            return;
        }
        Palette.from(bitmap).generate(palette -> {
            Palette.Swatch vibrant = palette.getVibrantSwatch();
            if (vibrant == null) {
                vibrant = palette.getSwatches().get(0);
            }
            int rgb = vibrant.getRgb();

            if (position == 0) {
                tab1.setContent(rgb);
            } else if (position == 1) {
                tab2.setContent(rgb);
            } else if (position == 2) {
                tab3.setContent(rgb);
            } else if (position == 3) {
                tab4.setContent(rgb);
            }

            tabLayout.setBackgroundColor(rgb);
            toolbar.setBackgroundColor(rgb);
            if (Build.VERSION.SDK_INT > 21) {
                Window window = getWindow();
                //状态栏改变颜色
                int color = changeColor(rgb);
                window.setStatusBarColor(color);
            }
        });
    }

    private int changeColor(int rgb) {
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        red = (int) Math.floor(red * (1 - 0.2));
        green = (int) Math.floor(green * (1 - 0.2));
        blue = (int) Math.floor(blue * (1 - 0.2));
        return Color.rgb(red, green, blue);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "第" + (position + 1) + "页";
        }
    }
}
