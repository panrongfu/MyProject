package com.project.pan.myproject.dagger2.di.module;


import android.app.Application;

import com.project.pan.myproject.dagger2.AppTools;

import dagger.Module;
import dagger.Provides;

/**
 * Author: panrongfu
 * Date:2018/6/26 14:49
 * Description:
 */

@Module
public class AppModule {

    @Provides
    AppTools provideAppTools(String name){
        return new AppTools(name);
    }
}
