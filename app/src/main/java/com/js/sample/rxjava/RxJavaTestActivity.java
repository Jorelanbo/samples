package com.js.sample.rxjava;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.js.sample.R;
import com.js.sample.retrofit.bean.ChapterBean;
import com.js.sample.retrofit.network.Data;
import com.js.sample.utils.Util;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


// Created by JS on 2020/6/15.

public class RxJavaTestActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaTestActivity";
    private ImageView ivImg;
    private TextView tvData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);
        initView();
//        useObserver();
//        useSubscriber();
//        useAction();
//        printStrings();
//        setImage();
//        setScheduler();
        getDataWithRetrofit();
    }

    private void initView() {
        ivImg = findViewById(R.id.iv_img);
        tvData = findViewById(R.id.tv_data);
        tvData.setMovementMethod(new ScrollingMovementMethod()); // 给TextView设置滑动效果
    }

    /**
     * 原始简单的使用案例
     */
    private void useObserver() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Completed! ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error! ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Item: " + s);
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("js");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(observer);
    }

    /**
     * 使用subscriber
     */
    private void useSubscriber() {
        // 与observer功能和用法一样，但是做了一些拓展，observer在订阅的过程中会转换成一个Subscriber。
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Completed! ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error! ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Item: " + s);
            }
        };
//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("You");
//                subscriber.onNext("are");
//                subscriber.onNext("the");
//                subscriber.onNext("best!");
//                subscriber.onCompleted();
//            }
//        });
//        Observable observable = Observable.just("Hello", "js");
        String[] words = {"Hello", "JS"};
        Observable observable = Observable.from(words);
        observable.subscribe(subscriber);
    }

    /**
     * 使用action构建observer
     */
    private void useAction() {
        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d(TAG, s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // error handling
            }
        };

        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.d(TAG, "Completed!");
            }
        };

        String[] words = {"Hello", "JS"};
        Observable observable = Observable.from(words);
        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    /**
     * 打印字符串数组的简单使用
     */
    private void printStrings() {
        String[] words = {"Hello", "JS", "Jorelanbo", "John Rainbow"};
        Observable.from(words)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, s);
                    }
                });
    }

    /**
     * 设置图片的简单使用
     */
    private void setImage() {
        int drawableRes = R.mipmap.fx;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Util.showToast(RxJavaTestActivity.this, "Error");
            }

            @Override
            public void onNext(Drawable drawable) {
                ivImg.setImageDrawable(drawable);
            }
        });
    }

    /**
     * 指定观察者执行的内容的线程和被观察者执行的线程
     *
     * 1、Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     * 2、Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     * 3、Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
     *    行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，
     *    可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     * 4、Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，
     *    例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * 5、另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
     */
    private void setScheduler() {
//        Observable.just(1, 2, 2, 4)
//                .subscribeOn(Schedulers.io()) // 指定subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer number) {
//                        Log.d(TAG, "number: " + number);
//                    }
//                });
        int drawableRes = R.mipmap.fx;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io()) // 指定subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
        .subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Util.showToast(RxJavaTestActivity.this, "Error");
            }

            @Override
            public void onNext(Drawable drawable) {
                ivImg.setImageDrawable(drawable);
            }
        });
    }

    /**
     * 与retrofit结合的例子
     */
    private void getDataWithRetrofit() {
        RxJavaApi rxJavaApi = RxJavaRetrofit.getRetrofit().create(RxJavaApi.class);
        rxJavaApi.getChapters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Data<List<ChapterBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        tvData.setText("数据加载失败...");
                    }

                    @Override
                    public void onNext(Data<List<ChapterBean>> listData) {
                        Log.d(TAG, "onNext: ");
                        List<ChapterBean> data = listData.getData();
                        tvData.setText(data.toString());
                    }
                });


    }
}
