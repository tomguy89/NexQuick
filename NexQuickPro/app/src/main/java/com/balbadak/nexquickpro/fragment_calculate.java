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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.balbadak.nexquickpro.vo.ListViewItem;
import com.balbadak.nexquickpro.vo.OnDelivery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class fragment_calculate extends Fragment {

    String mainUrl;
    ViewPager viewPager;
    ListView listView;
    View view;

    SharedPreferences loginInfo;
    int qpId;

    ArrayList<ListViewItem> dataList;
    ArrayList<OnDelivery> list;
    OnDelivery orderDetail;

    private TextView calculateNoneTv;
    private TextView inappMoneyTv;
    private TextView placeMoneyTv;
    private TextView totalJungsanTv;

    private LinearLayout calculate_sub;

    private Button finishBtn;
    int jungsan;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_calculate, container, false);

        viewPager = getActivity().findViewById(R.id.pager);
        mainUrl = getActivity().getResources().getString(R.string.main_url);


        listView = (ListView) view.findViewById(R.id.calculate_listview);

        finishBtn = view.findViewById(R.id.calculate_finish);
        dataList = new ArrayList<>();
        list = new ArrayList<>();

        calculate_sub = view.findViewById(R.id.calculate_sub);

        loginInfo = getActivity().getSharedPreferences("setting",0);

        qpId =  loginInfo.getInt("qpId",0);

        calculateNoneTv = (TextView) view.findViewById(R.id.calculate_none);
        inappMoneyTv = (TextView) view.findViewById(R.id.inappMoney);
        placeMoneyTv = (TextView) view.findViewById(R.id.placeMoney);
        totalJungsanTv = (TextView) view.findViewById(R.id.totaljungsan);

        String url = mainUrl + "list/calculationList.do";
        ContentValues values = new ContentValues();
        values.put("qpId", qpId);

        GetListTask getListTask = new GetListTask(url,values);
        getListTask.execute();

        CustomAdapter adapter = new CustomAdapter(getActivity(), 0, dataList);
        listView.setAdapter(adapter);

        url = mainUrl + "qpAccount/unpayedMoney.do";

        GetCalMoneyListTask getCalMoneyListTask = new GetCalMoneyListTask(url,values);
        getCalMoneyListTask.execute();




        return view;
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
            Log.e("money", s);
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
                        order.setPayType(data.getInt("payType"));

                        switch(order.getPayType()){
                            case 0:
                                titleSb.append("계좌이체");
                                break;
                            case 1:
                                titleSb.append("온라인결제");
                                break;
                            case 2:
                                titleSb.append("발송인/현금");
                                break;
                            case 3:
                                titleSb.append("발송인/카드");
                                break;
                            case 4:
                                titleSb.append("수령인/현금");
                                break;
                            case 5:
                                titleSb.append("수령인/카드");
                                break;
                            case 6:
                                titleSb.append("기업후불");
                                break;
                        }

                        titleSb.append("   ");
                        titleSb.append(order.getOrderPrice()+" 원");
                        descSb.append("픽/");
                        descSb.append(order.getSenderAddress()).append("\n");
                        descSb.append("착/");
                        descSb.append(order.getReceiverAddress());

                        item.setTitleStr(titleSb.toString());
                        item.setDescStr(descSb.toString());
                        item.setCallNum(order.getOrderNum());
                        item.setDeliveryStatus(order.getDeliveryStatus());

                        dataList.add(item);
                        list.add(order);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (s != null) {
                    CustomAdapter adapter = new CustomAdapter(getActivity(), 0, dataList);
                    listView.setAdapter(adapter);
                } else {



                }
            } else {

                calculateNoneTv.setVisibility(View.VISIBLE);
            }

        }
    }


    // 여기부터 AsyncTask 영역
    public class GetCalMoneyListTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public GetCalMoneyListTask(String url, ContentValues values) {

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

            Log.e("money", s);
            if (s != null && s.toString().trim().length()!= 0) {
                try {
                    JSONObject data = new JSONObject(s);

                    int inapp = data.getInt("inapp");
                    int place = data.getInt("place");

                    inappMoneyTv.setText(inapp+" 원");
                    placeMoneyTv.setText(place+" 원");
                    jungsan = (int)((inapp+place)*0.95);
                    totalJungsanTv.setText(jungsan + " 원");

                    if(inapp == 0 && place == 0) {
                        calculateNoneTv.setVisibility(View.VISIBLE);
                        calculate_sub.setVisibility(View.GONE);
                        finishBtn.setEnabled(false);
                    }

                    if(jungsan > 0) {

                        finishBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String url = mainUrl + "qpAccount/processPayment.do";
                                ContentValues values = new ContentValues();
                                values.put("qpId", qpId);
                                values.put("jungsan", jungsan);

                                ProcessPaymentTask processPaymentTask = new ProcessPaymentTask(url,values);
                                processPaymentTask.execute();


                            }
                        });

                    } else {

                        finishBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String url = mainUrl + "qpAccount/processDepositSubtr.do";
                                ContentValues values = new ContentValues();
                                values.put("qpId", qpId);
                                values.put("jungsan", jungsan);

                                DepositSubtrTask depositSubtrTask = new DepositSubtrTask(url,values);
                                depositSubtrTask.execute();

                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            } else {


                calculateNoneTv.setVisibility(View.VISIBLE);
                calculate_sub.setVisibility(View.GONE);
                finishBtn.setEnabled(false);

            }

        }
    }


    public class ProcessPaymentTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public ProcessPaymentTask(String url, ContentValues values) {

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
            //qpPay JSON 객체를 파싱한다.

            int money=0;
            String qpBank = null;
            String qpAccount = null;


            if(s!=null){
                try {
                    JSONObject object = new JSONObject(s);

                    money=object.getInt("money");
                    qpBank=object.getString("qpBank");
                    qpAccount=object.getString("qpAccount");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(money!= 0){

                } else{

                    Toast.makeText(getActivity(),"정산 가능한 내역이 없습니다.",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(),"정산 가능한 내역이 없습니다.",Toast.LENGTH_SHORT).show();
            }

            //정산완료 팝업을 띄웁니다.
            Intent intent = new Intent(getActivity(), DialogCalculateEndActivity.class);
            intent.putExtra("money",money);
            intent.putExtra("qpBank",qpBank);
            intent.putExtra("qpAccount",qpAccount);

            startActivity(intent);
        }
    }

//로직 수정
    public class DepositSubtrTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public DepositSubtrTask(String url, ContentValues values) {

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
            //qpPay JSON 객체를 파싱한다.

            int money = 0;
            String qpBank = null;
            String qpAccount = null;

            if (s != null) {
                try {
                    JSONObject object = new JSONObject(s);

                    money = object.getInt("money");


                } catch (JSONException e) {
                    e.printStackTrace();

                }

                loginInfo.edit().putInt("deposit", money).commit();

                Intent intent = new Intent(getActivity(), DialogCalculateEndActivity.class);
                intent.putExtra("money", money);

                startActivity(intent);
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
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    Intent intent = new Intent(getActivity(), DialogDetailActivity.class);
                    intent.putExtra("num", orderInfo.getOrderNum());
                    intent.putExtra("callNum", orderInfo.getCallNum());
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

}
