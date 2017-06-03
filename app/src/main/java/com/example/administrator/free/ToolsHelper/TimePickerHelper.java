package com.example.administrator.free.ToolsHelper;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TimePicker;

import com.example.administrator.free.ActivityPage.LockScreenActivity;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/06/02.
 */

public class TimePickerHelper {
    //设置时间选择器
     public static TimePickerListener pickerListener;
    public TimePickerHelper(Context context,TimePickerListener pickerListener) {
        this.pickerListener=pickerListener;
        TimePickerMethod(context);
    }

     Calendar ca = Calendar.getInstance();
    int nowHour = ca.get(Calendar.HOUR_OF_DAY);
    int nowMinute = ca.get(Calendar.MINUTE);

    public void TimePickerMethod(Context context){
            TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                      if (pickerListener!=null){
                          pickerListener.onHourPicked(hourOfDay);
                          pickerListener.onMinutePicked(minute);
                      }
                }
            },nowHour,nowMinute,true);
            timePickerDialog.show();
        }
    public interface TimePickerListener{
       void onHourPicked(int hour);
       void onMinutePicked(int minute);
    }
}

