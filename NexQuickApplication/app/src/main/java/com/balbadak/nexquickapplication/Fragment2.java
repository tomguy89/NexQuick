package com.balbadak.nexquickapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.widget.Button;


@SuppressLint("ValidFragment")
public class Fragment2 extends Fragment {


    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view  =inflater.inflate(R.layout.fragment_neworder2, container, false);

    Button nextBtn = (Button) view.findViewById(R.id.next3p);
    Button prevBtn = (Button) view.findViewById(R.id.prev1p);
        viewPager = getActivity().findViewById(R.id.pager);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });




        return view;
    }
}
