package com.project.pan.myproject.dagger2.di.module;

import com.project.pan.myproject.dagger2.Bike;
import com.project.pan.myproject.dagger2.Car;

import dagger.Module;
import dagger.Provides;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/30 09:25
 * @Description: java类作用描述
 */
@Module
public class ManModule {

    @Provides
    Car provideCar() {
        return new Car();
    }
}
