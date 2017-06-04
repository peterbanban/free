package com.example.administrator.free.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.free.ActivityPage.LockScreenActivity;
import com.example.administrator.free.R;


public class BlankFragment3 extends Fragment {
    Button btnStart;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
        btnStart= (Button) view.findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=getActivity();
                Intent intent1=new Intent(context, LockScreenActivity.class);
                context.startActivity(intent1);

            }
        });
        return view;
    }


}
