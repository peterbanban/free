package com.example.administrator.free.ToolsHelper;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/06/06.
 */

public class CountDownHelper extends CountDownTimer{
     TextView textView;

    //参数为计时器总时长和计时器时间间隔以及要更新的textView
    public CountDownHelper(long millisInFuture, long countDownInterval,TextView view) {
        super(millisInFuture, countDownInterval);
        this.textView=view;
    }

    @Override                //计时器计时过程
    public void onTick(long millisUntilFinished) {
       long millisUntilFinished1 = millisUntilFinished/1000;
         textView.setText(millisUntilFinished1/3600+":"+(millisUntilFinished1%3600)/60+":"+millisUntilFinished1%60);
    }

    @Override                //计时器出发完毕
    public void onFinish() {
         textView.setText("恭喜完成挑战");
    }
}
