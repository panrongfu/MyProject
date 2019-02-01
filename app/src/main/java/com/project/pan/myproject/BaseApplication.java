package com.project.pan.myproject;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.project.pan.myproject.crash.CrashHandler;
import com.project.pan.myproject.dagger2.di.component.AppComponent;
import com.project.pan.myproject.dagger2.di.component.DaggerAppComponent;
import com.project.pan.myproject.dagger2.di.module.AppModule;


/**
 * Author: panrongfu
 * Date:2018/6/26 20:18
 * Description:
 */

public class BaseApplication extends Application {

   static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();

        appComponent.inject(this);
        //这里是为了应用设置异常处理，然后程序才能获取到未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
