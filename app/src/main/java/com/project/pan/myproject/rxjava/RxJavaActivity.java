package com.project.pan.myproject.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.project.pan.myproject.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
            }
        })
        .subscribeOn(Schedulers.io())//指定Observable自身在哪个调度器上执行
        .map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) throws Exception {
                return "ADFSDFSDF";
            }
        })
        .observeOn(AndroidSchedulers.mainThread())//指定一个观察者在哪个调度器上观察这个Observable
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
}
