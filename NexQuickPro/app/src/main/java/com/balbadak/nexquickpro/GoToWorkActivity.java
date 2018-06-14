package com.balbadak.nexquickpro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class GoToWorkActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Context context = this;
    private SharedPreferences loginInfo;
    private SharedPreferences.Editor editor;
    String qpPhone;
    String qpName;
    private int onWork;
    Button workbtn;
    Button exitbtn;

    TextView nav_header_title;
    TextView nav_header_contents;
    Switch onWorkSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_work);
        loginInfo = getSharedPreferences("setting", 0);
        editor = loginInfo.edit();

        if (loginInfo != null) {
            qpPhone = loginInfo.getString("qpPhone", "");
            qpName = loginInfo.getString("qpName", "");
        }

        TextView greeting = findViewById(R.id.greeting);

        greeting.setText(qpName + "프로님" + "\n안녕하세요?");

        workbtn = findViewById(R.id.workbtn);
        exitbtn = findViewById(R.id.exitbtn);

        if (!runtime_permissions()) {
            enable_buttons();
        }

        /*workbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //출근처리 메소드 넣어주세요.
                Intent i =new Intent(getApplicationContext(),LocationService.class);
                startService(i);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });*/


        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앱종료를 위한 코드

                //나중에 로그아웃, 정산 후 퇴근, 앱 종료 등을 할 때 복사
                Intent i = new Intent(getApplicationContext(), LocationService.class);
                stopService(i);
                //여기까지

                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });


        //은진이 파트 얹어주세용

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


        if (onWork == 0) {
            //퇴근상태이면 들어갈 각종 디폴트 상황들, oncreat에서만 로딩됨

        } else {
            //출근상태라면 들어갈 각종 디폴트 상황들, oncreat에서만 로딩됨

        }


        onWorkSwitch = (Switch) nav_header_view.findViewById(R.id.onWorkSwitch);

        onWorkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //스위치 토글시 바뀔 내용들
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putInt("onWork", 1); //프리퍼런스 값 바꿈
                    editor.commit();
                    nav_header_contents.setText("출근중");

                } else {
                    editor.putInt("onWork", 0); //프리퍼런스 값 바꿈
                    editor.commit();
                    nav_header_contents.setText("퇴근중");
                }
            }
        });

/*        //인수인도용 플로팅액션버튼 아직 미구현
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        */
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



    private void enable_buttons() {

        workbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //출근처리 메소드 넣어주세요.

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }
}
