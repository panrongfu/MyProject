package com.project.pan.myproject.dagger2;

import javax.inject.Inject;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/27 10:04
 * @Description: java类作用描述
 */
public class Father {

    @Inject
    public Father(Car car) {
        car.go();
    }
}
