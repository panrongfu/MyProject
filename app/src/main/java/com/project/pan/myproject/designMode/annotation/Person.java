package com.project.pan.myproject.designMode.annotation;

import android.util.Log;

/**
 * Author: panrongfu
 * Date:2018/7/2 20:26
 * Description:
 */

public class Person {

    String name;
    int age;

    @BindName(name = "abc")
    public void show(String name){
      //  System.out.println(name);
        Log.e("show",name);
    }
}
