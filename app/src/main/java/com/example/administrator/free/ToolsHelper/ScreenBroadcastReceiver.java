package com.example.administrator.free.ToolsHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.free.DataBaseRelated.DateRecode;
import com.example.administrator.free.DataBaseRelated.LockScreen;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private String action = null;
   static private  long Time1=0;
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

            //记录每天解锁次数
            SimpleDateFormat format=new SimpleDateFormat("MM-dd");
            String str1=format.format(System.currentTimeMillis());
            List<DateRecode> listRecode=DataSupport
                    .where("dates=?",str1)
                    .find(DateRecode.class);
            Log.d("12","即将进入解锁");
            if (listRecode.size()>0) {
                Log.d("12","进入解锁");
                for (DateRecode dateRecode : listRecode) {
                        dateRecode.setCounts(dateRecode.getCounts() + 1);
                        dateRecode.save();
                    Log.d(dateRecode.getDates()+"/"+dateRecode.getCounts()+"/","解锁");
                }
            } else{
                   DateRecode dateRecode=new DateRecode();
                   dateRecode.setDates(str1);
                   dateRecode.setCounts(1);
                   dateRecode.setMostTime(0);
                   dateRecode.setLessTime(100000);
                   dateRecode.save();
                   Time1=0;
                 }
        }

        else if (Intent.ACTION_SCREEN_OFF.equals(action))
        {
             //锁屏
            long id= DataSupport.max(LockScreen.class,"id",long.class);
            if(id>0){
            LockScreen beforeDate=DataSupport.find(LockScreen.class,id);
            Date beforeStart=beforeDate.getDateStart();
            beforeDate.setDateEnd(new Date(System.currentTimeMillis()));
            Time1=System.currentTimeMillis()-beforeStart.getTime();
            beforeDate.setInterval(Time1); //设置间隔时间
            beforeDate.save();
            }

            SimpleDateFormat format=new SimpleDateFormat("MM-dd");
            String str1=format.format(System.currentTimeMillis());
            List<DateRecode> listRecode=DataSupport
                    .where("dates=?",str1)
                    .find(DateRecode.class);
            if (listRecode.size()>0) {
                for (DateRecode dateRecode : listRecode) {
                  if (dateRecode.getMostTime()<Time1)
                      dateRecode.setMostTime(Time1);
                    if(dateRecode.getLessTime()>Time1)
                        dateRecode.setLessTime(Time1);
                  dateRecode.save();
                    Log.d("时长",""+dateRecode.getMostTime()+"/"+Time1);
                }

            }
        }
    }

}
