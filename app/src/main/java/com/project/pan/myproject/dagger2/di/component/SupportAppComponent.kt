package com.project.pan.myproject.dagger2.di.component

import com.project.pan.myproject.dagger2.TomHobby
import com.project.pan.myproject.dagger2.di.module.ActivityBindModule
import com.project.pan.myproject.dagger2.di.module.SupportAppModule
import com.project.pan.myproject.dagger2.qualifier.Name
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * @Author: panrongfu
 * @CreateDate: 2020/4/1 13:38
 * @Description: java类作用描述
 */
@Component(modules = [SupportAppModule::class, AndroidInjectionModule::class, ActivityBindModule::class])
 interface SupportAppComponent {

}