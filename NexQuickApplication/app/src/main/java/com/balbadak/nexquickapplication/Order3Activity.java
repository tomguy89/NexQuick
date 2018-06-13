package com.balbadak.nexquickapplication;

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
import android.widget.Button;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;


public class Order3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context = this;
    private SharedPreferences quickInfo;

    //.173 태진, .164 승진
    private String mainUrl = "http://70.12.109.164:9090/NexQuick/";
    private String payUrl;
    private int totalPrice;
    private ContentValues values;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder3);
        quickInfo = getSharedPreferences("quickInfo", 0);
        totalPrice = quickInfo.getInt("totalPrice", 0);

        Button prevBtn = (Button) findViewById(R.id.prev2p);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Order2Activity.class);
                startActivity(intent);
            }
        });


        payUrl = mainUrl + "appCall/registCall.do";
        values = new ContentValues();

        Button payAppCard = (Button) findViewById(R.id.payAppCard);
        Button payAppDeposit = (Button) findViewById(R.id.payAppDeposit);
        Button paySenderCard = (Button) findViewById(R.id.paySenderCard);
        Button paySenderMoney = (Button) findViewById(R.id.paySenderMoney);
        Button payReceiverCard = (Button) findViewById(R.id.payReceiverCard);
        Button payReceiverMoney = (Button) findViewById(R.id.payReceiverMoney);
        Button payCredit = (Button) findViewById(R.id.payCredit);


        payAppCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DialogPayActivity.class);
                startActivityForResult(intent, 2000);
            }
        });

        payAppDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DialogPayActivity.class);
                startActivityForResult(intent, 2020);
            }
        });

        paySenderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put("deliveryStatus", 1);
                values.put("payType", 2);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

        paySenderMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put("deliveryStatus", 1);
                values.put("payType", 3);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

        payReceiverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put("deliveryStatus", 1);
                values.put("payType", 4);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

        payReceiverMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put("deliveryStatus", 1);
                values.put("payType", 5);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

        payCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put("deliveryStatus", 1);
                values.put("payType", 6);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {

            case 2000:
                if (resultCode == RESULT_OK) {
                    values.put("deliveryStatus", 1);
                    values.put("payType", 0);
                    values.put("payStatus", 1);
                    values.put("totalPrice", totalPrice);
                    Intent i = new Intent(context, OrderCompleteActivity.class);
                    startActivity(i);
                }
                break;
            case 2020 :
                if (resultCode == RESULT_OK) {
                    values.put("deliveryStatus", 1);
                    values.put("payType", 1);
                    values.put("payStatus", 1); // 일단 실시간계좌이체라고 설정
                    values.put("totalPrice", totalPrice);
                    Intent i = new Intent(context, OrderCompleteActivity.class);
                    startActivity(i);
                }
                break;
        }
    }


    //새로운 콜을 보내는 태스크
    public class MainTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public MainTask(String url, ContentValues values) {

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
            Intent intent = new Intent(getApplicationContext(), Order3Activity.class);
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
