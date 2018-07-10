package com.project.pan.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.pan.myproject.dagger2.DaggerActivity;
import com.project.pan.myproject.designMode.DesignModeActivity;
import com.project.pan.myproject.observer.ObserverActivity;
import com.project.pan.myproject.onTouchEvent.OnTouchEventActivity;
import com.project.pan.myproject.view.animation.AnimationActivity;
import com.project.pan.myproject.view.ViewStubActivity;
import com.project.pan.myproject.view.textSwithcer.TextSwitcherActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 跳转到事件传递页面
     * @param view
     */
    public void onTouchDispatch(View view){
        startActivity(new Intent(this, OnTouchEventActivity.class));
    }

    public void clickObserver(View view){
        startActivity(new Intent(this, ObserverActivity.class));
    }

    public void clickDagger(View view){
        startActivity(new Intent(this, DaggerActivity.class));
    }

    public void clickViewStub(View view){
        startActivity(new Intent(this, ViewStubActivity.class));
    }

    public void clickSwitcher(View view){
        startActivity(new Intent(this, TextSwitcherActivity.class));
    }

    public void clickDesignMode(View view){
        startActivity(new Intent(this, DesignModeActivity.class));
    }

    public void clickAnimation(View view){
        startActivity(new Intent(this, AnimationActivity.class));
    }


}
