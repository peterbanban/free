package com.example.administrator.free.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.free.DataBaseRelated.DateRecode;
import com.example.administrator.free.R;
import com.example.administrator.free.ToolsHelper.HelloChartsHelper;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.List;
import lecho.lib.hellocharts.view.LineChartView;
public class BlankFragment4 extends Fragment {
    View view;
    String [] chartDate;
    int []source ;
    LineChartView lineChart;
    TextView txtLong;
    TextView txtShort;
    TextView txtMost;
    TextView txtLess;
    Button btnStart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_blank_fragment4, container, false);
        lineChart= (LineChartView) view.findViewById(R.id.hello_chart_Line);
         txtLong= (TextView) view.findViewById(R.id.tip_long);
         txtShort= (TextView) view.findViewById(R.id.tip_short);
         txtMost= (TextView) view.findViewById(R.id.tip_most);
         txtLess= (TextView) view.findViewById(R.id.tip_less);
         btnStart= (Button) view.findViewById(R.id.btn_start);
        dataInit();
        HelloChartsHelper chartsHelper=new HelloChartsHelper(chartDate,source,lineChart);
        chartsHelper.getAxisXLables();    //获取x轴的标签
        chartsHelper.getAxisPoints();    //获取坐标点
        chartsHelper.initLineChart();    //初始化
        //lineChart.hasOnClickListeners();


        /**
         * 利用安卓事件分发机制，告诉父控件View不要拦截子控件的touchEvent，使得lineChart的滑动不会引起viewpager的滑动
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
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;

    }


    private void dataInit() {
        //计算每天锁屏次数并显示出来
        int mostTime = 0;
        int lessTime = 1000;
        List<DateRecode> list2 = DataSupport.findAll(DateRecode.class);
        chartDate = new String[list2.size()];
        source = new int[list2.size()];
        int i = 0, j = 0;
        for (DateRecode recode : list2) {
            source[i++] = recode.getCounts();
            chartDate[j++] = recode.getDates();
            if (recode.getCounts() > mostTime)
                mostTime = recode.getCounts();

            if (recode.getCounts() < lessTime)
                lessTime = recode.getCounts();
        }
        txtMost.setText("近期最多锁屏次数： " + mostTime);
        txtLess.setText("近期最少锁屏次数： " + lessTime);

        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String str1 = format.format(System.currentTimeMillis());
        List<DateRecode> listRecode = DataSupport
                .where("dates=?",str1)
                .find(DateRecode.class);

        if (listRecode.size() > 0) {
            for (DateRecode dateRecode : listRecode) {
                if(dateRecode.getLessTime()<60000){
                     txtLong.setText("今日连续使用手机最长时间为："+dateRecode.getMostTime()/1000+"  min");
                     txtShort.setText("今日连续使用手机最短时间为："+dateRecode.getLessTime()/1000+"  s");
                }
                else {
                    txtLong.setText("今日连续使用手机最长时间为："+dateRecode.getMostTime()/60000+"  min");
                    txtShort.setText("今日连续使用手机最短时间为：" + dateRecode.getLessTime() / 60000 + "  min");
                }
            }
        }
    }

}
