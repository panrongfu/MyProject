package com.project.pan.myproject.dagger2;

import android.util.Log;

import com.project.pan.myproject.dagger2.qualifier.Name;

import javax.inject.Inject;

/**
 * Author: panrongfu
 * Date:2018/6/26 13:23
 * Description:
 */

public class Person {

    private String name;
    @Inject
    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
