package com.js.sample.activity.materialDesign;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.js.sample.R;


// Created by JS on 2020/1/15.

public class TabLayoutBasicSampleActivity extends AppCompatActivity {
    private TabLayout tabLayoutOne;
    private TabLayout tabLayoutTwo;
    private TabLayout tabLayoutThree;
    private TabLayout tabLayoutFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_basic_sample);
        tabLayoutOne = (TabLayout) findViewById(R.id.tabLayoutOne);
        tabLayoutTwo = (TabLayout) findViewById(R.id.tabLayoutTwo);
        tabLayoutThree = (TabLayout) findViewById(R.id.tabLayoutThree);
        tabLayoutFour = (TabLayout) findViewById(R.id.tabLayoutFour);
        initTabLayoutOne();
        initTabLayoutTwo();
        initTabLayoutThree();
        initTabLayoutFour();
    }

    private void initTabLayoutOne() {
        tabLayoutOne.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayoutOne.addTab(tabLayoutOne.newTab().setText("one"));
        tabLayoutOne.addTab(tabLayoutOne.newTab().setText("Two"));
        tabLayoutOne.addTab(tabLayoutOne.newTab().setText("Three"));
        tabLayoutOne.addTab(tabLayoutOne.newTab().setText("four"));
        tabLayoutOne.addTab(tabLayoutOne.newTab().setText("five"));
        tabLayoutOne.addTab(tabLayoutOne.newTab().setText("six"));
        tabLayoutOne.addTab(tabLayoutOne.newTab().setText("seven"));
    }

    private void initTabLayoutTwo() {
        tabLayoutTwo.setTabMode(TabLayout.MODE_FIXED);
        tabLayoutTwo.addTab(tabLayoutTwo.newTab().setText("one"));
        tabLayoutTwo.addTab(tabLayoutTwo.newTab().setText("Two"));
        tabLayoutTwo.addTab(tabLayoutTwo.newTab().setText("Three"));
        tabLayoutTwo.addTab(tabLayoutTwo.newTab().setText("four"));
    }

    private void initTabLayoutThree() {
        tabLayoutThree.setTabMode(TabLayout.MODE_FIXED);
        tabLayoutThree.addTab(tabLayoutThree.newTab().setIcon(android.R.drawable.ic_menu_call));
        tabLayoutThree.addTab(tabLayoutThree.newTab().setIcon(android.R.drawable.ic_menu_add));
        tabLayoutThree.addTab(tabLayoutThree.newTab().setIcon(android.R.drawable.ic_menu_agenda));
        tabLayoutThree.addTab(tabLayoutThree.newTab().setIcon(android.R.drawable.ic_menu_camera));
        tabLayoutThree.addTab(tabLayoutThree.newTab().setIcon(android.R.drawable.ic_menu_close_clear_cancel));
    }

    private void initTabLayoutFour() {
        tabLayoutThree.setTabMode(TabLayout.MODE_FIXED);
        tabLayoutFour.addTab(tabLayoutFour.newTab().setCustomView(R.layout.item_tab));
        tabLayoutFour.addTab(tabLayoutFour.newTab().setCustomView(R.layout.item_tab));
        tabLayoutFour.addTab(tabLayoutFour.newTab().setCustomView(R.layout.item_tab));
        tabLayoutFour.addTab(tabLayoutFour.newTab().setCustomView(R.layout.item_tab));
        tabLayoutFour.addTab(tabLayoutFour.newTab().setCustomView(R.layout.item_tab));
    }
}
