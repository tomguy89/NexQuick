package com.balbadak.nexquickpro;

import android.content.Context;
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
public class Fragment1 extends Fragment {


    ViewPager viewPager;
    ListView insuListView;
    ListView indoListView;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_list, container, false);

        viewPager = getActivity().findViewById(R.id.pager);

        insuListView = (ListView) view.findViewById(R.id.insu_listview);
        indoListView = (ListView) view.findViewById(R.id.indo_listview);

        ArrayList<String> insuList = new ArrayList<>();
        insuList.add("5/28");
        insuList.add("5/29");
        insuList.add("5/30");


        ArrayList<String> indoList = new ArrayList<>();
        indoList.add("5/22");
        indoList.add("5/23");
        indoList.add("5/25");

        Fragment1.CustomAdapter insuAdapter = new Fragment1.CustomAdapter(getActivity(), 0, insuList);
        Fragment1.CustomAdapter indoAdapter = new Fragment1.CustomAdapter(getActivity(), 0, indoList);
        insuListView.setAdapter(insuAdapter);
        indoListView.setAdapter(indoAdapter);



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
