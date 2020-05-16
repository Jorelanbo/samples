package com.js.sample.materialDesign.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.js.sample.R;
import com.js.sample.materialDesign.fragment.Tab2Fragment;

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2020/1/15.

public class TabLayoutAndViewPagerActivity extends AppCompatActivity {

    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_and_viewpager);
        TabLayout tabLayout = findViewById(R.id.t_tablayout);
        ViewPager viewPager = findViewById(R.id.vp_tablayout);
        initFragments();
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragments() {
        Tab2Fragment tab1 = new Tab2Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TAB", 1);
        tab1.setArguments(bundle);

        Tab2Fragment tab2 = new Tab2Fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("TAB", 2);
        tab2.setArguments(bundle2);

        Tab2Fragment tab3 = new Tab2Fragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("TAB", 3);
        tab3.setArguments(bundle3);


        Tab2Fragment tab4 = new Tab2Fragment();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("TAB", 4);
        tab4.setArguments(bundle4);

        fragments.add(tab1);
        fragments.add(tab2);
        fragments.add(tab3);
        fragments.add(tab4);
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "第" + (position + 1) + "页";
        }
    }
}
