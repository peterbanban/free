package com.example.administrator.free.ActivityPage;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.administrator.free.Fragment.BlankFragment1;
import com.example.administrator.free.Fragment.BlankFragment2;
import com.example.administrator.free.Fragment.BlankFragment3;
import com.example.administrator.free.Fragment.BlankFragment4;
import com.example.administrator.free.R;
import com.example.administrator.free.ReceiverHelper.ScreenBroadcastReceiver;
import com.example.administrator.free.ServiceHelper.MonitorService;

import org.litepal.LitePal;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnCheckedChangeListener {

    private  ViewPager mainViewPager;
    private  RadioGroup mainRadioGroup;
    RadioButton mainBtn1;
    RadioButton mainBtn2;
    RadioButton mainBtn3;
    RadioButton mainBtn4;
    ArrayList<Fragment> fragmentList;
    private ScreenBroadcastReceiver screenBroadcastReceiver=new ScreenBroadcastReceiver();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        InitView();                                 //初始化控件
        InitViewPager();                            //初始化pageView和fragment
        LitePal.getDatabase();                      //创建数据库
        MonitorService.context=MainActivity.this;   //开启服务用于广播监听
        Intent intentService=new Intent(MainActivity.this,MonitorService.class);
        startService(intentService);
    }

    private void InitView() {
        mainRadioGroup= (RadioGroup) findViewById(R.id.main_RadioGroup);
        mainBtn1= (RadioButton) findViewById(R.id.radio_btn1);
        mainBtn2= (RadioButton) findViewById(R.id.radio_btn2);
        mainBtn3= (RadioButton) findViewById(R.id.radio_btn3);
        mainBtn4= (RadioButton) findViewById(R.id.radio_btn4);
        mainRadioGroup.setOnCheckedChangeListener(this);
    }
    private void InitViewPager() {
        mainViewPager = (ViewPager) findViewById(R.id.main_viewPage);
        fragmentList = new ArrayList<>();

        Fragment btn1Fragment=new BlankFragment1();
        Fragment btn2Fragment=new BlankFragment2();
        Fragment btn3Fragment=new BlankFragment3();
        Fragment btn4Fragment=new BlankFragment4();


        fragmentList.add(btn1Fragment);
        fragmentList.add(btn2Fragment);
        fragmentList.add(btn4Fragment);
        fragmentList.add(btn3Fragment);

        //设置ViewPage适配器
        mainViewPager.setAdapter(new  MyFragmentAdapter(getSupportFragmentManager(),fragmentList));
        //当前list里0为第一个页面
        mainViewPager.setCurrentItem(0);
        //ViewPager的页面改变监听器
        mainViewPager.addOnPageChangeListener(new MyListener() );
    //    mainViewPager.setOn PageChangeListener(new MyListener() );
    }
    public class MyFragmentAdapter extends FragmentPagerAdapter{
        ArrayList<Fragment>list;

        public  MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

    }

    public class MyListener implements OnPageChangeListener{

        //获取当前页面用于改变RadioButton的状态
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {
           int current=mainViewPager.getCurrentItem();
            switch (current){
                case 0:
                    mainRadioGroup.check(R.id.radio_btn1);
                    break;
                case 1:
                    mainRadioGroup.check(R.id.radio_btn2);
                    break;
                case 2:
                    mainRadioGroup.check(R.id.radio_btn3);
                    break;
                case 3:
                    mainRadioGroup.check(R.id.radio_btn4);
                    break;
                default:
                    break;

            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
         int current=0;
         switch (checkedId){
             case R.id.radio_btn1:
                 current=0;
                 break;
             case R.id.radio_btn2:
                 current=1;
                 break;
             case R.id.radio_btn3:
                 current=2;
                 break;
             case R.id.radio_btn4:
                 current=3;
                 break;
         }
         if (mainViewPager.getCurrentItem()!=current){
             mainViewPager.setCurrentItem(current);

         }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
              case R.id.menu1:
                  break;
            case R.id.menu2:
                  break;
            case R.id.menu3:
                  break;
            case R.id.menu4:
                break;
            default:

        }
        return true;
    }


    @Override   //此方法动态更新menu, 通过反射 用于动态显示menu的图标
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
         if(menu!=null){
             if(menu.getClass()== MenuBuilder.class){  //MenuBuilder类有设置menu的方法 setIconEnable被设置为了false
                 try {
                     //method是反射包中的类，提供关于类和接口上的单独的某个方法
                     Method method=menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                     method.setAccessible(true);  //设置是许访问
                     method.invoke(menu,true);  //调用指定对象的底层方法并设置为true
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     //    unregisterReceiver(screenBroadcastReceiver);//动态注册的广播要销毁掉
    }
}
