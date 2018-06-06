package com.balbadak.nexquickapplication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);


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

        ArrayList<String> dateList = new ArrayList<>();
        dateList.add("5/28");
        dateList.add("5/29");
        dateList.add("5/30");


//        ArrayList<String> itemList = new ArrayList<>();
//        itemList.add("역삼동/박스소/2개");
//        itemList.add("도곡동/서류/1개");
//        itemList.add("대치동/서류/2개");

         CustomAdapter adapter = new CustomAdapter(this, 0, dateList);
         listView.setAdapter(adapter);


        Button orderListBeforeBtn = (Button) findViewById(R.id.orderListBeforeBtn);

        orderListBeforeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderListBeforeActivity.class);
                startActivity(intent);




            }
        });




    }


    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> dates;
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> object1) {
            super(context, textViewResourceId, object1);
            this.dates = object1;
//            this.items = object2;
        }
            public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null ) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.order_list_item, null);
            }

            TextView dateTextView = (TextView) v.findViewById(R.id.order_list_item_date);
            TextView detailTextView = (TextView) v.findViewById(R.id.order_list_item_detail);
            Button detailBtn = (Button) v.findViewById(R.id.detailBtn);

            dateTextView.setText(dates.get(position));

            return v;
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
        } else if (id == R.id.nav_user_info) {

        } else if (id == R.id.nav_customer_center) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
