package com.balbadak.nexquickapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        Button newOrder = (Button) findViewById(R.id.newOrderBtn);
        Button orderList = (Button) findViewById(R.id.orderListBtn);

        newOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewOrderActivity.class);
                startActivity(intent);
            }
        });

        orderList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
                startActivity(intent);
            }
        });
    }
}
