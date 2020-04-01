package com.project.pan.myproject.dagger2.di.component;

import com.project.pan.myproject.dagger2.activity.SecondDagger2Activity;
import com.project.pan.myproject.dagger2.di.module.SonModule;
import com.project.pan.myproject.dagger2.scope.SonScope;

import dagger.Subcomponent;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/30 09:20
 * @Description: java类作用描述
 */
@SonScope
@Subcomponent(modules = SonModule.class)
public interface SonComponent {
    void inject(SecondDagger2Activity secondDagger2Activity);

    @Subcomponent.Builder
    interface Builder{
        // SubComponent 必须显式地声明 Subcomponent.Builder，parent Component 需要用 Builder 来创建 SubComponent
        SonComponent build();
    }
}
