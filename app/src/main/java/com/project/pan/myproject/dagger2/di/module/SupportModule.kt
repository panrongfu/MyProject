package com.project.pan.myproject.dagger2.di.module

import com.project.pan.myproject.dagger2.Bike
import dagger.Module
import dagger.Provides


/**
 * @Author: panrongfu
 * @CreateDate: 2020/4/1 14:53
 * @Description: java类作用描述
 */
@Module
class SupportModule {

    @Provides
    fun provideBike() = Bike()
}