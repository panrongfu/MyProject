package com.project.pan.myproject.dagger2.di.component

import com.project.pan.myproject.dagger2.activity.SupportActivity
import com.project.pan.myproject.dagger2.di.module.ActivityBindModule
import com.project.pan.myproject.dagger2.di.module.SupportModule
import dagger.Component
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


/**
 * @Author: panrongfu
 * @CreateDate: 2020/4/1 13:51
 * @Description: java类作用描述
 */
@Subcomponent(modules = [AndroidInjectionModule::class, SupportModule::class])
interface SupportActivitySubComponent: AndroidInjector<SupportActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SupportActivity>()
}