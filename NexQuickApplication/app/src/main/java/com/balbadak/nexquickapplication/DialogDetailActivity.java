package com.balbadak.nexquickapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.balbadak.nexquickapplication.vo.OrderInfo;
import com.tsengvn.typekit.TypekitContextWrapper;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DialogDetailActivity extends AppCompatActivity {

    private String mainUrl;

    int orderNum;
    int callNum;
    String receiverName;
    String receiverPhone;
    String receiverAddress;
    String freights;
    int orderPrice;
    String memo;
    int deliveryStatus;
    ArrayList<OrderInfo> orderList;
    Button quickCancelBtn;
    Button chatBotBtn;
    Button mapBtn;
    Context context;
    TextView deliveryStatusTv;
    TextView orderNumTV;
    TextView receiverNameTV;
    TextView receiverPhoneTV;
    TextView receiverAddressTV;
    TextView freightsTV;
    TextView orderPriceTV;
    TextView memoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_detail);
        mainUrl = getResources().getString(R.string.main_url);
        context = this;

        Intent intent = getIntent();
        orderNum = intent.getIntExtra("orderNum", 0);
        callNum = intent.getIntExtra("callNum", 0);
        receiverName = intent.getStringExtra("receiverName");
        receiverPhone = intent.getStringExtra("receiverPhone");
        receiverAddress = intent.getStringExtra("receiverAddress");
        freights = intent.getStringExtra("freights");
        orderPrice = intent.getIntExtra("orderPrice", 0);
        memo = intent.getStringExtra("memo");

        Log.w("memo", memo);
        if (memo == null || memo.equals("null")) {
            memo = "메모 없음";
        }

        deliveryStatus = intent.getIntExtra("deliveryStatus", 0);

        orderNumTV = (TextView) findViewById(R.id.detail_orderNum);
        receiverNameTV = (TextView) findViewById(R.id.detail_receiverName);
        receiverPhoneTV = (TextView) findViewById(R.id.detail_receiverPhone);
        receiverAddressTV = (TextView) findViewById(R.id.detail_receiverAddress);
        freightsTV = (TextView) findViewById(R.id.detail_freights);
        orderPriceTV = (TextView) findViewById(R.id.detail_orderPrice);
        memoTV = (TextView) findViewById(R.id.detail_memo);

        ImageButton cancelBtn = (ImageButton) findViewById(R.id.dialogCancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deliveryStatusTv = (TextView) findViewById(R.id.deliveryStatusTv);
        quickCancelBtn = (Button) findViewById(R.id.quickCancelBtn);
        quickCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCallAlert(deliveryStatus);
            }
        });
        chatBotBtn = (Button) findViewById(R.id.quickCancelBtn);
        chatBotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
                startActivity(intent);
            }
        });
        mapBtn = (Button) findViewById(R.id.mapBtn);

        switch (deliveryStatus) {
            case -1:
                deliveryStatusTv.setText("배차실패");
                mapBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorEmerald));
                mapBtn.setText("배차 재요청");
                mapBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reAllocateAlert();
                    }
                });

                break;
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

                break;
            case 4:
                deliveryStatusTv.setText("배송완료");
                chatBotBtn.setEnabled(false);
                quickCancelBtn.setVisibility(View.GONE);
                chatBotBtn.setVisibility(View.GONE);
                mapBtn.setText("닫기");
                mapBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { finish(); }});

                break;
            default:
                deliveryStatusTv.setText("미처리주문");
        }

        orderNumTV.setText(orderNum+"");
        receiverNameTV.setText(receiverName);
        receiverPhoneTV.setText(receiverPhone);
        receiverAddressTV.setText(receiverAddress);
        freightsTV.setText(freights);
        orderPriceTV.setText(orderPrice+"");
        memoTV.setText(memo);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    private void reAllocateAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("배차 재요청");
        alert.setMessage("배차를 재요청 하시겠습니까?");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = mainUrl+"appCall/reRegistCall.do";
                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();
            }
        });

        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        alert.show();
    }

    private void cancelCallAlert(int deliveryStatus){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String msg= null;
        if(deliveryStatus==2) msg = "이미 배차가 되어 취소 요금이 발생합니다.\n정말 취소하시겠습니까?";
        else msg = "정말 취소하시겠습니까?";
        alert.setTitle("콜 취소");
        alert.setMessage(msg);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = mainUrl+"appCall/cancelCall.do";
                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();
            }
        });

        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        alert.show();
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
            if(s.toString().equals("true")) {
                Toast.makeText(getApplicationContext(), "요청에 성공했습니다.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), OrderListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
