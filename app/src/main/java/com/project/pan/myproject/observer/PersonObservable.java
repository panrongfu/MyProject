package com.project.pan.myproject.observer;

import java.util.Observable;

/**
 * Created by pan on 2018/6/20
 * 被观察者
 */

public class PersonObservable extends Observable {
    private String name;
    private int age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        //告知数据改变
        setChanged();
        //发送信号通知观察者。
        notifyObservers();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "PersonObservable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
