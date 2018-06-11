package com.balbadak.nexquickpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class fragment_calculate extends Fragment {

    ViewPager viewPager;
    ListView listView;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_calculate, container, false);

        viewPager = getActivity().findViewById(R.id.pager);

        listView = (ListView) view.findViewById(R.id.calculate_listview);

        Button finishBtn = view.findViewById(R.id.calculate_finish);
        ArrayList<String> dateList = new ArrayList<>();
        dateList.add("5/28");
        dateList.add("5/29");
        dateList.add("5/30");



        CustomAdapter adapter = new CustomAdapter(getActivity(), 0, dateList);
        listView.setAdapter(adapter);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //정산 기능을 넣어주세요

                //정산완료 팝업을 띄웁니다.
                Intent intent = new Intent(getActivity(), DialogCalculateEndActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }


    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> dates;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> object1) {
            super(context, textViewResourceId, object1);
            this.dates = object1;
//            this.items = object2;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null ) {
                    LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
