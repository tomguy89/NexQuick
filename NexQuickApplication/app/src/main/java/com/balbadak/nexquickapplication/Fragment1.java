package com.balbadak.nexquickapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.annotation.SuppressLint;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


@SuppressLint("ValidFragment")
public class Fragment1 extends Fragment {

    private String TAG = "PickerActivity";
    private View view;
    private EditText etDatePicker;
    private EditText etTimePicker;
    private Spinner spinner;
    private LinearLayout reserveView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_neworder1, container, false);


        Button nextBtn = (Button) view.findViewById(R.id.next2p);

        etDatePicker = (EditText) view.findViewById(R.id.date_picker);
        etTimePicker = (EditText) view.findViewById(R.id.time_picker);
        etDatePicker.setInputType(0);
        etTimePicker.setInputType(0);

        spinner = (Spinner) view.findViewById(R.id.senderAddressSpinner);

        final ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("즐겨찾기");
        spinnerList.add("봉천동");
        spinnerList.add("역삼동");
        spinnerList.add("신림동");

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


        CheckBox reserveCbx = (CheckBox) view.findViewById(R.id.reserveCbx);

        reserveView = (LinearLayout) view.findViewById(R.id.reserveView);


        reserveCbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {

                    reserveView.setVisibility(View.VISIBLE);

                } else {

                    reserveView.setVisibility(View.INVISIBLE);
                }


            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.pager);
                viewPager.setCurrentItem(1);
            }
        });


        timeInit();
        return view;


    }


    private void timeInit() {

        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
        final Calendar cal = Calendar.getInstance();

        Log.e(TAG, cal.get(Calendar.YEAR) + "");
        Log.e(TAG, cal.get(Calendar.MONTH) + 1 + "");
        Log.e(TAG, cal.get(Calendar.DATE) + "");
        Log.e(TAG, cal.get(Calendar.HOUR_OF_DAY) + "");
        Log.e(TAG, cal.get(Calendar.MINUTE) + "");


        //DATE PICKER DIALOG
        view.findViewById(R.id.date_picker).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {


                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                            String msg = String.format("%d 년 %d 월 %d 일", year, month + 1, date);
                            etDatePicker.setText(msg);
                        }
                    }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                    dialog.getDatePicker().setMinDate(new Date().getTime());    //입력한 날짜 이전으로 클릭 안되게 옵션
                    dialog.show();
                }
            }
        });




        //TIME PICKER DIALOG
        view.findViewById(R.id.time_picker).setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus) {
                    TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int min) {

                            String msg = String.format("%d 시 %d 분", hour, min);
                            etTimePicker.setText(msg);
                        }
                    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                    dialog.show();
                }

            }
        });
    }


}
