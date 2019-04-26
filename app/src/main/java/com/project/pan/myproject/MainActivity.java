package com.project.pan.myproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.project.pan.common.global.ARouterPaths;
import com.project.pan.myproject.cache.MainCacheActivity;
import com.project.pan.myproject.dagger2.DaggerActivity;
import com.project.pan.myproject.designMode.DesignModeActivity;
import com.project.pan.myproject.dispatch.DispatchActivity;
import com.project.pan.myproject.dispatch.DispatchActivity2;
import com.project.pan.myproject.dynamic.DynamicActivity;
import com.project.pan.myproject.ipc.IpcActivity;
import com.project.pan.myproject.jni.JniActivity;
import com.project.pan.myproject.observer.ObserverActivity;
import com.project.pan.myproject.onTouchEvent.OnTouchEventActivity;
import com.project.pan.myproject.view.BezierActivity;
import com.project.pan.myproject.view.ViewStubActivity;
import com.project.pan.myproject.view.animation.AnimationActivity;
import com.project.pan.myproject.view.custom.CustomViewActivity;
import com.project.pan.myproject.view.textSwithcer.TextSwitcherActivity;
import com.project.pan.myproject.window.WindowActivity;


public class MainActivity extends Activity {


    @RequiresApi(api = Build.VERSION_CODES.N)
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

    public void ipc(View view){
        startActivity(new Intent(this, IpcActivity.class));
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

    public void jniClick(View view){
        startActivity(new Intent(this, JniActivity.class));
    }

    public void clickDispatch(View view){
        startActivity(new Intent(this, DispatchActivity.class));
    }

    public void clickDispatch2(View view){
        startActivity(new Intent(this, DispatchActivity2.class));

    }

    public void clickNotify(View view){
        ARouter.getInstance()
                .build(ARouterPaths.NOTIFY_ACTIVITY)
                .navigation();
    }

    public void clickKotlin(View view){
        ARouter.getInstance()
                .build(ARouterPaths.K_HOME_ACTIVITY)
                .navigation(MainActivity.this);
    }

    public void clickDynamic(View view){
        startActivity(new Intent(this, DynamicActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    public void bezier(View view) {
        startActivity(new Intent(this, BezierActivity.class));
    }
}
