package com.balbadak.nexquickapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class DialogAddressActivity extends AppCompatActivity {

    private WebView addressWebView;
    private String mainUrl;

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {

            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_address);
        mainUrl = getResources().getString(R.string.main_url);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.96); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.80);  //Display 사이즈의 80%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        addressWebView = (WebView) findViewById(R.id.addressWebView);
        addressWebView.getSettings().setJavaScriptEnabled(true);
        addressWebView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        addressWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                addressWebView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        //browser.loadUrl("file:///android_asset/daum.html");
        //browser.loadUrl("http://www.daddyface.com/public/daum.html");
        addressWebView.loadUrl(mainUrl+"app_address.jsp");

    }
}