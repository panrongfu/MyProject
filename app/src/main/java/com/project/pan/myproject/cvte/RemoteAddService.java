package com.project.pan.myproject.cvte;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteAddService extends Service {


    RemoteAdd.Stub binder = new RemoteAdd.Stub() {
        @Override
        public void add(int a, int b) throws RemoteException {

        }
    };

    public RemoteAddService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
