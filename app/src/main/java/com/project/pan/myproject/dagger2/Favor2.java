package com.project.pan.myproject.dagger2;

import com.project.pan.myproject.dagger2.qualifier.MyString;
import com.project.pan.myproject.dagger2.qualifier.YourString;

import javax.inject.Inject;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/28 10:30
 * @Description: java类作用描述
 */
public class Favor2 {
    String name;

    @Inject
    public Favor2( @YourString String name) {
        this.name = name;
    }

}
