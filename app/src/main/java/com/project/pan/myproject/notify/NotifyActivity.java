package com.project.pan.myproject.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.project.pan.common.global.ARouterPaths;
import com.project.pan.myproject.R;

@Route(path = ARouterPaths.NOTIFY_ACTIVITY)
public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
    }

    public void sendNotify(View view){
//        Notification notification = new Notification();
//        notification.icon = R.drawable.btn_tick_pressed;
//        notification.tickerText = "hello notify";
//        notification.when = System.currentTimeMillis();
//        notification.flags = Notification.FLAG_AUTO_CANCEL;
//
        Intent intent = new Intent(this,NotifyActivity.class);

        PendingIntent pendingIntent = PendingIntent.
                getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //notification.contentIntent = pendingIntent;

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.btn_tick_pressed);
        builder.setContentTitle("最简单的notification");
        builder.setContentText("内容");
        builder.setContentIntent(pendingIntent);

        manager.notify(1,builder.build());
    }
}
