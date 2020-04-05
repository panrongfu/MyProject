package com.project.pan.myproject.dagger2.di.component

import android.app.Application
import com.project.pan.myproject.BaseApplication
import com.project.pan.myproject.dagger2.TomHobby
import com.project.pan.myproject.dagger2.di.module.ActivityBindModule
import com.project.pan.myproject.dagger2.di.module.SupportAppModule
import com.project.pan.myproject.dagger2.qualifier.Name
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

/**
 * @Author: panrongfu
 * @CreateDate: 2020/4/1 13:38
 * @Description: java类作用描述
 */
@Component(modules = [SupportAppModule::class,
 AndroidInjectionModule::class,
 AndroidSupportInjectionModule::class,
 ActivityBindModule::class])
 interface SupportAppComponent {
   fun inject(application: BaseApplication)

   @Component.Builder
   interface Builder {
     fun build(): SupportAppComponent
   }
}