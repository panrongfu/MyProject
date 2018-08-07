package com.project.pan.myproject.ipc;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 * @author pan
 */
public class MyIntentService extends IntentService {



    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            // TODO: 2018/7/30 做一些该做的事情，如果数据结果需要返回到页面，可以通过广播发送回启动页面
        }
    }
}
