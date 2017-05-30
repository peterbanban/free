package com.example.administrator.free.ToolsHelper;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2017/05/29.
 */

public class HelloChartsHelper {

    String [] chartDate;
    int []source ;
    private List<PointValue> mPointValues=new ArrayList<>();
    private List<AxisValue> mAxisXValues=new ArrayList<>();  //轴线值
    LineChartView lineChart;

    public HelloChartsHelper(String[] chartDate, int[] source,  LineChartView lineChart) {
        this.chartDate = chartDate;
        this.source = source;
        this.lineChart = lineChart;
    }

    /**
     * 每个点的显示
     */
    public void getAxisPoints() {
        for (int i=0;i<source.length;i++){
            mPointValues.add(new PointValue(i,source[i]));
        }
    }

    /**
     * 设置X轴显示
     */
    public void getAxisXLables() {
        for(int i=0;i<chartDate.length;i++){
            mAxisXValues.add(new AxisValue(i).setLabel(chartDate[i]));
        }
    }

    /**
     * 图表初始化
     */
    public void initLineChart() {
        //设置折线属性

        Line line=new Line(mPointValues).setColor(Color.parseColor("#ffffcc"));
        List<Line> lines=new ArrayList<>();
        line.setShape(ValueShape.CIRCLE);//折线上每个数据点的形状
        line.setCubic(false);//曲线是否平滑
        line.setFilled(false);//是否填充曲线面积
        line.setHasLabels(true);//是否给数据坐标添加数据
        line.setHasPoints(true);//是否显示圆点
        line.setHasLines(true);//是否用线显示
        lines.add(line);
        LineChartData data=new LineChartData();
        data.setLines(lines);

        //设置坐标轴属性
        Axis axisX=new Axis();  //X轴
        axisX.setHasTiltedLabels(false); //设置字体是直的还是斜的
        axisX.setTextColor(Color.parseColor("#333333"));
        axisX.setTextSize(10); //设置字体大小
        axisX.setName("锁屏日期"); //设置X轴名称
        axisX.setMaxLabelChars(7); //最多几个X坐标轴
        axisX.setValues(mAxisXValues); //填充X轴坐标名称
        axisX.setHasLines(true);     //轴分割线
        data.setAxisXBottom(axisX); //x轴在底部

        //Y轴是根据数据大小自动设置上限
        Axis axisY=new Axis();  //Y轴
        axisY.setTextColor(Color.parseColor("#333333"));
        axisY.setLineColor(Color.parseColor("#333333"));
        axisY.setName("锁屏次数");//Y轴名称
        axisY.setTextSize(10);
        data.setAxisYLeft(axisY);//设置Y轴在左边
        //....属性同X轴

        //设置行为属性 缩放 平移和平滑
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom(2);  //最大放大比例
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        //下面四句话设置x轴显示数据个数0-7个，
        Viewport v=new Viewport(lineChart.getMaximumViewport());
        v.left=0;
        v.right=7;      //若不设置这句 则会将所有数据显示出来
        lineChart.setCurrentViewport(v);
    }



}
