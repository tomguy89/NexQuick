package com.balbadak.nexquickapplication;

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

import com.balbadak.nexquickapplication.vo.OrderInfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//배차 관련 파이어베이스 메시지를 받은 클래스
public class DialogAllocateActivity extends AppCompatActivity {

    private String mainUrl;
    Context context;

    int callNum;
    String req;

    Button alloCancelBtn;
    Button alloRetryBtn;
    Button alloMapBtn;

    TextView alloTitleTv;
    TextView alloStatusTv;
    TextView alloContentsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_allocate);
        mainUrl = getResources().getString(R.string.main_url);
        context = this;


        alloTitleTv = (TextView) findViewById(R.id.alloTitle); // 다이얼로그 제목
        alloStatusTv = (TextView) findViewById(R.id.alloStatus); // 성공실패를 띄우는 tv
        alloContentsTv = (TextView) findViewById(R.id.alloContents); // 상세메시지를 띄우는 tv

        alloCancelBtn = (Button) findViewById(R.id.alloCancelBtn);
        alloRetryBtn = (Button) findViewById(R.id.alloRetryBtn);
        alloMapBtn = (Button) findViewById(R.id.alloMapBtn);

        Intent intent = getIntent();

        String message = intent.getStringExtra("message");
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '번') {
                callNum = Integer.parseInt(message.substring(0, i));
                break;
            }
        }

        if (message.contains("완료")) {
            alloStatusTv.setText("배차 완료");
            alloContentsTv.setText(message); // 배차 알림 관련 설명 넣기
            alloMapBtn.setVisibility(View.VISIBLE); // 기사님 위치 맵 띄움


        } else {
            alloStatusTv.setText("배차 실패");
            alloContentsTv.setText(message); // 배차 알림 관련 설명 넣기
            alloRetryBtn.setVisibility(View.VISIBLE); // 재배차 요청 버튼 띄움
        }


        //"취소를 누르면 콜삭제로 가는 메소드"
        alloCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mainUrl + "appCall/cancelCall.do";
                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                req = "cancelCall";
                // AsyncTask를 통해 HttpURLConnection 수행.
                MainTask mainTask = new MainTask(url, values);
                mainTask.execute();
                finish();
            }
        });


        //"재배차요청" 버튼 클릭시 재배차 요청 쓰레드를 돌리는 메소드
        alloRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = mainUrl + "appCall/reRegistCall.do";
                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                // AsyncTask를 통해 HttpURLConnection 수행.
                req = "reRegistCall";
                MainTask mainTask = new MainTask(url, values);
                mainTask.execute();
                finish();
            }
        });

        //"기사님위치" 버튼 클릭시 돌리는 메소드
        alloMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "맵을 띄웁니당", Toast.LENGTH_SHORT).show();

            }
        });
        ImageButton cancelBtn = (ImageButton) findViewById(R.id.dialogCancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public class MainTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public MainTask(String url, ContentValues values) {

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

            Log.e("받아온 것", s);
            if (s.equals("true")) {
                if(req.equals("cancelCall")) {
                    Toast.makeText(context, "콜 요청이 취소됐습니다.", Toast.LENGTH_SHORT).show();
                }else if(req.equals("reRegistCall")){
                    Toast.makeText(context, "배차를 재요청 했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
