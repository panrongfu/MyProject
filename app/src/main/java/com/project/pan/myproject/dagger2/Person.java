package com.project.pan.myproject.dagger2;

import javax.inject.Inject;

/**
 * Author: panrongfu
 * Date:2018/6/26 13:23
 * Description:
 */

public class Person {

    private String name;
    private Hobby mHobby;

    @Inject
    public Person(Hobby hobby) {
        this.mHobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
