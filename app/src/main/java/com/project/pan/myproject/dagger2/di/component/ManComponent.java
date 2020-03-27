package com.project.pan.myproject.dagger2.di.component;

import com.project.pan.myproject.dagger2.DaggerActivity;
import com.project.pan.myproject.dagger2.Man;

import dagger.Component;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/27 10:04
 * @Description: java类作用描述
 */
@Component
public interface ManComponent {

    void inject(Man man);
}
