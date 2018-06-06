package com.balbadak.nexquickapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderListBeforeActivity extends AppCompatActivity {


    private Spinner spinner;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_before);

        Button orderListBtn = (Button) findViewById(R.id.orderListBeforeBtn);

        spinner = (Spinner) findViewById(R.id.orderSpinner);

        final ArrayList<String> list = new ArrayList<>();
        list.add("최근 1주일");
        list.add("최근 2주");
        list.add("최근 1달");
        list.add("최근 1년");


        //스피너용 어댑터
        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);

        //스피너 이벤트리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderListBeforeActivity.this, "선택된 아이템 : " + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView = (ListView) this.findViewById(R.id.order_before_listview);

        ArrayList<String> dateList = new ArrayList<>();
        dateList.add("5/28");
        dateList.add("5/29");
        dateList.add("5/30");

        CustomAdapter adapter = new OrderListBeforeActivity.CustomAdapter(this, 0, dateList);
        listView.setAdapter(adapter);


        //진행중 주문으로 넘어가는 리스트
        orderListBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
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
            if (v == null) {
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
}
