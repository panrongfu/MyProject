package com.project.pan.myproject.dagger2;

import com.project.pan.myproject.dagger2.di.component.DaggerManComponent;
import com.project.pan.myproject.dagger2.di.component.ManComponent;

import javax.inject.Inject;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/27 10:04
 * @Description: java类作用描述
 */
public class Man {
    @Inject
    Car mCar;

    public Man() {
        DaggerManComponent.create().inject(this);
    }
}
