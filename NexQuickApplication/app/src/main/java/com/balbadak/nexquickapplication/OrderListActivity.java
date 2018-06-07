package com.balbadak.nexquickapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context = this;
    private SharedPreferences loginInfo;
    private ListView listView;
<<<<<<< HEAD
    private TextView titletextView;
=======
>>>>>>> 900b8c93aa53af6ac6117b662602650a1b10fe6a
    private String csId;
    private String csName;
    private int callNum;
    private int deliveryStatus;

    ArrayList<ListViewItem> dateList;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        dateList = new ArrayList<>();
<<<<<<< HEAD

        titletextView = (TextView) findViewById(R.id.order_list_Title);
=======
>>>>>>> 900b8c93aa53af6ac6117b662602650a1b10fe6a

        // Adding Toolbar to the activity
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

        listView = (ListView) this.findViewById(R.id.order_listview);
        loginInfo = getSharedPreferences("setting", 0);

        if (loginInfo != null && loginInfo.getString("csId", "") != null && loginInfo.getString("csId", "").length() != 0) {
            csId = loginInfo.getString("csId", "");
            csName = loginInfo.getString("csName", "");
        }

        String temp = csName + "님의 최근 주문 내역";
        titletextView.setText(temp);

        String url = "http://70.12.109.173:9090/NexQuick/list/userAllCallLists.do";

        ContentValues values = new ContentValues();
        values.put("csId", csId);
        // AsyncTask를 통해 HttpURLConnection 수행.
        GetListTask getListTask = new GetListTask(url, values);
        getListTask.execute();


        Button orderListBeforeBtn = (Button) findViewById(R.id.orderListBeforeBtn);

        orderListBeforeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderListBeforeActivity.class);
                startActivity(intent);
            }
        });


    }


    private class CustomAdapter extends ArrayAdapter<ListViewItem> {
        private ArrayList<ListViewItem> data;
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<ListViewItem> object) {
            super(context, textViewResourceId, object);
            this.data = object;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.order_list_item, null);
            }

            TextView titleStrView = (TextView) v.findViewById(R.id.order_list_item_date);
            TextView descStrView = (TextView) v.findViewById(R.id.order_list_item_detail);
            Button detailBtn = (Button) v.findViewById(R.id.detailBtn);

<<<<<<< HEAD
=======

            titleStrView.setText(data.get(position).getTitleStr());
            descStrView.setText(data.get(position).getDescStr());

            callNum = data.get(position).getCallNum();
            deliveryStatus = data.get(position).getDeliveryStatus();
            Log.i("callNum", callNum+"");

            detailBtn.setOnClickListener(new View.OnClickListener() {
                int cn = callNum;
                int ds = deliveryStatus;
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DialogDetailActivity.class);
                    intent.putExtra("callNum", cn);
                    intent.putExtra("deliveryStatus", ds);
                    startActivity(intent);
                }
            });
>>>>>>> 900b8c93aa53af6ac6117b662602650a1b10fe6a

            titleStrView.setText(data.get(position).getTitleStr());
            descStrView.setText(data.get(position).getDescStr());

            callNum = data.get(position).getCallNum();
            deliveryStatus = data.get(position).getDeliveryStatus();
            Log.i("callNum", callNum+"");

<<<<<<< HEAD
            detailBtn.setOnClickListener(new View.OnClickListener() {
                int cn = callNum;
                int ds = deliveryStatus;
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DialogDetailActivity.class);
                    intent.putExtra("callNum", cn);
                    intent.putExtra("deliveryStatus", ds);
                    startActivity(intent);
                }
            });

            return v;
        }


    }

    // 여기부터 마이메뉴 네비게이션 영역
=======
    }


>>>>>>> 900b8c93aa53af6ac6117b662602650a1b10fe6a
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
            Intent intent = new Intent(getApplicationContext(), NewOrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order_list) {
            Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // 여기까지 마이메뉴 영역


    // 여기부터 AsyncTask 영역
    public class GetListTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public GetListTask(String url, ContentValues values) {

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
            StringBuilder titleSb = new StringBuilder();
            StringBuilder descSb = new StringBuilder();
            super.onPostExecute(s);
<<<<<<< HEAD

            if (s != null) {
                Log.e("받아온 것", s);
=======
            Log.e("받아온 것", s);
            if (s != null) {
>>>>>>> 900b8c93aa53af6ac6117b662602650a1b10fe6a
                try {
                    JSONArray ja = new JSONArray(s);
                    JSONObject data;
                    for (int i = 0; i < ja.length(); i++) {
                        data = ja.getJSONObject(i);

                        ListViewItem item = new ListViewItem();
                        titleSb.setLength(0);
                        descSb.setLength(0);
                        if (data.getInt("urgent") == 1) {
                            descSb.append("급/");
                        }

                        titleSb.append(data.getString("callTime"));
                        descSb.append("수령인 : ");
                        descSb.append(data.getString("receiverName"));
                        descSb.append(" 수령지 : ");
                        descSb.append(data.getString("receiverAddress"));
                        descSb.append(" 가격 : ");
                        descSb.append(data.getString("orderPrice"));

                        item.setTitleStr(titleSb.toString());
                        item.setDescStr(descSb.toString());
                        item.setCallNum(data.getInt("callNum"));
                        item.setDeliveryStatus(data.getInt("deliveryStatus"));

                        dateList.add(item);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (csName != null) {
                    CustomAdapter adapter = new CustomAdapter(context, 0, dateList);
                    listView.setAdapter(adapter);
                } else {
<<<<<<< HEAD


=======
>>>>>>> 900b8c93aa53af6ac6117b662602650a1b10fe6a
                }
            } else {

                titletextView.setText("이전 주문 내역이 없습니다");

            }
        }
    }
}
