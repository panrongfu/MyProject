package com.project.pan.myproject.dagger2.di.component;

import android.content.Context;

import com.project.pan.myproject.dagger2.Car;
import com.project.pan.myproject.dagger2.Man;
import com.project.pan.myproject.dagger2.SecondDagger2Activity;
import com.project.pan.myproject.dagger2.di.module.ManModule;
import com.project.pan.myproject.dagger2.di.module.SonModule;
import com.project.pan.myproject.dagger2.di.module.TaskModule;
import com.project.pan.myproject.dagger2.scope.ManScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/27 10:04
 * @Description: java类作用描述
 */
@ManScope
@Component(modules = {ManModule.class , TaskModule.class})
public interface ManComponent {
    // 继承关系中不用显式地提供暴露依赖实例的接口
//    Car offCar();
    SonComponent.Builder sonComponent();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        ManComponent build();
    }
}
