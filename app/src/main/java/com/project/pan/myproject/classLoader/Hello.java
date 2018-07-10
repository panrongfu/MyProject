package com.project.pan.myproject.classLoader;



/**
 * author: panrongfu
 * date:2018/7/4 14:44
 * describe:
 */

public class Hello {
    public static void main(String[] args){
        System.out.println("aaaaaaaa");

        ClassLoader cl = Hello.class.getClassLoader();
        System.out.println("ClassLoader is:"+cl.toString());

        //BaseDexClassLoader
    }
}
