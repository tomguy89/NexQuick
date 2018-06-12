package com.balbadak.nexquickpro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.annotation.SuppressLint;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;


@SuppressLint("ValidFragment")
public class fragment_order_list extends Fragment {

    ViewPager viewPager;
    ListView insuListView;
    TextView insuTitle;
    Context context;

    ArrayList<ListViewItem> insuList;
    ArrayList<ListViewItem> indoList;

    SharedPreferences loginInfo;
    int orderNum;
    int callNum;
    int qpId;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_list, container, false);

        viewPager = getActivity().findViewById(R.id.pager);

        context = getActivity();

        insuTitle = (TextView) view.findViewById(R.id.insu_title);
        insuListView = (ListView) view.findViewById(R.id.insu_listview);

        insuList = new ArrayList<>();


        loginInfo = getActivity().getSharedPreferences("setting", 0);

        if (loginInfo != null) {
            qpId = loginInfo.getInt("qpId", 0);
        }

        String url = "http://70.12.109.173:9090/NexQuick/appCall/orderListByQPId.do";

        ContentValues values = new ContentValues();
        values.put("qpId", qpId);
        // AsyncTask를 통해 HttpURLConnection 수행.
        GetListTask getListTask = new GetListTask(url, values);
        getListTask.execute();

        return view;


    }


    private class CustomAdapter extends ArrayAdapter<ListViewItem> {
        private ArrayList<ListViewItem> data;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<ListViewItem> object1) {
            super(context, textViewResourceId, object1);
            this.data = object1;
//            this.items = object2;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.order_list_item, null);
            }

            TextView titleStrView = (TextView) v.findViewById(R.id.order_list_item_date);
            TextView descStrView = (TextView) v.findViewById(R.id.order_list_item_detail);
            TextView UrgentStrView = (TextView) v.findViewById(R.id.order_list_item_urgent);
            Button detailBtn = (Button) v.findViewById(R.id.detailBtn);



            if(data.get(position).getQuickType() == 1) {
                titleStrView.setText("인수/"+data.get(position).getTitleStr());
                titleStrView.setTextColor(getResources().getColor(R.color.colorGold));

                detailBtn.setOnClickListener(new View.OnClickListener() {
                    int cn = callNum;

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DialogDetailActivity.class);
                        intent.putExtra("callNum", cn);
                        startActivity(intent);
                    }
                });

            } else if(data.get(position).getQuickType() == 2) {
                titleStrView.setText("인도/"+data.get(position).getTitleStr());
                titleStrView.setTextColor(getResources().getColor(R.color.colorEmerald));

                detailBtn.setOnClickListener(new View.OnClickListener() {
                    int on = orderNum;

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DialogDetailActivity.class);
                        intent.putExtra("orderNum", on);
                        startActivity(intent);
                    }
                });






            }

            if(data.get(position).getUrgentStr()!=null) {
                //UrgentStrView.setText("급");
                titleStrView.setTextColor(getResources().getColor(R.color.colorTomato));
            }

            descStrView.setText( data.get(position).getDescStr());
            orderNum = data.get(position).getOrderNum();

            Log.i("orderNum", orderNum + "");


            return v;
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
//                            titleSb.append(data.getString("callTime"));
                            titleSb.append("발송지: ");
                            titleSb.append(data.getString("senderAddress"));
//                          descSb.append("발송인: ");
//                          descSb.append(data.getString("senderName"));
//                            descSb.append("발송지: ");
//                            descSb.append(data.getString("senderAddress"));
                            if(data.getString("freightList")!=null) descSb.append(data.getString("freightList"));
                            descSb.append("  / 가격: ");
                            descSb.append(data.getString("orderPrice"));

                            item.setTitleStr(titleSb.toString());
                            item.setDescStr(descSb.toString());
                            item.setCallNum(data.getInt("callNum"));
                            item.setOrderNum(data.getInt("orderNum"));
                            item.setQuickType(1);

                            insuList.add(item);
                            }

                        } else if (data.getInt("deliveryStatus") == 2) {
                            if (data.getInt("urgent") == 1) {
                                titleSb.append("급/");
                                item.setUrgentStr("급");
                            }

//                            titleSb.append(data.getString("callTime"));
                            titleSb.append("수령지: ");
                            titleSb.append(data.getString("receiverAddress"));
//                            descSb.append("수령인: ");
//                            descSb.append(data.getString("receiverName"));
                            if(data.getString("freightList")!=null) descSb.append(data.getString("freightList"));
                            descSb.append("  / 가격: ");
                            descSb.append(data.getString("orderPrice"));

                            item.setTitleStr(titleSb.toString());
                            item.setDescStr(descSb.toString());
                            item.setCallNum(data.getInt("callNum"));
                            item.setOrderNum(data.getInt("orderNum"));
                            item.setQuickType(2);

                            insuList.add(item);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                CustomAdapter insuAdapter = new CustomAdapter(getActivity(), 0, insuList);
                insuListView.setAdapter(insuAdapter);


            } else {

                insuTitle.setText("현재 받은 퀵이 없습니다");

            }


        }
    }

}
