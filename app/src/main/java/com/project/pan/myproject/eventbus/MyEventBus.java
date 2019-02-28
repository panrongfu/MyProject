package com.project.pan.myproject.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

/**
 * @author: panrongfu
 * @date: 2019/2/16 15:18
 * @describe:
 */
public class MyEventBus extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new UnknownError());
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
