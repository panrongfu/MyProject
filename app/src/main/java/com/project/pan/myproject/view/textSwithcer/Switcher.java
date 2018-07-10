package com.project.pan.myproject.view.textSwithcer;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Switcher{

    private int mDuration=3;
	private AdvTextSwitcher advTsView;
    private static Disposable mDisposable;

	public Switcher(AdvTextSwitcher view, int duration){
		this.advTsView = view;
		this.mDuration = duration;
	}

	public void start(){
        mDisposable = Flowable.interval(mDuration, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e("Switcher", "accept: accept : "+aLong );
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e("Switcher", "accept: 设置文本 ："+aLong );
                        advTsView.nextTips();
                    }
                });
	}

	public void pause(){

        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
	}
}
