package com.balbadak.nexquickapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MainMenuActivity extends AppCompatActivity {


    Context context;
    private SharedPreferences loginInfo;
    private String csId;
    private String csName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;


        Button orderList = (Button) findViewById(R.id.orderListBtn);
        Button newOrder = (Button) findViewById(R.id.newOrderBtn);
        Button tapAndConfirm = (Button) findViewById(R.id.tapAndConfirmBtn);

        TextView greeting = (TextView) findViewById(R.id.greeting);

        loginInfo = getSharedPreferences("setting", 0);
        if (loginInfo != null && loginInfo.getString("csId", "") != null && loginInfo.getString("csId", "").length() != 0) {
            csId = loginInfo.getString("csId", "");
            csName = loginInfo.getString("csName", "");
            //폰은
        }

        String temp = csName + "님 안녕하세요! :)";
        greeting.setText(temp);

        orderList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
                startActivity(intent);
            }
        });

        newOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Order1Activity.class);
                startActivity(intent);
            }
        });


        tapAndConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CSBeamActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
