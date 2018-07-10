package com.project.pan.myproject.dagger2.di.module;


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
    AppTools provideAppTools(){
        return new AppTools();
    }
}
