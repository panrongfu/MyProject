package com.project.pan.myproject.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {
    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TestService","onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("TestService","onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TestService","onDestroy");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TestService","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
