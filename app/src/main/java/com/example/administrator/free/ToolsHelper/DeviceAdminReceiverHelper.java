package com.example.administrator.free.ToolsHelper;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2017/06/03.
 */

public class DeviceAdminReceiverHelper extends DeviceAdminReceiver {
     //封装获取组件名称
     public static ComponentName getCname(Context context){
         return new ComponentName(context,DeviceAdminReceiverHelper.class);
     }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
    }
}
