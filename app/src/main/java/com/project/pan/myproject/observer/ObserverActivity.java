package com.project.pan.myproject.observer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.pan.myproject.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class ObserverActivity extends AppCompatActivity {

    PersonObservable personObservable;
    MyObserver myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        personObservable = new PersonObservable();

    }

    public void btnClick (View view){
        myObserver = new MyObserver();
        personObservable.addObserver(myObserver);
        personObservable.setAge(25);
    }

    public void rxjava(){
        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("adfd");
                e.onComplete();
            }
        });

        Observer observer = new Observer() {
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
        };

        observable.subscribe(observer);
    }
}
