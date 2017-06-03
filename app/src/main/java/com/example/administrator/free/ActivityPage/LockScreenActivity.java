package com.example.administrator.free.ActivityPage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.administrator.free.DataBaseRelated.LockScreen;
import com.example.administrator.free.R;
import com.example.administrator.free.ToolsHelper.DeviceAdminReceiverHelper;
import com.example.administrator.free.ToolsHelper.TimePickerHelper;

import org.litepal.tablemanager.typechange.BlobOrm;

import java.util.Calendar;

public class LockScreenActivity extends AppCompatActivity implements View.OnClickListener {
    public static int mHourStart, mMinuteStart,mHourEnd,mMinuteEnd;
    Button btnStart,btnEnd,btnOnStart;
    TextView startDisplay,endDisplay;
    private  DevicePolicyManager devicePolicyManager=null;
    private static final int REQUEST_CODE_ADD_DEVICE_ADMIN=10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        btnStart = (Button) findViewById(R.id.timeStart);
        btnEnd = (Button) findViewById(R.id.timeEnd);
        btnOnStart= (Button) findViewById(R.id.setOver);
        startDisplay = (TextView) findViewById(R.id.startDisplay);
        endDisplay = (TextView) findViewById(R.id.endDisplay);
        //设置dialog的属性
        Window window = getWindow();
        final WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = 1000;
        layoutParams.height = 1500;
        layoutParams.gravity = Gravity.CENTER;

        layoutParams.alpha = 0.8f;                       //透明度
        window.setAttributes(layoutParams);
        btnEnd.setOnClickListener(LockScreenActivity.this);
        btnStart.setOnClickListener(LockScreenActivity.this);
        btnOnStart.setOnClickListener(LockScreenActivity.this);

    }

    private void startAddDeviceAdmin() {
        Intent intent =new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,DeviceAdminReceiverHelper.getCname(this));
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"请先注册此组件开启锁屏");
        startActivityForResult(intent,REQUEST_CODE_ADD_DEVICE_ADMIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            devicePolicyManager.lockNow();
            finish();
        }else {
            startAddDeviceAdmin();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.timeStart:{
                new TimePickerHelper(LockScreenActivity.this, new TimePickerHelper.TimePickerListener() {
                    @Override
                    public void onHourPicked(int hour) {
                        mHourStart = hour;
                        startDisplay.setText(mHourStart + ":");
                    }

                    @Override
                    public void onMinutePicked(int minute) {
                        mMinuteStart = minute;
                        startDisplay.setText("  " + String.valueOf(mHourStart) + ":" + mMinuteStart);
                    }
                });
                 break;
            }
            case R.id.timeEnd:{
                new TimePickerHelper(LockScreenActivity.this, new TimePickerHelper.TimePickerListener() {
                    @Override
                    public void onHourPicked(int hour) {
                        mHourEnd = hour;
                    }

                    @Override
                    public void onMinutePicked(int minute) {
                        mMinuteEnd = minute;
                        if (mHourStart > mHourEnd || (mHourEnd == mHourStart && mMinuteStart > mMinuteEnd)) {
                            endDisplay.setText("  " + String.valueOf(mHourEnd) + ":" + mMinuteEnd + "   结束时间应大于开始时间，请重新选择");
                            endDisplay.setTextColor(Color.parseColor("#ff0000"));
                        } else {
                            endDisplay.setText("  " + String.valueOf(mHourEnd) + ":" + mMinuteEnd);
                            endDisplay.setTextColor(Color.parseColor("#808080"));
                        }
                    }
                });
                break;
            }
            case R.id.setOver:{
                devicePolicyManager= (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
                if (devicePolicyManager.isAdminActive(DeviceAdminReceiverHelper.getCname(this))){
                    devicePolicyManager.lockNow();
                    finish();
                }else
                    startAddDeviceAdmin();
                 break;
            }

        }
    }

}
