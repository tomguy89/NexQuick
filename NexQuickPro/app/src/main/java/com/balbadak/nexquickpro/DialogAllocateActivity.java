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

public class DialogAllocateActivity extends AppCompatActivity {

    private int callNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_allocate);

        TextView tv = (TextView)findViewById(R.id.quick_allocate_contents);
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        Log.e("tqtq", "tqtq");
        for(int i=message.length()-1; i>=0; i--){
            if(message.charAt(i) == '@'){
                callNum = Integer.parseInt(message.substring(i+1));
                message = message.substring(0, i);
            }
        }
        tv.setText(message);
        ImageButton cancelBtn = (ImageButton) findViewById(R.id.dialogCancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish(); }});

        Button dismissBtn = (Button) findViewById(R.id.quickDismissBtn);
        dismissBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }}));


        Button acceptBtn = (Button) findViewById(R.id.quickAcceptBtn);
        acceptBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://70.12.109.173:9090/NexQuick/appCall/reRegistCall.do";
                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();
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
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            Log.e("asdf", s);
            if(s.equals("true")){
                Toast.makeText(getApplicationContext(), "배차가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}


