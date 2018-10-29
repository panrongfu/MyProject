package com.project.pan.myproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.project.pan.myproject.cache.MainCacheActivity;
import com.project.pan.myproject.dagger2.DaggerActivity;
import com.project.pan.myproject.designMode.DesignModeActivity;
import com.project.pan.myproject.observer.ObserverActivity;
import com.project.pan.myproject.onTouchEvent.OnTouchEventActivity;
import com.project.pan.myproject.view.ViewStubActivity;
import com.project.pan.myproject.view.animation.AnimationActivity;
import com.project.pan.myproject.view.custom.CustomViewActivity;
import com.project.pan.myproject.view.textSwithcer.TextSwitcherActivity;
import com.project.pan.myproject.window.WindowActivity;


public class MainActivity extends Activity {


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
        Drawable drawable = getResources().getDrawable(R.drawable.btn_tick_pressed);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.btn_tick_pressed);
        ImageView iv = findViewById(R.id.iv);
        iv.setImageBitmap(bitmap);
        startActivity(new Intent(this, OnTouchEventActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
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


    public void customViewClick(View view){
        startActivity(new Intent(this, CustomViewActivity.class));

    }

    public void clickWindow(View view){
        startActivity(new Intent(this, WindowActivity.class));
    }

    public void cacheClick(View view){
        startActivity(new Intent(this, MainCacheActivity.class));
    }

}
