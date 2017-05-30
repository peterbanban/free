package com.example.administrator.free.ToolsHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class BootAutoStartReceiver extends BroadcastReceiver {

    ScreenBroadcastReceiver screenBroadcastReceiver;
 public final  String action_boot="android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(action_boot)){

            NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification=new NotificationCompat.Builder(context)
                    .setContentTitle("开机了")
                    .setContentText("成功了")
                    .setWhen(System.currentTimeMillis())
                    .build();
            manager.notify(2,notification);



            //注册监 控屏幕的广播
            screenBroadcastReceiver=new ScreenBroadcastReceiver();
            startScreenBroadCastReceiver();


        }
    }
    private void startScreenBroadCastReceiver() {
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
       AppContext.getContext().registerReceiver(screenBroadcastReceiver,filter);
    }
}
