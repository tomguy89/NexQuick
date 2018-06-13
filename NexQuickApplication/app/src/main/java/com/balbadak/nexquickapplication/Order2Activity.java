package com.balbadak.nexquickapplication;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.balbadak.nexquickapplication.vo.FavoriteInfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Order2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context = this;
    private SharedPreferences loginInfo;

    //.173 태진, .164 승진
    private String mainUrl = "http://70.12.109.164:9090/NexQuick/";
    private String csId;
    private String freight;
    private int callNum;
    private int orderNum;

    private ArrayList<FavoriteInfo> favoriteInfos;
    private ArrayAdapter favspinnerAdapter;
    private ArrayAdapter frspinnerAdapter;

    private Spinner favSpinner;
    private Spinner frSpinner;
    private Button addressBtn;

    private EditText etReceiverName;
    private EditText etReceiverPhone;
    private EditText etReceiverAddress;
    private EditText etReceiverAddressDetail;
    private EditText etMemo;

    private ContentValues oValues;
    private ContentValues fValues;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder2);

        loginInfo = getSharedPreferences("setting", 0);
        csId = loginInfo.getString("csId", "");
        callNum = loginInfo.getInt("callNum", callNum);

        Log.e("callNum", callNum + "");

        favSpinner = (Spinner) findViewById(R.id.receiverAddressSpinner);
        etReceiverName = (EditText) findViewById(R.id.receiverName);
        etReceiverPhone = (EditText) findViewById(R.id.receiverPhone);
        etReceiverAddress = (EditText) findViewById(R.id.receiverAddress);
        etReceiverAddressDetail = (EditText) findViewById(R.id.receiverAddressDetail);
        etMemo = (EditText) findViewById(R.id.memo);

        favoriteInit();
        addressBtn = (Button) findViewById(R.id.receiverAddressBtn);

        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Order2Activity.this, DialogAddressActivity.class);
                startActivityForResult(i, 1011);
            }
        });


        frSpinner = (Spinner) findViewById(R.id.freightSpinner);

        final ArrayList<String> frSpinnerList = new ArrayList<>();
        frSpinnerList.add("물품정보");
        frSpinnerList.add("서류");
        frSpinnerList.add("박스 소");
        frSpinnerList.add("박스 중");
        frSpinnerList.add("박스 대");

        //스피너용 어댑터
        ArrayAdapter spinnerAdapter2;
        spinnerAdapter2 = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, frSpinnerList);
        frSpinner.setAdapter(spinnerAdapter2);

        //스피너 이벤트리스너
        frSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                freight = frSpinner.getItemAtPosition(position) + "";
                Toast.makeText(context, "선택된 아이템 : " + frSpinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button nextBtn = (Button) findViewById(R.id.next3p);
        Button prevBtn = (Button) findViewById(R.id.prev1p);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //173 태진햄, 164 승진
                String url = mainUrl + "appCall/addOrder.do";
                oValues = new ContentValues();
                fValues = new ContentValues();
                boolean orderResult = setOrderInfo(oValues);


                if (orderResult) {

                    oValues.put("callNum", callNum);
                    SetOrderTask setOrderTask = new SetOrderTask(url, oValues);
                    setOrderTask.execute();

                } else {
                    oValues.clear();
                    Log.e("메소드 빈칸", "메소드에 빈칸있쑝");
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Order1Activity.class);
                startActivity(intent);
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

        //스피너용 어댑터
        favoriteInfos = new ArrayList<>();
        favoriteInfos.add(0, new FavoriteInfo(-1, "", 0, "주소를 선택하세요", "", "즐겨찾기", ""));

        String url = mainUrl + "appCall/getFavorite.do";

        ContentValues values = new ContentValues();
        values.put("csId", csId);

        GetFavorite getFavorite = new GetFavorite(url, values);
        getFavorite.execute();

    }

    private void setFavoriteInfos() {

        favspinnerAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, favoriteInfos);
        favSpinner.setAdapter(favspinnerAdapter);

        //스피너 이벤트리스너
        favSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FavoriteInfo fi = (FavoriteInfo) favSpinner.getItemAtPosition(position);

                if (fi.getFavoriteId() != -1) {
                    etReceiverName.setText(fi.getReceiverName());
                    etReceiverPhone.setText(fi.getReceiverPhone());
                    etReceiverAddress.setText(fi.getAddress());
                    etReceiverAddressDetail.setText(fi.getAddrDetail());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private boolean setOrderInfo(ContentValues values) {

        if (etReceiverName != null && etReceiverName.getText().toString().trim().length() != 0) {
            values.put("receiverName", etReceiverName.getText().toString().trim());
            Log.i("receiverName", etReceiverName.getText().toString().trim() + 111);

        } else {
            return false;
        }

        if (etReceiverAddress != null && etReceiverAddress.getText().toString().trim().length() != 0) {
            values.put("receiverAddress", etReceiverAddress.getText().toString().trim());
            Log.i("receiverAddress", etReceiverAddress.getText().toString().trim());
        } else {
            return false;
        }

        if (etReceiverAddressDetail != null && etReceiverAddressDetail.getText().toString().trim().length() != 0) {
            values.put("receiverAddressDetail", etReceiverAddressDetail.getText().toString().trim());
            Log.i("receiverAddressDetail", etReceiverAddressDetail.getText().toString().trim());

        } else {
            return false;
        }
        if (etReceiverPhone != null && etReceiverPhone.getText().toString().trim().length() != 0) {
            values.put("receiverPhone", etReceiverPhone.getText().toString().trim());
            Log.i("receiverPhone", etReceiverPhone.getText().toString().trim());
        } else {
            return false;
        }

        if (etMemo != null && etMemo.getText().toString().trim().length() != 0) {
            values.put("memo", etMemo.getText().toString().trim());
            Log.i("memo", etMemo.getText().toString().trim());
        } else {
            values.put("memo", "없음");
            Log.i("memo", "없음");
        }

        return true;
    }

    private boolean setFreightInfo(ContentValues values) {

        values.put("freightDetail", "없음");
        if (frSpinner != null) {
            switch (freight) {  // 일단 화물 종류 복수 선택 안되고,  화물 1개 고정
                case "서류":
                    values.put("freightType", 1);
                    values.put("freightQuant", 1);
                    break;
                case "박스 소":
                    values.put("freightType", 2);
                    values.put("freightQuant", 1);
                    break;
                case "박스 중":
                    values.put("freightType", 3);
                    values.put("freightQuant", 1);
                    break;
                case "박스 대":
                    values.put("freightType", 4);
                    values.put("freightQuant", 1);
                    break;
                default:
                    return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {

            case 1011:

                if (resultCode == RESULT_OK) {

                    String data = intent.getExtras().getString("data").substring(7);
                    if (data != null)
                        etReceiverAddress.setText(data);
                }
                break;

        }

    }


    // 여기부터 AsyncTask 영역
    public class SetOrderTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public SetOrderTask(String url, ContentValues values) {

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
            if (s != null) {
                Log.e("SetOrderTask 받아온 것", s);
                JSONObject data = null;
                try {
                    data = new JSONObject(s);
                    orderNum = data.getInt("orderNum");
                    SharedPreferences.Editor ed = loginInfo.edit();
                    ed.putInt("orderNum", orderNum);
                    ed.commit();
                    Log.e("orderNum", orderNum + "!");

                    boolean freightResult = setFreightInfo(fValues);
                    if (freightResult) {
                        url = mainUrl + "appCall/addFreight.do";
                        fValues.put("callNum", callNum);
                        fValues.put("orderNum", orderNum);
                        SetFreightTask setFreightTask = new SetFreightTask(url, fValues);
                        setFreightTask.execute();
                        Intent intent = new Intent(context, Order3Activity.class);
                        startActivity(intent);
                    } else {
                        Log.e("화물 미선택", "화물정보가 없쑝");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                Toast.makeText(context, "SetOrderTask", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class SetFreightTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public SetFreightTask(String url, ContentValues values) {

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
            if (s != null) {
                Log.e("SetFreightTask 받아온 것", s);
            }
            Toast.makeText(context, "SetFreightTask", Toast.LENGTH_SHORT).show();
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
                        if (data.getInt("addressType") != 0) {

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
