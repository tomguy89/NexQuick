package com.balbadak.nexquickpro;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

            }
        });
        chatBotBtn = (Button) findViewById(R.id.quickCancelBtn);
        mapBtn = (Button) findViewById(R.id.mapBtn);

        num = intent.getIntExtra("num", 0);
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        freights = intent.getStringExtra("freights");
        orderPrice = intent.getIntExtra("orderPrice", 0);
        memo = intent.getStringExtra("memo");
        deliveryStatus = intent.getIntExtra("deliveryStatus", 0);


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

}
