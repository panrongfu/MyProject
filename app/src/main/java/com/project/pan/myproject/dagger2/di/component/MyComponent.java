package com.project.pan.myproject.dagger2.di.component;

import com.project.pan.myproject.dagger2.DaggerActivity;
import com.project.pan.myproject.dagger2.di.module.MyModule;
import dagger.Component;

/**
 * Author: panrongfu
 * Date:2018/6/26 13:13
 * Description:
 */
@Component(modules =  {MyModule.class}, dependencies = {AppComponent.class})
public interface MyComponent {

    void inject(DaggerActivity daggerActivity);
}
