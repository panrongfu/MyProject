package com.project.pan.myproject.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author pan
 */
public class MessengerService extends Service {
    public MessengerService() {
    }

    private static class MessengerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    // TODO: 2018/7/30 做具体的业务逻辑处理
                     //....
                    Log.e("handleMessage","get data from client");
                    // TODO: 2018/7/30 如果要回复消息给客户端则需如下操作
                    //这里就是客户端设置的messenger
                    Messenger client = msg.replyTo;
                    Message replyMsg = Message.obtain(null, 0);
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                   break;
            }
        }
    }
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
    }
}
