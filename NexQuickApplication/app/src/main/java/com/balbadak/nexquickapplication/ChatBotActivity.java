package com.balbadak.nexquickapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.balbadak.nexquickapplication.R;

public class ChatBotActivity extends AppCompatActivity  {

    private String mainUrl = "http://70.12.109.164:9090/NexQuick/";
    private WebView chatBotWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        chatBotWebView = findViewById(R.id.chatBotWebView);

        chatBotWebView.getSettings().setJavaScriptEnabled(true);
        chatBotWebView.loadUrl(mainUrl+"/payTest.jsp");




    }



}
