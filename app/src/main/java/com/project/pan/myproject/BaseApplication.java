package com.project.pan.myproject;

import android.app.Application;

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
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

}
