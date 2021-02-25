package com.js.sample.selfmadeViews.activity;

// Created by JS on 2021/2/24.

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.js.sample.R;
import com.js.sample.selfmadeViews.adapter.DataAdapter;
import com.js.sample.selfmadeViews.bean.ItemModel;
import com.ktw.ktwlib.util.NavigationBarUtil;
import com.ktw.ktwlib.util.NetUtils;
import com.ktw.ktwlib.util.StatusBarUtil;
import com.ktw.ktwlib.util.ToastUtil;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.ItemDecoration.DividerDecoration;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.LRecyclerView;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.ProgressStyle;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.adapter.LRecyclerViewAdapter;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.interfaces.OnItemClickListener;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.interfaces.OnItemLongClickListener;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.interfaces.OnLoadMoreListener;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.interfaces.OnNetWorkErrorListener;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.interfaces.OnRefreshListener;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.util.WeakHandler;
import com.ktw.ktwlib.widget.RecyclerView.LRecyclerView.view.BaiDuRefreshHeader;

import java.util.ArrayList;

public class EndlessLinearLayoutActivity extends AppCompatActivity {

    private static final String TAG = "lzx";

    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 34;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;

    private LRecyclerView mRecyclerView = null;

    private DataAdapter mDataAdapter = null;


    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    //WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case -1:

                    int currentSize = mDataAdapter.getItemCount();

                    //模拟组装10个数据
                    ArrayList<ItemModel> newList = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (newList.size() + currentSize >= TOTAL_COUNTER) {
                            break;
                        }

                        ItemModel item = new ItemModel();
                        item.id = currentSize + i;
                        item.title = "item" + (item.id);

                        newList.add(item);
                    }

                    addItems(newList);

                    mRecyclerView.refreshComplete(REQUEST_COUNT);

                    break;
                case -3:
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                    mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            requestData();
                        }
                    });

                    break;
                default:
                    break;
            }
        }
    };
    private EndlessLinearLayoutActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_endless_linear_layout);
        StatusBarUtil.setStatusBarColor(this, R.color.black);
        NavigationBarUtil.setSysNavigationColor(this, Color.parseColor("#FFFFFF"));

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setRefreshHeader(new BaiDuRefreshHeader(this));
        mDataAdapter = new DataAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.split)
                .build();
        mRecyclerView.addItemDecoration(divider);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.mipmap.ic_pulltorefresh_arrow);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        // add a HeaderView
        View header = LayoutInflater.from(this).inflate(R.layout.sample_header, (ViewGroup)findViewById(android.R.id.content), false);
        mLRecyclerViewAdapter.addHeaderView(header);

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                mCurrentCounter = 0;
                requestData();
            }
        });

        // 是否禁止自动加载更多功能，false为禁用，默认开启加载更多功能
        mRecyclerView.setLoadMoreEnabled(true);

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // load more
                    requestData();
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });

        // 设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.app_def_color, R.color.dark, R.color.white);
        // 设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.app_def_color, R.color.dark, R.color.white);
        // 设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "我是有底线的", "网络不给力啊，点击再试一次吧");

        mRecyclerView.refresh();

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDataAdapter.getDataList().size() > position) {
                    ItemModel item = mDataAdapter.getDataList().get(position);
                    ToastUtil.showToast(mContext, "点击的项：" + item.title);
                }
            }
        });

        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                ItemModel item = mDataAdapter.getDataList().get(position);
                ToastUtil.showToast(mContext, "长按删除的项：" + item.title);
                mDataAdapter.remove(position);
            }
        });
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(ArrayList<ItemModel> list) {

        mDataAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    /**
     * 模拟请求网络
     */
    private void requestData() {
        Log.d(TAG, "requestData");
        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一下网络请求失败的情况
                if(NetUtils.isNetworkAvailable(getApplicationContext())) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }
            }
        }.start();
    }
}
