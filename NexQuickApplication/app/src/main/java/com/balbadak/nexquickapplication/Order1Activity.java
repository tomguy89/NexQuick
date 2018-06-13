package com.balbadak.nexquickapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class Order1Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private String TAG = "PickerActivity";

    private Context context = this;
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

    private RadioGroup vehicleType;

    private LinearLayout reserveView;

    private EditText etDatePicker;
    private EditText etTimePicker;

    private Spinner spinner;
    private ContentValues values;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder1);


        Button nextBtn = (Button) findViewById(R.id.next2p);

        loginInfo = getSharedPreferences("setting", 0);
        if (loginInfo != null && loginInfo.getString("csId", "") != null && loginInfo.getString("csId", "").length() != 0) {
            csId = loginInfo.getString("csId", "");
            csName = loginInfo.getString("csName", "");

        }

        spinner = (Spinner) findViewById(R.id.senderAddressSpinner);

        etSenderName = (EditText) findViewById(R.id.senderName);
        etSenderPhone = (EditText) findViewById(R.id.senderPhone);
        etSenderAddress = (EditText) findViewById(R.id.senderAddress);
        etSenderAddressDetail = (EditText) findViewById(R.id.senderAddressDetail);

        etDatePicker = (EditText) findViewById(R.id.date_picker);
        etTimePicker = (EditText) findViewById(R.id.time_picker);
        etDatePicker.setInputType(0);
        etTimePicker.setInputType(0);

        timeInit();

        vehicleType = (RadioGroup) findViewById(R.id.vehicleType);

        final ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("즐겨찾기");
        spinnerList.add("봉천동");
        spinnerList.add("역삼동");
        spinnerList.add("신림동");

        //스피너용 어댑터
        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(spinnerAdapter);

        //스피너 이벤트리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "선택된 아이템 : " + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbxUrgent = (CheckBox) findViewById(R.id.urgentCbx);
        cbxReserve = (CheckBox) findViewById(R.id.reserveCbx);
        reserveView = (LinearLayout) findViewById(R.id.reserveView);


        cbxReserve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    reserveView.setVisibility(View.VISIBLE);
                } else {
                    reserveView.setVisibility(View.GONE);
                }


            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                // URL 설정. 173 태진햄 / 164 승진
                String url = "http://70.12.109.164:9090/NexQuick/appCall/newCall.do";
                values = new ContentValues();
                boolean result = setCallInfo(values);

                if(result) {

                    SetCallTask setCallTask = new SetCallTask(url, values);
                    setCallTask.execute();
                    Intent intent = new Intent(context, Order2Activity.class);
                    startActivity(intent);

                } else {
                    values.clear();
                    Log.e("메소드 빈칸", "메소드에 빈칸있쑝");
                }


            }
        });

        // 내비게이션 서랍을 위한 툴바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 내비게이션 서랍 관련 설정
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        findViewById(R.id.date_picker).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {


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
        findViewById(R.id.time_picker).setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
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
    private boolean setCallInfo(ContentValues values) {

        if (csId != null) {
            values.put("csId", csId);
        } else {
            return false;
        }

        if (etSenderName != null && etSenderName.getText().toString().trim().length() != 0) {
            values.put("senderName", etSenderName.getText().toString());
            Log.i("senderName", etSenderName.getText().toString().trim() + 111);

        } else {
            return false;
        }

        if (etSenderAddress != null && etSenderAddress.getText().toString().trim().length() != 0) {
            values.put("senderAddress", etSenderAddress.getText().toString().trim());
            Log.i("senderAddress", etSenderAddress.getText().toString().trim());
        } else {
            return false;
        }

        if (etSenderAddressDetail != null && etSenderAddressDetail.getText().toString().trim().length() != 0) {
            values.put("senderAddressDetail", etSenderAddressDetail.getText().toString().trim());
            Log.i("senderAddressDetail", etSenderAddressDetail.getText().toString().trim());

        } else {
            return false;
        }
        if (etSenderPhone != null && etSenderPhone.getText().toString().trim().length() != 0) {
            values.put("senderPhone", etSenderPhone.getText().toString().trim());
            Log.i("senderPhone", etSenderPhone.getText().toString().trim());
        } else {
            return false;
        }

        int id = vehicleType.getCheckedRadioButtonId();

        RadioButton rb = (RadioButton) findViewById(id);

        if (rb != null) {

            switch (rb.getText().toString()) {
                case "오토바이":
                    values.put("vehicleType", 1);
                    break;
                case "다마스":
                    values.put("vehicleType", 2);
                    break;
                case "라보":
                    values.put("vehicleType", 3);
                    break;
                case "트럭":
                    values.put("vehicleType", 4);
                    break;
                default:
            }
        } else {
            return false;
        }


        int temp;
        values.put("urgent", (cbxUrgent.isChecked()) ? 1 : 0); // checked 1, unchecked 2
        values.put("reserved", temp = (cbxReserve.isChecked()) ? 1 : 0); // checked 1, unchecked 2
        values.put("series", 0); // 일괄 배송 처리 안함
        if (temp == 1) {
            String date = etDatePicker.getText().toString() + " " + etTimePicker.getText().toString();
            Log.i("reservationTime", date);
            values.put("reservationTime", date);
        }

        return true;

    }


    // ----------------------여기부터 AsyncTask 영역
    public class SetCallTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public SetCallTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

        Toast.makeText(context,"퀵 발송 정보가 저장 되었음",Toast.LENGTH_SHORT ).show();

            if (s != null) {
                Log.e("받아온 것", s);
                try {
                    JSONArray ja = new JSONArray(s);
                    JSONObject data;
                    for (int i = 0; i < ja.length(); i++) {
                        data = ja.getJSONObject(i);
                        int callNum = Integer.parseInt(data.getString("callNum"));

                        SharedPreferences.Editor ed = loginInfo.edit();

                        ed.putInt("callNum", callNum);
                        ed.commit();
                        Log.e("callNum", callNum+"!");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("콜넘버", "없음");

            }

        }
    }



    //------------------------------여기부터 내비 영역 -----------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_order) {
            Intent intent = new Intent(getApplicationContext(), Order1Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order_list) {
            Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}