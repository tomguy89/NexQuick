package com.balbadak.nexquickapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;



public class Order2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context = this;

    private Spinner reSpinner;
    private Spinner frSpinner;

    private EditText etReceiverName;
    private EditText etReceiverPhone;
    private EditText etReceiverAddress;
    private EditText etReceiverAddressDetail;

    String freight;


    private ContentValues values;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder2);


        reSpinner = (Spinner) findViewById(R.id.receiverAddressSpinner);

        final ArrayList<String> reSpinnerList = new ArrayList<>();

        reSpinnerList.add("즐겨찾기");
        reSpinnerList.add("난곡동");
        reSpinnerList.add("신대방동");
        reSpinnerList.add("서초동");


        //스피너용 어댑터
        ArrayAdapter spinnerAdapter1;
        spinnerAdapter1 = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, reSpinnerList);
        reSpinner.setAdapter(spinnerAdapter1);

        //스피너 이벤트리스너
        reSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "선택된 아이템 : " + reSpinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etReceiverName = (EditText) findViewById(R.id.receiverName);
        etReceiverPhone = (EditText) findViewById(R.id.receiverPhone);
        etReceiverAddress = (EditText) findViewById(R.id.receiverAddress);
        etReceiverAddressDetail = (EditText) findViewById(R.id.receiverAddressDetail);

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

                String url = "http://70.12.109.173:9090/NexQuick/appCall/addOrder.do";
                values = new ContentValues();
                boolean orderResult = setOrderInfo(values);
                boolean freightResult = setFreightInfo(values);

                if(orderResult) {

                    SetOrderTask setOrderTask = new SetOrderTask(url, values);
                    setOrderTask.execute();

                    if(freightResult) {

                        SetFreightTask setFreightTask = new SetFreightTask(url, values);
                        setFreightTask.execute();
                        Intent intent = new Intent(context, Order3Activity.class);
                        startActivity(intent);
                    } else  {
                        Log.e("화물 미선택", "화물정보가 없쑝");
                    }

                } else {
                    values.clear();
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


        return true;
    }

    private boolean setFreightInfo(ContentValues values) {

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

            Toast.makeText(context,"오더 발송 정보가 저장 되었음",Toast.LENGTH_SHORT ).show();
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

            Toast.makeText(context,"화물 정보가 저장 되었음",Toast.LENGTH_SHORT ).show();
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
