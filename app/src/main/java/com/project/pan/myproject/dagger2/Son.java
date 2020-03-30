package com.project.pan.myproject.dagger2;

import javax.inject.Inject;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/30 09:18
 * @Description: java类作用描述
 */
public class Son {
Bike mBike;
Car mCar;
    @Inject
   public Son(Bike bike, Car car) {
mBike = bike;
mCar = car;
    }

    public void go(){
        mBike.go();
        mCar.go();
    }
}
