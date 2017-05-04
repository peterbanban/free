package com.example.administrator.free.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.free.R;

import lecho.lib.hellocharts.view.LineChartView;

import static lecho.lib.hellocharts.gesture.ContainerScrollType.VERTICAL;
import static lecho.lib.hellocharts.gesture.ZoomType.HORIZONTAL;


public class BlankFragment4 extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_blank_fragment4, container, false);
        LineChartView chartView= (LineChartView) view.findViewById(R.id.hello_chart);
        boolean IsInteractine=true;
        chartView.setInteractive(IsInteractine);
        chartView.setZoomType(HORIZONTAL);
        chartView.setContainerScrollEnabled(true, VERTICAL);
        return view;
    }


}
