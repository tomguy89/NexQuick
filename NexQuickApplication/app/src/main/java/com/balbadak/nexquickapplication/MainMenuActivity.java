package com.balbadak.nexquickapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    Context context;
    private SharedPreferences loginInfo;
    private String csId;
    private String csName;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;


        Button orderList = (Button) findViewById(R.id.orderListBtn);
        Button newOrder = (Button) findViewById(R.id.newOrderBtn);
        Button tapAndConfirm = (Button) findViewById(R.id.tapAndConfirmBtn);

        TextView greeting = (TextView) findViewById(R.id.greeting);

        loginInfo = getSharedPreferences("setting", 0);
        if (loginInfo != null && loginInfo.getString("csId", "") != null && loginInfo.getString("csId", "").length() != 0) {
            csId = loginInfo.getString("csId", "");
            csName = loginInfo.getString("csName", "");
            //폰은
        }

        String temp = csName + "님 안녕하세요! :)";
        greeting.setText(temp);

        orderList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
                startActivity(intent);
            }
        });

        newOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Order1Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        tapAndConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CSBeamActivity.class);
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
        } else if(id == R.id.chatBot) {
            Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
            startActivity(intent);
        } else if(id == R.id.userUpdate) {
            Intent intent = new Intent(getApplicationContext(), UserInfoUpdateActivity.class);
            startActivity(intent);
        } else if(id == R.id.insuindo) {
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
