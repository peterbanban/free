package com.example.administrator.free.ToolsHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.widget.Toast;

import com.example.administrator.free.DataBaseRelated.LockScreen;

import org.litepal.crud.DataSupport;

import java.util.Date;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private String action = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();

        if (Intent.ACTION_SCREEN_ON.equals(action))
        {
           //亮屏
        }

        else if (Intent.ACTION_USER_PRESENT.equals(action))
        {
            //解锁
            LockScreen lockScreen=new LockScreen();
            lockScreen.setDateStart(new Date(System.currentTimeMillis()));
            lockScreen.save();
        }

        else if (Intent.ACTION_SCREEN_OFF.equals(action))
        {
             //锁屏
            long id= DataSupport.max(LockScreen.class,"id",long.class);
            if(id>0){
            LockScreen beforeDate=DataSupport.find(LockScreen.class,id);
            Date beforeStart=beforeDate.getDateStart();
            beforeDate.setDateEnd(new Date(System.currentTimeMillis()));
            beforeDate.setInterval(System.currentTimeMillis()-beforeStart.getTime());
            beforeDate.save();
            }
        }


    }

}
