package com.balbadak.nexquickpro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

public class DialogAllocateActivity extends AppCompatActivity {

    int callNum = 0;
    int qpId = 0;
    private SharedPreferences loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_allocate);

        TextView tv = (TextView)findViewById(R.id.quick_allocate_contents);
        Intent intent = getIntent();

        String message = intent.getStringExtra("message");

        for(int i=message.length()-1; i>=0; i--){
            if(message.charAt(i) == '@'){
                callNum = Integer.parseInt(message.substring(i+1));
                message = message.substring(0, i);
                break;
            }
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(message);
        for(int i=0; i<ssb.length(); i++){
            switch(ssb.charAt(i)){
                case '급':
                    ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTomato)), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Log.e("확인했다", "급");
                    break;
                case '픽':
                    ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGold)), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Log.e("확인했다", "착");
                    break;
                case '착':
                    ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorEmerald)), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Log.e("확인했다", "픽");
                    break;
            }
        }


        loginInfo = getSharedPreferences("setting", 0);
        qpId = loginInfo.getInt("qpId", 0);
        Log.e("callNum", callNum+"");

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
	            // TODO Auto-generated method stub
                    finish();
	            }
	        }, 10000);

        tv.setText(ssb);
        ImageButton cancelBtn = (ImageButton) findViewById(R.id.dialogCancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish(); }});

        Button dismissBtn = (Button) findViewById(R.id.quickDismissBtn);
        dismissBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish(); }}));


        Button acceptBtn = (Button) findViewById(R.id.quickAcceptBtn);
        acceptBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://70.12.109.164:9090/NexQuick/appCall/completeAllocate.do";
                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                values.put("qpId", qpId);
                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();
                finish();
            }
        }));

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
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
            Log.e("결과", s);
            Toast.makeText(getApplicationContext(), "배차가 완료됐습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}


