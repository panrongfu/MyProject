package com.project.pan.myproject.dagger2.di.module

import android.app.Activity
import com.project.pan.myproject.dagger2.activity.SupportActivity
import com.project.pan.myproject.dagger2.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


/**
 * @Author: panrongfu
 * @CreateDate: 2020/4/1 13:59
 * @Description: java类作用描述
 */
@Module
abstract class ActivityBindModule {

//    @Binds
//    @IntoMap
//    @ActivityKey(SupportActivity::class)
//    abstract fun bindAndroidInjectorFactory(builder: SupportActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @ActivityScope
    @ContributesAndroidInjector(modules = [SupportModule::class])
    abstract fun supportActivityInjector(): SupportActivity
}