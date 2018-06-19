package com.balbadak.nexquickpro;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.balbadak.nexquickpro.vo.OrderInfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

public class DialogDetailActivity extends AppCompatActivity {

    private String mainUrl;

    int callNum;
    int num;
    String name;
    String phone;
    String address;
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
    TextView numLTV;
    TextView nameLTV;
    TextView numTV;
    TextView nameTV;
    TextView phoneTV;
    TextView addressTV;
    TextView freightsTV;
    TextView orderPriceTV;
    TextView memoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_detail);
        mainUrl = getResources().getString(R.string.main_url);
        context = this;
        orderList = new ArrayList<>();
        Intent intent = getIntent();

        numLTV = (TextView) findViewById(R.id.detail_numTV);
        nameLTV = (TextView) findViewById(R.id.detail_nameTV);
        numTV = (TextView) findViewById(R.id.detail_num);
        nameTV = (TextView) findViewById(R.id.detail_name);
        phoneTV = (TextView) findViewById(R.id.detail_phone);
        addressTV = (TextView) findViewById(R.id.detail_address);
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
                cancelCallAlert();
            }
        });
        chatBotBtn = (Button) findViewById(R.id.chatBotBtn);
        mapBtn = (Button) findViewById(R.id.mapBtn);
        callNum = intent.getIntExtra("callNum", 0);
        num = intent.getIntExtra("num", 0);
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        freights = intent.getStringExtra("freights");
        orderPrice = intent.getIntExtra("orderPrice", 0);
        memo = intent.getStringExtra("memo");
        deliveryStatus = intent.getIntExtra("deliveryStatus", 0);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+phone)));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        chatBotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
                startActivity(intent);
            }
        });

        switch (deliveryStatus) {
            case 2:
                numLTV.setText("콜번호");
                nameLTV.setText("발송인");
                deliveryStatusTv.setText("배차완료");
                break;
            case 3:
                numLTV.setText("오더번호");
                nameLTV.setText("수령인");
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
            case 5:
                deliveryStatusTv.setText("정산완료");
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

        numTV.setText(num+"");
        nameTV.setText(name);
        phoneTV.setText(phone);
        addressTV.setText(address);
        freightsTV.setText(freights);
        orderPriceTV.setText(orderPrice+"");
        memoTV.setText(memo);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private void cancelCallAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String msg= "취소 수수료가 부과됩니다.\n정말 취소하시겠습니까?";
        alert.setTitle("콜 취소");
        alert.setMessage(msg);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = mainUrl+"call/reRegistCall.do";
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
                Toast.makeText(getApplicationContext(), "배차가 취소됐습니다.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        }
    }
}
