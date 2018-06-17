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
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;

public class SPayCheckActivity extends AppCompatActivity {

    String mainUrl;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_spay_check);
        mainUrl = getResources().getString(R.string.main_url);

        ImageButton cancelBtn = (ImageButton) findViewById(R.id.payDialogCancelBtn);
        Button yesBtn=findViewById(R.id.yesBtn);
        TextView tv = findViewById(R.id.spayContent);

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
                                           public void onClick(View v) {    //컨트롤러에서 update한다.....

                                               String url = mainUrl + "list/payComplete.do";

                                               ContentValues values = new ContentValues();
                                               values.put("result", gotIntent.getStringExtra("JSONArray"));

                                               NetworkTask networkTask = new NetworkTask(url,values);
                                               networkTask.execute();

                                               Intent i = new Intent(getApplicationContext(), QPBeamSActivity.class);//인수하기 버튼을 눌렀다면...
                                                i.putExtra("callNum",getIntent().getIntExtra("callNum",0));

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
            Log.e("INFO","결제 업데이트 성공!");
        }
    }
}


