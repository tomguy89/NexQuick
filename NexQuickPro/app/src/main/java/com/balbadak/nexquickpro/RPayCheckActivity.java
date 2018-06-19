package com.balbadak.nexquickpro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class RPayCheckActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_rpay_check);

        ImageButton cancelBtn = (ImageButton) findViewById(R.id.payDialogCancelBtn);
        Button yesBtn=findViewById(R.id.yesBtn);
        TextView tv = findViewById(R.id.rpayContent);
        final Intent gotIntent = getIntent();

        tv.setText( gotIntent.getIntExtra("unpayedCallNumber",0)+"건의 결제를 현장에서 처리하셨습니까?");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             finish();
                                         }
                                     }
        );

        yesBtn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               String url = "http://70.12.109.164:9090/NexQuick/list/payComplete.do";

                                               ContentValues values = new ContentValues();
                                               values.put("result", gotIntent.getStringExtra("JSONArray"));

                                               NetworkTask networkTask=new NetworkTask(url,values);
                                               networkTask.execute();

                                               Intent i = new Intent(getApplicationContext(), QPBeamRActivity.class);//인수하기 버튼을 눌렀다면...
                                                i.putExtra("orderNum",getIntent().getIntExtra("orderNum",0));
                                               startActivity(i);
                                               finish();
                                           }
                                       }
        );


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
        }
    }
}


