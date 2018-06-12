package com.balbadak.nexquickpro;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    //폰트관련 설정
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    ArrayList<ListViewItem> quickList;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences loginInfo;
    private SharedPreferences.Editor editor;
    private String qpName;
    private int qpId;
    private int onWork;
    TextView nav_header_title;
    TextView nav_header_contents;
    android.support.v7.widget.SwitchCompat onWorkSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginInfo = getSharedPreferences("setting", 0);
        editor = loginInfo.edit();

        qpName = loginInfo.getString("qpName", "");
        qpId = loginInfo.getInt("qpId", 0);
        onWork = loginInfo.getInt("onWork", 0);
        quickList = new ArrayList<>();

        initQuickList();
        initNavi();

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

        if (id == R.id.nav_new_order) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order_list) {

        } else if (id == R.id.nav_user_info) {

        } else if (id == R.id.nav_customer_center) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void initQuickList(){

        String url = "http://70.12.109.173:9090/NexQuick/appCall/orderListByQPId.do";

        ContentValues values = new ContentValues();
        values.put("qpId", qpId);
        // AsyncTask를 통해 HttpURLConnection 수행.
        GetListTask getListTask = new GetListTask(url, values);
        getListTask.execute();
    }

    public void initNavi(){
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
        nav_header_title.setText(qpName+" 퀵프로님");



        if(onWork == 0) {
            //퇴근상태이면 들어갈 각종 디폴트 상황들, oncreat에서만 로딩됨
            onWorkSwitch.setChecked(false);
            nav_header_contents.setText("운행정지");
        } else {
            //출근상태라면 들어갈 각종 디폴트 상황들, oncreat에서만 로딩됨
            onWorkSwitch.setChecked(true);
            nav_header_contents.setText("운행중");
            Intent i = new Intent(getApplicationContext(), LocationService.class);
            startService(i);
        }


        onWorkSwitch = (android.support.v7.widget.SwitchCompat) nav_header_view.findViewById(R.id.onWorkSwitch);

        onWorkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //스위치 토글시 바뀔 내용들
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent i = new Intent(getApplicationContext(), LocationService.class);
                if(isChecked) {
                    editor.putInt("onWork", 1); //프리퍼런스 값 바꿈
                    editor.commit();
                    startService(i);
                    nav_header_contents.setText("운행중");

                } else {
                    editor.putInt("onWork", 0); //프리퍼런스 값 바꿈
                    editor.commit();
                    stopService(i);
                    nav_header_contents.setText("운행정지");
                }
            }
        });


        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("퀵리스트").setIcon(R.drawable.tab_apply_detail_black));
        tabLayout.addTab(tabLayout.newTab().setText("경로안내").setIcon(R.drawable.tab_address_detail));
        tabLayout.addTab(tabLayout.newTab().setText("정산하기").setIcon(R.drawable.tab_complete_black));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setCurrentItem(1); // routeActivity (경로화면)이 디폴트

    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {


        // Count number of tabs
        private int tabCount;

        public TabPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment=null;
            Bundle bundle;

            // Returning the current tabs
            switch (position) {
                case 0:
                    fragment = new fragment_order_list();
                    bundle = new Bundle();
                    bundle.putParcelableArrayList("quickList", quickList);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new fragment_route();
                    bundle = new Bundle();
                    bundle.putParcelableArrayList("quickList", quickList);
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    fragment = new fragment_calculate();
                    break;
                default:

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabCount;
        }
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


            if (s != null) {
                Log.e("받아온 것", s);
                try {
                    JSONArray ja = new JSONArray(s);
                    JSONObject data;
                    HashSet<Integer> callNumSet = new HashSet<>();
                    for (int i = 0; i < ja.length(); i++) {
                        data = ja.getJSONObject(i);

                        ListViewItem item = new ListViewItem();
                        titleSb.setLength(0);
                        descSb.setLength(0);


                        if (data.getInt("deliveryStatus") == 1) {
                            if (data.getInt("urgent") == 1) {
                                titleSb.append("급/");
                                item.setUrgentStr("급");
                            }


                            if(!callNumSet.contains(data.getInt("callNum"))) {

                                callNumSet.add(data.getInt("callNum"));

                                titleSb.append("픽/");
                                titleSb.append(data.getString("senderAddress"));

                                if(data.getString("freightList")!=null) descSb.append(data.getString("freightList"));
                                descSb.append("/");
                                descSb.append(data.getString("orderPrice"));

                                item.setTitleStr(titleSb.toString());
                                item.setDescStr(descSb.toString());
                                item.setCallNum(data.getInt("callNum"));
                                item.setOrderNum(data.getInt("orderNum"));
                                item.setQuickType(1);

                                quickList.add(item);
                            }

                        } else if (data.getInt("deliveryStatus") == 2) {
                            if (data.getInt("urgent") == 1) {
                                titleSb.append("급/");
                                item.setUrgentStr("급");
                            }

                            titleSb.append("착/");
                            titleSb.append(data.getString("receiverAddress"));

                            if(data.getString("freightList")!=null) descSb.append(data.getString("freightList"));
                            descSb.append("/");
                            descSb.append(data.getString("orderPrice"));

                            item.setTitleStr(titleSb.toString());
                            item.setDescStr(descSb.toString());
                            item.setCallNum(data.getInt("callNum"));
                            item.setOrderNum(data.getInt("orderNum"));
                            item.setQuickType(2);

                            quickList.add(item);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }

        }
    }

}



