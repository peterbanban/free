package com.example.administrator.free.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.free.DataBaseRelated.DateRecode;
import com.example.administrator.free.DataBaseRelated.LockScreen;
import com.example.administrator.free.R;
import com.example.administrator.free.ToolsHelper.HelloChartsHelper;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.animation.ChartViewportAnimator;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import static lecho.lib.hellocharts.gesture.ContainerScrollType.VERTICAL;
import static lecho.lib.hellocharts.gesture.ZoomType.HORIZONTAL;


public class BlankFragment4 extends Fragment {
    View view;
    DateRecode dateRecode;

    String [] chartDate;
    int []source ;
    LineChartView lineChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataInit();
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_blank_fragment4, container, false);
        lineChart= (LineChartView) view.findViewById(R.id.hello_chart_Line);

        HelloChartsHelper chartsHelper=new HelloChartsHelper(chartDate,source,lineChart);
        chartsHelper.getAxisXLables();    //获取x轴的标签
        chartsHelper.getAxisPoints();    //获取坐标点
        chartsHelper.initLineChart();    //初始化
        lineChart.hasOnClickListeners();

        /**
         * 利用安卓事件分发机制，告诉父控件View不要拦截子控件的touch，使得lineChart的滑动不会引起viewpager的滑动
         * 如果返回true的话则会被拦截，子控件就不能响应滑动了
          */
        lineChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        lineChart.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:

                    case MotionEvent.ACTION_CANCEL:
                        lineChart.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }

        });
        return view;

    }

    private void dataInit() {

        //初始化得到锁屏次数表DateRecode的数据
        List<LockScreen> list1= DataSupport.findAll(LockScreen.class);
        String str="";
        DataSupport.deleteAll(DateRecode.class);
        for(LockScreen lock:list1){
            SimpleDateFormat format=new SimpleDateFormat("MM-dd");
           String str1=format.format(lock.getDateStart());
              if (!str.equals(str1)){
                  dateRecode=new DateRecode();
                  dateRecode.setDates(str1);
                  dateRecode.setCounts(1);
                  dateRecode.save();
                  str=str1;
              }
              else
              {
                  if(str1==""||str1==null)
                      continue;
                  int i=dateRecode.getCounts();
                  dateRecode.setCounts(i+1);
                  dateRecode.save();
              }
        }


        //计算每天锁屏次数并显示出来
        List<DateRecode> list2 =DataSupport.findAll(DateRecode.class);
        chartDate=new String[list2.size()];
        source=new int[list2.size()];
        int i=0,j=0;
        for (DateRecode recode:list2){
           source[i++]= recode.getCounts();
            chartDate[j++]= recode.getDates();
            Log.d("次数"+recode.getCounts(),"日期"+recode.getDates());
        }
    }


}
