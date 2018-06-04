package com.balbadak.nexquickpro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }



    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

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
}
