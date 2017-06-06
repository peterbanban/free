package com.example.administrator.free.ServiceHelper;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.free.ActivityPage.MainActivity;
import com.example.administrator.free.R;
import com.example.administrator.free.ReceiverHelper.ScreenBroadcastReceiver;

public class MonitorService extends Service {
    ScreenBroadcastReceiver screenBroadcastReceiver;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intentActivity=new Intent(context, MainActivity.class);
        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi=PendingIntent.getActivity(context,0,intentActivity,0);
        Notification notification=new NotificationCompat.Builder(context)
                .setContentTitle("free")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("次数")
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        screenBroadcastReceiver=new ScreenBroadcastReceiver();
        // startScreenBroadCastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(screenBroadcastReceiver,filter);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
