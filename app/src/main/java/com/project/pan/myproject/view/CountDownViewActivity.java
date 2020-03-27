package com.project.pan.myproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.pan.myproject.R;
import com.project.pan.myproject.view.countdown.CountDownCircleProgressBar;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CountDownViewActivity extends AppCompatActivity {
    int total = 7;
    Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_view);

        CountDownCircleProgressBar bar = findViewById(R.id.CountDownCircleProgressBar);
        bar.setCountdownTotalNumber(7);
        bar.quickCountDown(7);
//         disposable = Flowable.interval(1, TimeUnit.SECONDS)
//                .subscribe(aLong -> {
//                    if (total < 0 ) {
//                        bar.stopCountDown();
//                        disposable.dispose();
//                        total = 7;
//                    }
//                    bar.setCountDownNumber(total);
//                    total --;
//                });

    }
}
