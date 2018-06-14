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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class fragment_calculate extends Fragment {

    ViewPager viewPager;
    ListView listView;
    View view;

    SharedPreferences loginInfo;
    int qpId;

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

                loginInfo = getActivity().getSharedPreferences("setting",0);
                qpId =  loginInfo.getInt("qpId",0);


                //정산 기능을 넣어주세요
                //String url = "http://70.12.109.166:9090/NexQuick/qpAccount/processPayment.do";
                String url = "http://70.12.109.173:9090/NexQuick/qpAccount/processPayment.do";
                ContentValues values = new ContentValues();
                values.put("qpId", qpId);

                NetworkTask networkTask = new NetworkTask(url,values);
                networkTask.execute();
                //정산 기능을 넣어주세요



            }
        });

        return view;
    }
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

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
