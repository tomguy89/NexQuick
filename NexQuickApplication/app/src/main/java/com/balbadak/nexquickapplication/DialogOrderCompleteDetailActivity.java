package com.balbadak.nexquickapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balbadak.nexquickapplication.vo.OrderInfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

public class DialogOrderCompleteDetailActivity extends AppCompatActivity {

    private String mainUrl;

    int orderNum;
    int callNum;
    String name;
    String phone;
    String address;
    String freights;
    int orderPrice;
    String memo;
    ArrayList<OrderInfo> orderList;
    Button quickCancelBtn;
    Button chatBotBtn;
    Button mapBtn;
    Context context;


    TextView nameTV;
    TextView phoneTV;
    TextView addressTV;
    TextView freightsTV;
    TextView orderPriceTV;
    TextView memoTV;
    LinearLayout freightsLayout;
    LinearLayout priceLayout;
    LinearLayout memoLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_detail_order_complete);
        mainUrl = getResources().getString(R.string.main_url);
        context = this;

        freightsLayout = (LinearLayout) findViewById(R.id.freights_layout);
        priceLayout = (LinearLayout) findViewById(R.id.price_layout);
        memoLayout = (LinearLayout) findViewById(R.id.memo_layout);
        nameTV = (TextView) findViewById(R.id.detail_name);
        phoneTV = (TextView) findViewById(R.id.detail_phone);
        addressTV = (TextView) findViewById(R.id.detail_address);
        freightsTV = (TextView) findViewById(R.id.detail_freights);
        orderPriceTV = (TextView) findViewById(R.id.detail_orderPrice);
        memoTV = (TextView) findViewById(R.id.detail_memo);


        Intent intent = getIntent();
        orderNum = intent.getIntExtra("orderNum", 0);
        callNum = intent.getIntExtra("callNum", 0);
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");

        nameTV.setText(name);
        phoneTV.setText(phone);
        addressTV.setText(address);

        if(intent.getStringExtra("freights") != null) {
            freights = intent.getStringExtra("freights");
            orderPrice = intent.getIntExtra("orderPrice", 0);
            memo = intent.getStringExtra("memo");

            freightsTV.setText(freights);
            orderPriceTV.setText(orderPrice+"");
            memoTV.setText(memo);

            if (memo == null || memo.equals("null")) {
                memo = "메모 없음";
            }
        } else {
            freightsLayout.setVisibility(View.GONE);
            priceLayout.setVisibility(View.GONE);
            memoLayout.setVisibility(View.GONE);
        }

        ImageButton cancelBtn = (ImageButton) findViewById(R.id.dialogCancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


}
