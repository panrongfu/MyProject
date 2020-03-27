package com.project.pan.myproject.view.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;
import com.project.pan.myproject.R;
import com.project.pan.myproject.view.progress.CircleProgress;

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
    int sweepAngle = 120;
    int totalClass = 3;
    int currentClass  = 0;
    private CircleProgress circleProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

       // pointView = findViewById(R.id.pointer_view);
       // loginView = findViewById(R.id.hrz_login_btn);
       // loginView.setOnClickListener(view-> loginView.startShrink(1));

        indicatorLayout = findViewById(R.id.indicatorLayout);
        indicatorLayout.setItemSize(5);

        circleProgress = findViewById(R.id.circleProgress);

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

    public void clickCircleProgress(View view){
        currentClass+=1;
        float everyClassOccupyAngle = 360/totalClass+2;
        float currentTotalOccupyAngle = currentClass*everyClassOccupyAngle;
        circleProgress.sweepAngle(currentTotalOccupyAngle);
        circleProgress.setProgressText(currentClass+"/"+totalClass);
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

//        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
//        animationView.setAnimation("data.json");
//        animationView.loop(true);
//        animationView.playAnimation();
    }

    public void clickFinish(View view){
       // pointView.startCompress(3);
    }

    public void clickLoginButton(View view){

      //  loginView.startStretch(1);
    }

    public void clickAnimation(View view){

    }


    public void clickEvaluator(View view){
        startActivity(new Intent(this,EvaluatorActivity.class));
    }
}
