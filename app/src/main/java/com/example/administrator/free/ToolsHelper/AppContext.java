package com.example.administrator.free.ToolsHelper;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.AppLaunchChecker;

/**
 * Created by Administrator on 2017/05/09.
 */

public class AppContext extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }
}
