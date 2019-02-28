package com.project.pan.myproject.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TestBindService extends Service {

    public TestBindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("TestBindService","onBind");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TestBindService","onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("TestBindService","onStart");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("TestBindService","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TestBindService","onDestroy");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TestBindService","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    // 添加一个类继承Binder
    public  class MyBinder extends Binder {
        // 添加要与外界交互的方法
        public void  getStringInfo(){
            Log.e("TestBindService","getStringInfo");
        }
    }
}
