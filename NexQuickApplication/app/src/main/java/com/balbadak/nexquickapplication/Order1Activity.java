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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

import com.balbadak.nexquickapplication.vo.FavoriteInfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Order1Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String mainUrl;

    private String TAG = "PickerActivity";

    private Context context = this;
    private SharedPreferences loginInfo;
    private String csId;
    private String csName;
    private String reserveTime;
    private int callNum;

    private EditText etSenderName;
    private EditText etSenderPhone;
    private EditText etSenderAddress;
    private EditText etSenderAddressDetail;

    private CheckBox cbxSeries; // 아직 미구현
    private CheckBox cbxUrgent;
    private CheckBox cbxReserve;
    private Button addressBtn;

    private RadioGroup vehicleType;

    private String dateStr;
    private String timeStr;

    private LinearLayout reserveView;

    private EditText etDatePicker;
    private EditText etTimePicker;

    private Spinner spinner;
    private ContentValues values;

    private ArrayList<FavoriteInfo> favoriteInfos;
    ArrayAdapter spinnerAdapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder1);
        mainUrl = getResources().getString(R.string.main_url);


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

        favoriteInit();

        addressBtn = (Button) findViewById(R.id.senderAddressBtn);
        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Order1Activity.this, DialogAddressActivity.class);
                startActivityForResult(i, 1010);
            }
        });

        vehicleType = (RadioGroup) findViewById(R.id.vehicleType);

        cbxUrgent = (CheckBox) findViewById(R.id.urgentCbx);
        cbxReserve = (CheckBox) findViewById(R.id.reserveCbx);
        cbxSeries = (CheckBox) findViewById(R.id.seriesCbx);
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
                String url = mainUrl + "appCall/newCall.do";
                values = new ContentValues();
                boolean result = setCallInfo(values);

                if (result) {

                    SetCallTask setCallTask = new SetCallTask(url, values);
                    setCallTask.execute();
                    Intent intent = new Intent(context, Order2Activity.class);
                    intent.putExtra("series",(cbxSeries.isChecked())? 1:0);
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

    private void favoriteInit() {

        //스피너 어댑터
        favoriteInfos = new ArrayList<>();
        favoriteInfos.add(0, new FavoriteInfo(-1, "", 0, "주소를 선택하세요", "", "즐겨찾기", ""));
        String url = mainUrl+"appCall/getFavorite.do";

        values = new ContentValues();
        values.put("csId", csId);

        GetFavorite getFavorite = new GetFavorite(url, values);
        getFavorite.execute();

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
                            dateStr =String.format("%02d/%02d/%4d", month + 1, date, year);


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
                            timeStr = String.format("%d:%d", hour, min);

                        }
                    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                    dialog.show();
                }

            }
        });
    }

    private void setFavoriteInfos() {

        Log.i("즐겨찾기 세팅 in 태스크", favoriteInfos.toString());

        FavoriteInfo nullInfo;

        spinnerAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, favoriteInfos);
        spinner.setAdapter(spinnerAdapter);


        //스피너 이벤트리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FavoriteInfo fi = (FavoriteInfo) spinner.getItemAtPosition(position);

                if(fi.getFavoriteId() != -1) {
                    etSenderName.setText(fi.getReceiverName());
                    etSenderPhone.setText(fi.getReceiverPhone());
                    etSenderAddress.setText(fi.getAddress());
                    etSenderAddressDetail.setText(fi.getAddrDetail());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

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


        int tempR, tempS;
        values.put("urgent", (cbxUrgent.isChecked()) ? 1 : 0); // checked 1, unchecked 2
        values.put("reserved", tempR = (cbxReserve.isChecked()) ? 1 : 0); // checked 1, unchecked 2
        values.put("series",tempS = (cbxSeries.isChecked()) ? 1 : 0 );
        if (tempR == 1) {

            String reservationTime = dateStr + " " + timeStr;
            Log.i("reservationTime", reservationTime);
            values.put("reservationTime", reservationTime);
        }



        return true;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode){

            case 1010:

                if(resultCode == RESULT_OK){

                    String data = intent.getExtras().getString("data").substring(7);
                    if (data != null)
                        etSenderAddress.setText(data);
                }
                break;

        }

    }


    // ----------------------여기부터 AsyncTask 영역 -------------------------------

    //새로운 콜을 보내는 태스크
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

            Toast.makeText(context, "퀵 발송 정보가 저장 되었음", Toast.LENGTH_SHORT).show();

            if (s != null) {
                Log.e("퀵 발송 정보 받아온 것", s);
                try {
                    JSONObject data = new JSONObject(s);
                        int callNum = data.getInt("callNum");
                        SharedPreferences.Editor ed = loginInfo.edit();
                        ed.putInt("callNum", callNum);
                        ed.putInt("totalPrice", 0);
                        ed.commit();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("콜넘버", "없음");

            }

        }
    }

    //즐겨찾기를 불러오는 태스크
    public class GetFavorite extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public GetFavorite(String url, ContentValues values) {

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

            Toast.makeText(context, "즐겨찾기를 부른다", Toast.LENGTH_SHORT).show();

            if (s != null) {
                Log.e("받아온 것", s);
                try {
                    JSONArray ja = new JSONArray(s);
                    JSONObject data;
                    for (int i = 0; i < ja.length(); i++) {
                        data = ja.getJSONObject(i);
                        if (data.getInt("addressType") != 0) { // 추후에 배송지 타입 바뀔예정

                            FavoriteInfo fi = new FavoriteInfo();
                            fi.setFavoriteId(data.getInt("favoriteId"));
                            fi.setAddressType(data.getInt("addressType"));
                            fi.setCsId(data.getString("csId"));
                            fi.setAddress(data.getString("address"));
                            fi.setAddrDetail(data.getString("addrDetail"));
                            fi.setReceiverName(data.getString("receiverName"));
                            fi.setReceiverPhone(data.getString("receiverPhone"));
                            favoriteInfos.add(fi);
                        }

                    }
                    setFavoriteInfos();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("콜넘버", "없음");

            }

        }
    }


    //미주문 콜을 불러오는 태스크
    public class SetCurrentCallTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public SetCurrentCallTask(String url, ContentValues values) {

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

            Toast.makeText(context, "퀵 발송 정보가 저장 되었음", Toast.LENGTH_SHORT).show();

            if (s != null) {
                Log.e("받아온 것", s);
                try {
                    JSONArray ja = new JSONArray(s);
                    JSONObject data;
                    for (int i = 0; i < ja.length(); i++) {
                        data = ja.getJSONObject(i);
                        callNum = data.getInt("callNum");
                        //셋텍스트 기기

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
        } else if(id == R.id.chatBot) {
            Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
            startActivity(intent);
        } else if(id == R.id.userUpdate) {
            Intent intent = new Intent(getApplicationContext(), UserInfoUpdateActivity.class);
            startActivity(intent);
        }else if(id == R.id.insuindo) {
            Intent intent = new Intent(getApplicationContext(), CSBeamActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout) {
            SharedPreferences.Editor editor = getSharedPreferences("setting", 0).edit();
            editor.clear().commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
