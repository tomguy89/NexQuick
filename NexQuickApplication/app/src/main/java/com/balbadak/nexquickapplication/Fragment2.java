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
    Spinner reSpinner;
    Spinner frSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view  =inflater.inflate(R.layout.fragment_neworder2, container, false);


        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);

        reSpinner = (Spinner) view.findViewById(R.id.receiverAddressSpinner);

        final ArrayList<String> reSpinnerList = new ArrayList<>();

        reSpinnerList.add("즐겨찾기");
        reSpinnerList.add("난곡동");
        reSpinnerList.add("신대방동");
        reSpinnerList.add("서초동");


        //스피너용 어댑터
        ArrayAdapter spinnerAdapter1;
        spinnerAdapter1 = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, reSpinnerList);
        reSpinner.setAdapter(spinnerAdapter1);

        //스피너 이벤트리스너
        reSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "선택된 아이템 : " + reSpinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        frSpinner = (Spinner) view.findViewById(R.id.freightSpinner);

        final ArrayList<String> frSpinnerList = new ArrayList<>();
        frSpinnerList.add("물품정보");
        frSpinnerList.add("서류");
        frSpinnerList.add("박스 소");
        frSpinnerList.add("박스 중");
        frSpinnerList.add("박스 대");

        //스피너용 어댑터
        ArrayAdapter spinnerAdapter2;
        spinnerAdapter2 = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, frSpinnerList);
        frSpinner.setAdapter(spinnerAdapter2);

        //스피너 이벤트리스너
        frSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "선택된 아이템 : " + frSpinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button nextBtn = (Button) view.findViewById(R.id.next3p);
        Button prevBtn = (Button) view.findViewById(R.id.prev1p);


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
