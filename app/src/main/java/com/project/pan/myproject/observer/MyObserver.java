package com.project.pan.myproject.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by pan on 2018/6/20.
 */

public class MyObserver implements Observer {

    private int id;
    private PersonObservable observable;

    @Override
    public void update(Observable observable, Object arg) {
        System.out.println("观察者---->" + id + "得到更新");
        this.observable = (PersonObservable) observable;
        System.out.println(((PersonObservable) observable).toString());
    }

    public MyObserver() {
        //System.out.println("我是观察者---->" + id);
        //this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonObservable getObservable() {
        return observable;
    }

    public void setObservable(PersonObservable observable) {
        this.observable = observable;
    }
}
