package com.balbadak.nexquickpro;

import android.app.DatePickerDialog;
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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;



import com.balbadak.nexquickpro.vo.OnDelivery;
import com.balbadak.nexquickpro.vo.ListViewItem;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class OrderListBeforeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Context context;
    String mainUrl;
    private SharedPreferences loginInfo;
    private SharedPreferences.Editor editor;
    private ListView listView;
    private int orderNum;
    private int callNum;
    private int qpId;
    private String qpName;
    private int onWork;
    private String callTime;


    private ArrayList<ListViewItem> quickList;
    private ArrayList<OnDelivery> list;
    private OnDelivery orderDetail;

    TextView nav_header_title;
    TextView nav_header_contents;
    Switch onWorkSwitch;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_before);
        mainUrl = getResources().getString(R.string.main_url);
        context = this;
        loginInfo = getSharedPreferences("setting", 0);
        editor = loginInfo.edit();
        if (loginInfo != null) {
            qpId = loginInfo.getInt("qpId", 0);
            qpName = loginInfo.getString("qpName", "");
            onWork = loginInfo.getInt("onWork", 0);
        }

        final Calendar cal = Calendar.getInstance();
        //DATE PICKER DIALOG

        Button picker = findViewById(R.id.order_date_picker);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        callTime = String.format("%02d/%02d/%d",  month + 1, date, year);
                        String url = mainUrl + "appCall/getQPCallByIdAndDate.do";
                        ContentValues values = new ContentValues();
                        values.put("callTime", callTime);
                        values.put("qpId", qpId);

                        GetListTask getListTask = new GetListTask(url, values);
                        getListTask.execute();

                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이전으로 클릭 안되게 옵션
                dialog.show();
            }


        });


        quickList = new ArrayList<>();
        list = new ArrayList<>();
        listView = (ListView) this.findViewById(R.id.order_before_listview);

        String url = mainUrl + "list/qptotalList.do";
        ContentValues values = new ContentValues();
        values.put("qpId", qpId);

        GetListTask getListTask = new GetListTask(url,values);
        getListTask.execute();

        CustomAdapter adapter = new CustomAdapter(this, 0, quickList);
        listView.setAdapter(adapter);

        // Adding Toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationView.getHeaderView(0);


        nav_header_title = (TextView) nav_header_view.findViewById(R.id.nav_header_title);
        nav_header_contents = (TextView) nav_header_view.findViewById(R.id.nav_header_contents);
        nav_header_title.setText(qpName + " 퀵프로님");


        onWorkSwitch = (Switch) nav_header_view.findViewById(R.id.onWorkSwitch);

        if(onWork == 2) {
            //퇴근상태이면 들어갈 각종 디폴트 상황들, oncreat에서만 로딩됨
            onWorkSwitch.setChecked(false);
            nav_header_contents.setText("운행정지");

        } else if (onWork == 1) {
            //출근상태라면 들어갈 각종 디폴트 상황들, oncreat에서만 로딩됨
            onWorkSwitch.setChecked(true);
            nav_header_contents.setText("운행중");

        } else{
            onWorkSwitch.setChecked(false);
            onWorkSwitch.setEnabled(false);
            nav_header_contents.setText("출근 전");
        }

        if (onWork != -1){
            onWorkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                //스위치 토글시 바뀔 내용들
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Intent i = new Intent(getApplicationContext(), LocationService.class);
                    if(isChecked) {
                        editor.putInt("onWork", 1); //프리퍼런스 값 바꿈
                        editor.commit();
                        nav_header_contents.setText("운행중");
                        String surl = mainUrl +"qpAccount/changeQPStatus.do";
                        ContentValues svalues = new ContentValues();
                        svalues.put("qpId", qpId);
                        svalues.put("qpStatus", 0);
                        QPStatusTask qpStatusTask = new QPStatusTask(surl, svalues);
                        qpStatusTask.execute();

                    } else {
                        editor.putInt("onWork", 2); //프리퍼런스 값 바꿈
                        editor.commit();
                        nav_header_contents.setText("운행정지");
                        String surl = mainUrl +"qpAccount/changeQPStatus.do";
                        ContentValues svalues = new ContentValues();
                        svalues.put("qpId", qpId);
                        svalues.put("qpStatus", 1);
                        QPStatusTask qpStatusTask = new QPStatusTask(surl, svalues);
                        qpStatusTask.execute();
                    }
                }
            });
        }

    }

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


    // 내비게이션 메뉴 관련 (인텐트 설정 - 메뉴이름을 바꾸려면 activity_navigation_drawer.xml로 가시오
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main_menu) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.order_list_before) {
            Intent intent = new Intent(getApplicationContext(), OrderListBeforeActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.update_info) {
            Intent intent = new Intent(getApplicationContext(), UpdateUserActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.deviceManager) {
            Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
            startActivity(intent);
        } else if (id == R.id.getOffWork){
            Intent intent = new Intent(getApplicationContext(), GoToWorkActivity.class);
            editor.remove("onWork");
            editor.commit();
            Intent i = new Intent(getApplicationContext(),LocationService.class);
            stopService(i);
            Intent i2 = new Intent(getApplicationContext(), BluetoothService.class);
            stopService(i2);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.logout) {

            editor.remove("qpId");
            editor.remove("qpName");
            editor.remove("qpPhone");
            editor.remove("qpDeposit");
            editor.remove("rememberId");
            editor.remove("rememberPassword");
            editor.remove("onWork");
            editor.commit();
            Intent i = new Intent(getApplicationContext(),LocationService.class);
            stopService(i);
            Intent i2 = new Intent(getApplicationContext(), BluetoothService.class);
            stopService(i2);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


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

            if (s != null && s.toString().trim().length()!= 0) {
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

                        order.setUrgent(data.getInt("urgent"));
                        order.setOrderNum(data.getInt("orderNum"));
                        order.setCallNum(data.getInt("callNum"));
                        order.setCallTime(data.getString("callTime"));
                        order.setSenderName(data.getString("senderName"));
                        order.setSenderPhone(data.getString("senderPhone"));
                        order.setSenderAddress(data.getString("senderAddress"));
                        order.setSenderAddressDetail(data.getString("senderAddressDetail"));
                        order.setReceiverName(data.getString("receiverName"));
                        order.setReceiverPhone(data.getString("receiverPhone"));
                        order.setReceiverAddress(data.getString("receiverAddress"));
                        order.setReceiverAddressDetail(data.getString("receiverAddressDetail"));
                        order.setOrderPrice(data.getInt("orderPrice"));
                        order.setMemo(data.getString("memo"));
                        order.setDeliveryStatus(data.getInt("deliveryStatus"));
                        order.setFreightList(data.getString("freightList"));
                        order.setArrivaltime(data.getString("arrivalTime"));

                        titleSb.append("   배송일   ");
                        titleSb.append(order.getArrivaltime());
                        descSb.append("   발송지   ");
                        descSb.append(order.getSenderAddress()).append("\n");
                        descSb.append("   수령지   ");
                        descSb.append(order.getReceiverAddress());

                        item.setTitleStr(titleSb.toString());
                        item.setDescStr(descSb.toString());
                        item.setCallNum(order.getOrderNum());
                        item.setDeliveryStatus(order.getDeliveryStatus());

                        quickList.add(item);
                        list.add(order);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (s != null) {
                    CustomAdapter adapter = new CustomAdapter(context, 0, quickList);
                    listView.setAdapter(adapter);
                } else {



                }
            } else {

            }

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

            orderDetail = list.get(position);

            titleStrView.setText(data.get(position).getTitleStr());
            descStrView.setText(data.get(position).getDescStr());

            detailBtn.setOnClickListener(new View.OnClickListener() {
                OnDelivery orderInfo = orderDetail;
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DialogDetailActivity.class);
                    intent.putExtra("num", orderInfo.getOrderNum());
                    intent.putExtra("name", orderInfo.getReceiverName());
                    intent.putExtra("phone", orderInfo.getReceiverPhone());
                    intent.putExtra("address", orderInfo.getReceiverAddress()+" "+orderInfo.getReceiverAddressDetail());
                    intent.putExtra("freights", orderInfo.getFreightList());
                    intent.putExtra("orderPrice", orderInfo.getOrderPrice());
                    intent.putExtra("memo", orderInfo.getMemo());
                    intent.putExtra("deliveryStatus", 4);
                    startActivity(intent);
                }
            });
            return v;
        }


    }


    public class QPStatusTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public QPStatusTask(String url, ContentValues values) {

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
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

        }
    }
}
