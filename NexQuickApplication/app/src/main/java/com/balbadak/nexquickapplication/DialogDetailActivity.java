package com.balbadak.nexquickapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.balbadak.nexquickapplication.vo.OrderInfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DialogDetailActivity extends AppCompatActivity {

    //.173 태진, .164 승진
    private String mainUrl = "http://70.12.109.164:9090/NexQuick/";

    int callNum;
    int deliveryStatus;
    TextView contents;
    ArrayList<OrderInfo> orderList;
    Button quickCancelBtn;
    Button chatBotBtn;
    Button mapBtn;
    Context context;
    TextView deliveryStatusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_detail);
        context = this;
        orderList = new ArrayList<>();
        Intent intent = getIntent();
        callNum = intent.getExtras().getInt("callNum");
        deliveryStatus = intent.getExtras().getInt("deliveryStatus");

        String url = mainUrl + "call/getOrderList.do";

        ContentValues values = new ContentValues();
        values.put("callNum", callNum);
        // AsyncTask를 통해 HttpURLConnection 수행.
        DialogDetailActivity.GetDetailTask getDetailTask = new DialogDetailActivity.GetDetailTask(url, values);
        getDetailTask.execute();


        contents = (TextView) findViewById(R.id.quick_detail_contents);


        ImageButton cancelBtn = (ImageButton) findViewById(R.id.dialogCancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deliveryStatusTv = (TextView) findViewById(R.id.deliveryStatusTv);
        quickCancelBtn = (Button) findViewById(R.id.quickCancelBtn);
        chatBotBtn = (Button) findViewById(R.id.quickCancelBtn);
        mapBtn = (Button) findViewById(R.id.mapBtn);


        switch (deliveryStatus) {

            case 1:
                deliveryStatusTv.setText("주문완료");
                break;
            case 2:
                deliveryStatusTv.setText("배차완료");
                break;
            case 3:
                deliveryStatusTv.setText("배송중");
                quickCancelBtn.setAlpha(.5f);
                quickCancelBtn.setEnabled(false);
                quickCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "인수 이후에는 취소하실 수 없습니다.", Toast.LENGTH_SHORT);
                    }
                });
                // 작동 안함

                break;
            case 4:
                deliveryStatusTv.setText("배송완료");
                quickCancelBtn.setAlpha(.5f);
                quickCancelBtn.setEnabled(false);
                quickCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "인수 이후에는 취소하실 수 없습니다.", Toast.LENGTH_SHORT);
                    }
                });
                // 작동 안함

                break;
            default:
                deliveryStatusTv.setText("미처리주문");

        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public class GetDetailTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public GetDetailTask(String url, ContentValues values) {

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

            Log.e("받아온 것", s);
            if (s != null) {
                try {
                    JSONArray ja = new JSONArray(s);
                    JSONObject data;
                    for (int i = 0; i < ja.length(); i++) {
                        data = ja.getJSONObject(i);

                        OrderInfo orderItem = new OrderInfo();

                        orderItem.setOrderNum(data.getInt("orderNum"));
                        orderItem.setCallNum(data.getInt("callNum"));
                        orderItem.setReceiverName(data.getString("receiverName"));
                        orderItem.setReceiverAddress(data.getString("receiverAddress"));
                        orderItem.setReceiverPhone(data.getString("receiverPhone"));
                        orderItem.setMemo(data.getString("memo"));
                        orderItem.setOrderPrice(data.getInt("orderPrice"));
                        orderItem.setIsGet(data.getInt("isGet"));
                        orderItem.setArrivalTime(data.getString("arrivalTime"));
                        orderItem.setDistance(data.getString("distance"));

                        orderList.add(orderItem);

                    }

                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < orderList.size(); i++) {
                        sb.append(orderList.get(i).getOrderNum() + "\n");
                        sb.append(orderList.get(i).getReceiverName() + "\n");
                        sb.append(orderList.get(i).getReceiverAddress() + "\n");
                        sb.append(orderList.get(i).getReceiverPhone() + "\n");
                        sb.append(orderList.get(i).getMemo() + "\n");
                        sb.append(orderList.get(i).getOrderPrice() + "\n");
                        sb.append("\n");
                    }

                    contents.setText(sb.toString());
                    // Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
