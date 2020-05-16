package com.js.sample.bottomMenu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.js.sample.R;
import com.js.sample.bottomMenu.fragment.ExploreFragment;
import com.js.sample.bottomMenu.fragment.FriendsFragment;
import com.js.sample.bottomMenu.fragment.MainFragment;
import com.js.sample.bottomMenu.fragment.MineFragment;


// Created by JS on 2019/12/11.

public class BottomMenuActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private static final String TAG = "BottomMenuActivity";
    private MainFragment mainFragment;
    private FriendsFragment friendsFragment;
    private ExploreFragment exploreFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_menu);
        RadioGroup rgBottomMenu = findViewById(R.id.rg_bottom_menu);
        ImageView ivBottomMenuCenter = findViewById(R.id.iv_bottom_menu_center);
        rgBottomMenu.setOnCheckedChangeListener(this);
        ivBottomMenuCenter.setOnClickListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mainFragment = new MainFragment();
        fragmentTransaction.add(R.id.fl_content, mainFragment).show(mainFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bottom_menu_center:
                Toast.makeText(this, "Center menu clicked.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (checkedId) {
            case R.id.rb_home:
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    fragmentTransaction.add(R.id.fl_content, mainFragment);
                }
                fragmentTransaction.show(mainFragment);
                break;
            case R.id.rb_friends:
                if (friendsFragment == null) {
                    friendsFragment = new FriendsFragment();
                    fragmentTransaction.add(R.id.fl_content, friendsFragment);
                }
                fragmentTransaction.show(friendsFragment);
                break;
            case R.id.rb_explore:
                if (exploreFragment == null) {
                    exploreFragment = new ExploreFragment();
                    fragmentTransaction.add(R.id.fl_content, exploreFragment);
                }
                fragmentTransaction.show(exploreFragment);
                break;
            case R.id.rb_mine:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.fl_content, mineFragment);
                }
                fragmentTransaction.show(mineFragment);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mainFragment != null) {
            fragmentTransaction.hide(mainFragment);
        }
        if (friendsFragment != null) {
            fragmentTransaction.hide(friendsFragment);
        }
        if (exploreFragment != null) {
            fragmentTransaction.hide(exploreFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
    }
}
