package com.project.pan.myproject.dagger2.di.module;

import com.project.pan.myproject.dagger2.Bike;
import com.project.pan.myproject.dagger2.Car;
import com.project.pan.myproject.dagger2.di.component.SonComponent;
import com.project.pan.myproject.dagger2.scope.SonScope;

import dagger.Module;
import dagger.Provides;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/30 09:24
 * @Description: java类作用描述
 */
//@Module(subcomponents = SonComponent.class)
@Module()
public class SonModule {

    @Provides
    @SonScope
    Bike provideCar(){
        return new Bike();
    }
}
