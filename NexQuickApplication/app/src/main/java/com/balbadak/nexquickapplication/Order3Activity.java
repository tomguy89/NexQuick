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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

import com.balbadak.nexquickapplication.vo.CallInfo;
import com.balbadak.nexquickapplication.vo.ListViewItem;
import com.balbadak.nexquickapplication.vo.OnDelivery;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Order3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String mainUrl;

    private Context context = this;
    private SharedPreferences loginInfo;
    private SharedPreferences.Editor editor;

    private String payUrl;

    private int totalPrice;
    private int callNum;
    private int orderCount;
    private ContentValues values;
    private ListView orderListview;

    CallInfo callInfo;

    TextView tvTotalPrice;
    OnDelivery orderDetail;
    ArrayList<ListViewItem> dateList;
    ArrayList<OnDelivery> list;
    StringBuilder titleSb;
    StringBuilder descSb;
    Button payAppCard;
    Button payAppDeposit;
    Button paySenderCard;
    Button paySenderMoney;
    Button payReceiverCard;
    Button payReceiverMoney;
    Button payCredit;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainUrl = getResources().getString(R.string.main_url);
        setContentView(R.layout.activity_neworder3);
        loginInfo = getSharedPreferences("setting", 0);
        editor = loginInfo.edit();
        callNum = getIntent().getExtras().getInt("cn");
        dateList = new ArrayList<>();
        list = new ArrayList<>();
        titleSb = new StringBuilder();
        descSb = new StringBuilder();

        String url = mainUrl + "appCall/getCall.do";
        values = new ContentValues();
        values.put("callNum", callNum);
        GetCallInfo getCallInfo = new GetCallInfo(url, values);
        getCallInfo.execute();

        url = mainUrl + "appCall/getOrderListLast.do";

        GetListTask getListTask = new GetListTask(url, values);
        getListTask.execute();

        orderListview = findViewById(R.id.order_listview);
        tvTotalPrice = (TextView) findViewById(R.id.totalPrice);
        Button prevBtn = (Button) findViewById(R.id.prev2p);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        payUrl = mainUrl + "appCall/registCall.do";

        payAppCard = (Button) findViewById(R.id.payAppCard);
        payAppDeposit = (Button) findViewById(R.id.payAppDeposit);
        paySenderCard = (Button) findViewById(R.id.paySenderCard);
        paySenderMoney = (Button) findViewById(R.id.paySenderMoney);
        payReceiverCard = (Button) findViewById(R.id.payReceiverCard);
        payReceiverMoney = (Button) findViewById(R.id.payReceiverMoney);
        payCredit = (Button) findViewById(R.id.payCredit);


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

    @Override
    protected void onDestroy() {
        editor.putInt("totalPrice", 0);
        editor.commit();
        super.onDestroy();
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
            case 2020:
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

            titleStrView.setText(data.get(position).getTitleStr());
            descStrView.setText(data.get(position).getDescStr());


            if (position > 0) {
                titleStrView.setTextColor(getResources().getColor(R.color.colorTomato));
                detailBtn.setOnClickListener(new View.OnClickListener() {
                    OnDelivery orderInfo = list.get(position-1);

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DialogOrderCompleteDetailActivity.class);

                        intent.putExtra("name", orderInfo.getReceiverName());
                        intent.putExtra("phone", orderInfo.getReceiverPhone());
                        intent.putExtra("address", orderInfo.getReceiverAddress() + " " + orderInfo.getReceiverAddressDetail());

                        intent.putExtra("freights", orderInfo.getFreightList());
                        intent.putExtra("orderPrice", orderInfo.getOrderPrice());
                        intent.putExtra("memo", orderInfo.getMemo());
                        startActivity(intent);
                    }
                });
            } else {

                titleStrView.setTextColor(getResources().getColor(R.color.colorGold));
                detailBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DialogOrderCompleteDetailActivity.class);

                        intent.putExtra("name", callInfo.getSenderName());
                        intent.putExtra("phone", callInfo.getSenderPhone());
                        intent.putExtra("address", callInfo.getSenderAddress() + " " + callInfo.getSenderAddressDetail());
                        startActivity(intent);
                    }
                });
            }
            return v;
        }


    }


    // 여기부터 AsyncTask 영역
    public class GetCallInfo extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public GetCallInfo(String url, ContentValues values) {

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

            super.onPostExecute(s);

            if (s != null && s.toString().trim().length() != 0) {
                try {

                    JSONObject data = new JSONObject(s);

                    ListViewItem item;


                    item = new ListViewItem();
                    callInfo = new CallInfo();
                    titleSb.setLength(0);
                    descSb.setLength(0);

                    callInfo.setCallNum(data.getInt("callNum"));
                    callInfo.setSenderName(data.getString("senderName"));
                    callInfo.setSenderAddress(data.getString("senderAddress"));
                    callInfo.setSenderAddressDetail(data.getString("senderAddressDetail"));
                    callInfo.setSenderPhone(data.getString("senderPhone"));

                    titleSb.append("발송정보 ");
                    descSb.append("   발송인   ");
                    descSb.append(callInfo.getSenderName()).append("\n");
                    descSb.append("   발송지   ");
                    descSb.append(callInfo.getSenderAddress());

                    item.setTitleStr(titleSb.toString());
                    item.setDescStr(descSb.toString());
                    item.setCallNum(callInfo.getCallNum());
                    dateList.add(item);
                    // list.add(order);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {


            }
        }
    }


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

            super.onPostExecute(s);
            titleSb = new StringBuilder();
            descSb = new StringBuilder();
            if (s != null && s.toString().trim().length() != 0) {
                try {
                    JSONArray ja = new JSONArray(s);
                    JSONObject data;
                    OnDelivery order;
                    ListViewItem item;
                    for (int i = 0; i < ja.length(); i++) {
                        data = ja.getJSONObject(i);

                        item = new ListViewItem();
                        order = new OnDelivery();
                        titleSb.setLength(0);
                        descSb.setLength(0);

                        order.setOrderNum(data.getInt("orderNum"));
                        order.setCallNum(data.getInt("callNum"));
                        order.setReceiverName(data.getString("receiverName"));
                        order.setReceiverPhone(data.getString("receiverPhone"));
                        order.setReceiverAddress(data.getString("receiverAddress"));
                        order.setReceiverAddressDetail(data.getString("receiverAddressDetail"));
                        order.setOrderPrice(data.getInt("orderPrice"));
                        order.setMemo(data.getString("memo"));
                        order.setFreightList(data.getString("freightList"));

                        titleSb.append("수령정보 " + (i + 1));
                        descSb.append("   수령인   ");
                        descSb.append(order.getReceiverName()).append("\n");
                        descSb.append("   수령지   ");
                        descSb.append(order.getReceiverAddress());

                        item.setTitleStr(titleSb.toString());
                        item.setDescStr(descSb.toString());
                        item.setCallNum(order.getOrderNum());
                        totalPrice += order.getOrderPrice();
                        dateList.add(item);
                        list.add(order);
                    }
                    tvTotalPrice.setText(totalPrice + "원");
                    editor.putInt("totalPrice",totalPrice);
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (dateList.size() != 0) {
                    CustomAdapter adapter = new CustomAdapter(context, 0, dateList);
                    orderListview.setAdapter(adapter);
                    orderCount = dateList.size()-1; //  오더 갯수. 데이트리스트는 수령지 정보도 가지고 있어서 하나 빼씀
                } else {


                }
            } else {


            }

            payAppCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DialogPayActivity.class);
                    intent.putExtra("orderCount", orderCount);
                    startActivityForResult(intent, 2000);
                }
            });

            payAppDeposit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DialogPayActivity.class);
                    intent.putExtra("orderCount", orderCount);
                    startActivityForResult(intent, 2020);
                }
            });

            paySenderCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    values.put("payType", 2);
                    values.put("payStatus", 0);
                    values.put("totalPrice", totalPrice);
                    values.put("callNum", callNum);
                    MainTask mainTask = new MainTask(payUrl, values);
                    mainTask.execute();
                    Intent intent = new Intent(context, OrderCompleteActivity.class);
                    intent.putExtra("orderCount", orderCount);
                    startActivity(intent);
                }
            });

            paySenderMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    values.put("payType", 3);
                    values.put("payStatus", 0);
                    values.put("totalPrice", totalPrice);
                    values.put("callNum", callNum);
                    MainTask mainTask = new MainTask(payUrl, values);
                    mainTask.execute();
                    Intent intent = new Intent(context, OrderCompleteActivity.class);
                    intent.putExtra("orderCount", orderCount);
                    startActivity(intent);
                }
            });

            payReceiverCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    values.put("payType", 4);
                    values.put("payStatus", 0);
                    values.put("totalPrice", totalPrice);
                    values.put("callNum", callNum);
                    MainTask mainTask = new MainTask(payUrl, values);
                    mainTask.execute();
                    Intent intent = new Intent(context, OrderCompleteActivity.class);
                    intent.putExtra("orderCount", orderCount);
                    startActivity(intent);
                }
            });

            payReceiverMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    values.put("payType", 5);
                    values.put("payStatus", 0);
                    values.put("totalPrice", totalPrice);
                    values.put("callNum", callNum);
                    MainTask mainTask = new MainTask(payUrl, values);
                    mainTask.execute();
                    Intent intent = new Intent(context, OrderCompleteActivity.class);
                    intent.putExtra("orderCount", orderCount);
                    startActivity(intent);
                }
            });

            payCredit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    values.put("payType", 6);
                    values.put("payStatus", 1);
                    values.put("totalPrice", totalPrice);
                    values.put("callNum", callNum);
                    MainTask mainTask = new MainTask(payUrl, values);
                    mainTask.execute();
                    Intent intent = new Intent(context, OrderCompleteActivity.class);
                    intent.putExtra("orderCount", orderCount);
                    startActivity(intent);
                }
            });
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
            Intent intent = new Intent(getApplicationContext(), Order1Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_order_list) {
            Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
            startActivity(intent);
        } else if (id == R.id.chatBot) {
            Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
            startActivity(intent);
        } else if (id == R.id.userUpdate) {
            Intent intent = new Intent(getApplicationContext(), UserInfoUpdateActivity.class);
            startActivity(intent);
        } else if (id == R.id.insuindo) {
            Intent intent = new Intent(getApplicationContext(), CSBeamActivity.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            SharedPreferences.Editor editor = getSharedPreferences("setting", 0).edit();
            editor.clear().commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
