package com.project.pan.myproject.rxjava.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: panrongfu
 * @date: 2019/2/15 16:21
 * @describe:
 */
public class User implements Observer {
    public String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Hi "+name +",公众号更新了内容："+arg);
    }
}
