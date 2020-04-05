package com.project.pan.myproject.dagger2.di.module

import android.support.v7.app.AppCompatActivity
import com.project.pan.myproject.dagger2.activity.SupportActivity
import com.project.pan.myproject.dagger2.di.component.SupportActivitySubComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


/**
 * @Author: panrongfu
 * @CreateDate: 2020/4/1 13:59
 * @Description: java类作用描述
 */
@Module(subcomponents = [SupportActivitySubComponent::class])
//@Module
 public abstract class ActivityBindModule {

    @Binds
    @IntoMap
    @ClassKey(SupportActivity::class)
    internal abstract fun bindAndroidInjectorFactory(builder: SupportActivitySubComponent.Builder): AndroidInjector.Factory<*>

//    @ActivityScope3.20-*
//    @ContributesAndroidInjector(modules = [SupportModule::class])
//    abstract fun supportActivityInjector(): SupportActivity
}