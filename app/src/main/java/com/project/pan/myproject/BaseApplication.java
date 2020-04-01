package com.project.pan.myproject;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.project.pan.myproject.crash.CrashHandler;
import com.project.pan.myproject.dagger2.di.component.AppComponent;
import com.project.pan.myproject.dagger2.di.component.DaggerAppComponent;
import com.project.pan.myproject.dagger2.di.component.DaggerManComponent;
import com.project.pan.myproject.dagger2.di.component.FatherComponent;


/**
 * Author: panrongfu
 * Date:2018/6/26 20:18
 * Description:
 */

public class BaseApplication extends Application {

    private  static AppComponent appComponent;
    private static FatherComponent manComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        manComponent = DaggerManComponent.builder().context(this.getApplicationContext()).build();
        appComponent = DaggerAppComponent.builder()
                .name("aaa")
                .application(this)
                .build();
        //这里是为了应用设置异常处理，然后程序才能获取到未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }

    public static FatherComponent getManComponent(){
        return manComponent;
    }

}
