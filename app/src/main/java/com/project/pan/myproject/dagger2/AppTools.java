package com.project.pan.myproject.dagger2;

import android.util.Log;

import javax.inject.Inject;

/**
 * Author: panrongfu
 * Date:2018/6/26 14:50
 * Description:
 */

public class AppTools {
    String name;
    public AppTools(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showTools(){
        Log.e("AppTools","我是一个工具 我的名字是：" +getName());
    }
}
