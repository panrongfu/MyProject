package com.project.pan.myproject.dagger2.di.component;

import android.app.Application;


import com.project.pan.myproject.dagger2.AppTools;
import com.project.pan.myproject.dagger2.di.module.AppModule;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Author: panrongfu
 * Date:2018/6/26 14:48
 * Description:
 */
@Component(modules = {AppModule.class})
public interface AppComponent {
    AppTools getAppTools();


    void inject(Application application); //注入生命周期中

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder appModule(AppModule appModule);
        AppComponent build();
    }
}
