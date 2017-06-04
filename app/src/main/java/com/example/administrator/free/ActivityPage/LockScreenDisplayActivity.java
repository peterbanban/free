package com.example.administrator.free.ActivityPage;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.administrator.free.R;
import com.example.administrator.free.ToolsHelper.FlagHelper;

public class LockScreenDisplayActivity extends AppCompatActivity {
    Button btnExit;
    TextView tvStart;
    TextView tvEnd;
    TextView tvCountdown;
    TextView tvCongratulation;
    Handler handler=new Handler();
    int cdm,cdh;
    int num ;
    int h;
    int k;
    int j = 59;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen_display);
        btnExit= (Button) findViewById(R.id.btn_exit);
        tvCountdown= (TextView) findViewById(R.id.tv_countdown);
        tvEnd= (TextView) findViewById(R.id.tv_timeEnd);
        tvStart= (TextView) findViewById(R.id.tv_timeStart);
        tvCongratulation= (TextView) findViewById(R.id.tv_congratulate);
        //设置当锁屏时就出现，不设置会在原生锁屏解锁后才会出现
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        //onBackPressed();

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String startTime= FlagHelper.startTime;
        String endTime=FlagHelper.endTime;
        cdm= FlagHelper.countdownMinute;
        cdh= FlagHelper.countdownHour;
        tvStart.setText("开始时间   "+startTime);
        tvEnd.setText("结束时间   "+endTime);
        num = 3600 * cdh + 60 * cdm;
        handler.postDelayed(runnable,1000);
    }

    //考虑一个问题，每次进入解锁此页面都要初始化一次，会影响num的值吗 ，还有对进程的影响！！！！

    //倒计时
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            for (int i = num; i >= 0; i--) {
                h = i % 3600;
                k = (i - h * 3600) / 60;
                tvCountdown.setText(h + ":" + k + ":" + j--);
                handler.postDelayed(this, 1000);
                if (j == 0) {
                    j = 59;
                }
                if (i==0){
                    tvCongratulation.setText("恭喜你完成挑战");
                }
            }
          //  handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onBackPressed() {
        //重写back按钮的监听事件
        Intent intent1=new Intent(Intent.ACTION_MAIN);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent1);

    }

}
