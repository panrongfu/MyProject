package com.project.pan.myproject.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.project.pan.myproject.R;
import com.project.pan.myproject.rxjava.observer.Gamedaily;
import com.project.pan.myproject.rxjava.observer.User;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("abc");
            }

        })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .compose(this.bindToLifecycle())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
        .map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) throws Exception {
                return "ADFSDFSDF";
            }
        })
        .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
        //观察者（new Observer）和被观察者订阅（Observable.create）

        .subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        new TextView(this).setOnClickListener(view-> Log.e("a","a"));
    }

    public static void main(String[] args){
        Gamedaily gamedaily = new Gamedaily();
        User user1 = new User("user1");
        User user2 = new User("user2");
        User user3 = new User("user3");
        //将观察者注册到可观察者的通知列表中。
        gamedaily.addObserver(user1);
        gamedaily.addObserver(user2);
        gamedaily.addObserver(user3);

        gamedaily.postNewArticle("新文章来了");

    }
}
