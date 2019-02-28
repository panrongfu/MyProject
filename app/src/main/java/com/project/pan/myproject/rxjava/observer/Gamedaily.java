package com.project.pan.myproject.rxjava.observer;

import java.util.Observable;

/**
 * @author: panrongfu
 * @date: 2019/2/15 16:22
 * @describe:
 */
public class Gamedaily extends Observable {

    public void postNewArticle(String content){
        //内容发生改变
        setChanged();
        //通知所有订阅者改变的内容
        notifyObservers(content);
    }
}
