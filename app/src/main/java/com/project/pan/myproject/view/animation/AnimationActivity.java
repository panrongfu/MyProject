package com.project.pan.myproject.view.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.pan.myproject.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class AnimationActivity extends AppCompatActivity {

   // PointView pointView;
   // LoginView loginView;

    private IndicatorLayout indicatorLayout;
    int  position= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

       // pointView = findViewById(R.id.pointer_view);
       // loginView = findViewById(R.id.hrz_login_btn);
       // loginView.setOnClickListener(view-> loginView.startShrink(1));

        indicatorLayout = findViewById(R.id.indicatorLayout);
        indicatorLayout.setItemSize(5);

//        Observable.interval(5, TimeUnit.MICROSECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        position++;
//                        if(position > 4){
//                            position =0;
//                        }
//                        indicatorLayout.setCurrentItemPosition(position);
//                    }
//                });

    }

    public void clickNextPoint(View view){
        position++;
        if(position > 4){
            position=0;
        }
        indicatorLayout.setCurrentItemPosition(position);
    }


    public void clickStart (View view){

       // pointView.startStretch(1);
    }

    public void clickFinish(View view){
       // pointView.startCompress(3);
    }

    public void clickLoginButton(View view){

      //  loginView.startStretch(1);
    }


}
