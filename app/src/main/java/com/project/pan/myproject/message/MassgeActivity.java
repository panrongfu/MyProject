package com.project.pan.myproject.message;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.project.pan.myproject.R;

public class MassgeActivity extends AppCompatActivity {

    ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massge);
        threadLocal.set(true);
        Log.e("main:>>>",threadLocal.get()+"");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                threadLocal.set(false);
                Log.e("one:>>>",threadLocal.get()+"");
                Looper.loop();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("two:>>>",threadLocal.get()+"");
            }
        })
                .start();
    }


}
