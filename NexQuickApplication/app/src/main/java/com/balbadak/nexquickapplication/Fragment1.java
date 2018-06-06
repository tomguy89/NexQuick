package com.balbadak.nexquickapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.annotation.SuppressLint;
import android.widget.Button;


@SuppressLint("ValidFragment")
public class Fragment1 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_neworder1, container, false);


        Button nextBtn = (Button) view.findViewById(R.id.next2p);


        nextBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.pager);
                viewPager.setCurrentItem(1);
            }
        });


        return view;




    }




}
