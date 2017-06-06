package com.example.administrator.free.ReceiverHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.free.ActivityPage.MainActivity;
import com.example.administrator.free.ServiceHelper.MonitorService;

public class BootAutoStartReceiver extends BroadcastReceiver {

    //开机自启并启用监听屏幕的广播
   //  ScreenBroadcastReceiver screenBroadcastReceiver;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().toString().equals(Intent.ACTION_BOOT_COMPLETED)){

            //通过开机广播启动屏幕监听和通知的服务
            Intent intentService =new Intent(context, MonitorService.class);
            MonitorService.context=context;
         //   intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(intentService);

           // 下面能够实现
//            Intent intent1=new Intent(context, MainActivity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent1);


            //待验证是否可行
            //启动app包名
//            Intent newIntent= context.getPackageManager().getLaunchIntentForPackage("package com.example.administrator.free");
//            context.startActivity(newIntent);
//            Log.d("开机自启","这是一条广播");
//            Toast.makeText(context, "接收到开机启动广播", Toast.LENGTH_LONG).show();

//            注册监 控屏幕的广播
   //         screenBroadcastReceiver=new ScreenBroadcastReceiver();
           // startScreenBroadCastReceiver();
//            IntentFilter filter=new IntentFilter();
//            filter.addAction(Intent.ACTION_SCREEN_OFF);
//            filter.addAction(Intent.ACTION_SCREEN_ON);
//            filter.addAction(Intent.ACTION_USER_PRESENT);
//           context.registerReceiver(screenBroadcastReceiver,filter);


        }

    }
}
