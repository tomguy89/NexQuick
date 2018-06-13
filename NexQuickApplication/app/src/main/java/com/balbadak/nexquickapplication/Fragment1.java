package com.balbadak.nexquickapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    ViewPager viewPager;
    private Bundle bundle; // 프래그먼트간 전달

    private SharedPreferences loginInfo;
    private String csId;
    private String csName;

    private EditText etSenderName;
    private EditText etSenderPhone;
    private EditText etSenderAddress;
    private EditText etSenderAddressDetail;

    private CheckBox cbxSeries; // 아직 미구현
    private CheckBox cbxUrgent;
    private CheckBox cbxReserve;

    private LinearLayout reserveView;

    private EditText etDatePicker;
    private EditText etTimePicker;

    private Spinner spinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_neworder1, container, false);
        viewPager = getActivity().findViewById(R.id.pager);

        Button nextBtn = (Button) view.findViewById(R.id.next2p);

        loginInfo = getActivity().getSharedPreferences("setting", 0);
        if (loginInfo != null && loginInfo.getString("csId", "") != null && loginInfo.getString("csId", "").length() != 0) {
            csId = loginInfo.getString("csId", "");
            csName = loginInfo.getString("csName", "");
        }

        if (bundle == null) {
            bundle = new Bundle();
            Log.i("프래그먼트1", "번들없다");
        } else {
            bundle = getArguments();
            Log.i("프래그먼트1", "번들있다");
        }


        spinner = (Spinner) view.findViewById(R.id.senderAddressSpinner);

        etSenderName = (EditText) view.findViewById(R.id.senderName);
        etSenderPhone = (EditText) view.findViewById(R.id.senderPhone);
        etSenderAddress = (EditText) view.findViewById(R.id.senderAddress);
        etSenderAddressDetail = (EditText) view.findViewById(R.id.senderAddressDetail);

        etDatePicker = (EditText) view.findViewById(R.id.date_picker);
        etTimePicker = (EditText) view.findViewById(R.id.time_picker);
        etDatePicker.setInputType(0);
        etTimePicker.setInputType(0);


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

        cbxUrgent = (CheckBox) view.findViewById(R.id.urgentCbx);
        cbxReserve = (CheckBox) view.findViewById(R.id.reserveCbx);
        reserveView = (LinearLayout) view.findViewById(R.id.reserveView);


        cbxReserve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    reserveView.setVisibility(View.VISIBLE);
                } else {
                    reserveView.setVisibility(View.INVISIBLE);
                }


            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(callInfoResult(bundle)) {
                    setArguments(bundle);
                    viewPager.setCurrentItem(1);
                } else {
                    Toast.makeText(getActivity(), "빈칸을 모두 입력하세요", Toast.LENGTH_SHORT).show();
                }
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

                if (hasFocus) {
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

                if (hasFocus) {
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

    // 프래그먼트간 내용 전달
    private boolean callInfoResult(Bundle bundle) {

        if (csId != null) {
            bundle.putString("csId", csId);
        } else {
            return false;
        }

        if (etSenderName != null && etSenderName.getText().toString() != "") {
            bundle.putString("senderName", etSenderName.getText().toString());
            Log.i("senderName", etSenderName.toString().trim() + 111);
        } else {
            return false;
        }

        if (etSenderAddress != null && etSenderAddress.toString().trim().length() != 0) {
            bundle.putString("senderAddress", etSenderAddress.toString().trim());
            Log.i("senderAddress", etSenderAddress.toString().trim()+222);
        } else {
            return false;
        }

        if (etSenderAddressDetail != null && etSenderAddressDetail.toString().trim().length() != 0) {
            bundle.putString("senderAddressDetail", etSenderAddressDetail.toString().trim());
        } else {
            return false;
        }
        if (etSenderPhone != null && etSenderPhone.toString().trim().length() != 0) {
            bundle.putString("senderPhone", etSenderPhone.toString().trim());
        } else {
            return false;
        }

        int temp = 0;
        bundle.putInt("urgent", temp =(cbxUrgent.isChecked())? 1:0); // checked 1, unchecked 2
        bundle.putInt("reserved", temp =(cbxReserve.isChecked())? 1:0); // checked 1, unchecked 2
        if(temp == 1) {
            String date = etDatePicker + " " + etTimePicker;
            bundle.putString("reservationTime",date );
        }
        return true;

    }


}
