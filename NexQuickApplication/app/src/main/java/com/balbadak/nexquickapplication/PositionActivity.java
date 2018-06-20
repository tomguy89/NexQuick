package com.balbadak.nexquickapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;


public class PositionActivity extends AppCompatActivity  {

    private String mainUrl;
    private WebView positionWebView;
    private int orderNum;
    private int callNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        mainUrl = getResources().getString(R.string.main_url);
        positionWebView = findViewById(R.id.positionWebView);
        Intent intent = getIntent();
        orderNum = intent.getIntExtra("orderNum", 0);
        callNum = intent.getIntExtra("callNum", 0);

        Log.e("orderNum", orderNum+"");
        Log.e("callNum", callNum+"");
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.90); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.67);  //Display 사이즈의 80%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        positionWebView.getSettings().setJavaScriptEnabled(true);
        positionWebView.loadUrl(mainUrl+"app_map.jsp?callNum="+callNum+"&orderNum="+orderNum);

    }

}
