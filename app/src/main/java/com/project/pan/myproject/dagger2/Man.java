package com.project.pan.myproject.dagger2;

import android.util.Log;

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
    public Man(Car car) {
        car.go();
    }
}
