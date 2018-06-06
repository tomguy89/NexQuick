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
public class Fragment3 extends Fragment {

    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_neworder3, container, false);


        Button prevBtn = (Button) view.findViewById(R.id.prev2p);
        viewPager = getActivity().findViewById(R.id.pager);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        Button pay1 = (Button) view.findViewById(R.id.pay1);

        pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DialogPayActivity.class);
                startActivityForResult(intent, 2000);
            }
        });

        return view;
    }

}
