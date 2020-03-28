package com.project.pan.myproject.dagger2;

import android.util.Log;

import javax.inject.Inject;

/**
 * Author: panrongfu
 * Date:2018/6/26 13:23
 * Description:
 */

public class Person {

    private String name;
    @Inject
    public Person(Favor favor, Favor2 favor2) {
       Log.e("person Favor", favor.name);
        Log.e("person Favor2", favor2.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
