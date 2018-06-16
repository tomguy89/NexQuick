package com.balbadak.nexquickapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.balbadak.nexquickapplication.R;

public class ChatBotActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        Toast.makeText(this, "왔다", Toast.LENGTH_SHORT).show();
    }



}
