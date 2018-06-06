package com.balbadak.nexquickapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Fragment2 extends Fragment {


    ViewPager viewPager;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view  =inflater.inflate(R.layout.fragment_neworder2, container, false);

    Button nextBtn = (Button) view.findViewById(R.id.next3p);
    Button prevBtn = (Button) view.findViewById(R.id.prev1p);
        viewPager = getActivity().findViewById(R.id.pager);


        spinner = (Spinner) view.findViewById(R.id.freightSpinner);
        final ArrayList<String> spinnerList = new ArrayList<>();

        spinnerList.add("즐겨찾기");
        spinnerList.add("난곡동");
        spinnerList.add("신대방동");
        spinnerList.add("서초동");

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

        //스피너용 어댑터
        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(spinnerAdapter);

        //스피너 이벤트리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "선택된 아이템 : " + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }
}
