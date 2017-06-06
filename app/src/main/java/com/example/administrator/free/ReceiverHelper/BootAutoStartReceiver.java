package com.example.administrator.free.ReceiverHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;

public class BootAutoStartReceiver extends BroadcastReceiver {

    //开机自启并启用监听屏幕的广播
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
            manager.notify(1,notification);

            //启动app包名
            Intent newIntent= context.getPackageManager().getLaunchIntentForPackage("package com.example.administrator.free");
            context.startActivity(newIntent);


            //注册监 控屏幕的广播
            screenBroadcastReceiver=new ScreenBroadcastReceiver();
           // startScreenBroadCastReceiver();
            IntentFilter filter=new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_USER_PRESENT);
           context.registerReceiver(screenBroadcastReceiver,filter);


        }

    }
   // private void startScreenBroadCastReceiver() {
//        IntentFilter filter=new IntentFilter();
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_USER_PRESENT);
//       AppContext.getContext().registerReceiver(screenBroadcastReceiver,filter);
//    }
}
