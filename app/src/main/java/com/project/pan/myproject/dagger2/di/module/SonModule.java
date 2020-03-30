package com.project.pan.myproject.dagger2.di.module;

import com.project.pan.myproject.dagger2.Bike;
import com.project.pan.myproject.dagger2.Car;
import com.project.pan.myproject.dagger2.di.component.SonComponent;

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
    Bike provideCar(){
        return new Bike();
    }
}
