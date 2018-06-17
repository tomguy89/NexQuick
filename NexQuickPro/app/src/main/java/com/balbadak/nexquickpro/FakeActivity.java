package com.balbadak.nexquickpro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class FakeActivity extends AppCompatActivity {

    String mainUrl;
    int callNum;
    int orderNum;
    int qpId;
    SharedPreferences loginInfo;
    Context ctx=this;
    //얘네는 가져올 수 있다고 치고....

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake);
        mainUrl = getResources().getString(R.string.main_url);

        Button insuBtn = findViewById(R.id.insuBtn);
        Button indoBtn = findViewById(R.id.indoBtn);

        //일단 callNum과 orderNum과 qpId는 박아두고 시작한다. 나중에 합칠 때...!
        callNum=4;
        orderNum=3;
//        loginInfo=getSharedPreferences("setting",0);
//        qpId=loginInfo.getInt("qpId",0);
        qpId=2;

        insuBtn.setOnClickListener(new View.OnClickListener() {//인수 버튼을 누르면
            @Override
            public void onClick(View v) {
                String url = mainUrl + "list/afterBeamforQPS.do";

                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                values.put("qpId", qpId);

                SNetworkTask networkTask = new SNetworkTask(url,values);
                networkTask.execute();

            }
        });


        indoBtn.setOnClickListener(new View.OnClickListener() {//인도 버튼을 누르면...
            @Override
            public void onClick(View v) {
                String url = mainUrl + "list/afterBeamforQPR.do";

                ContentValues values = new ContentValues();
                values.put("orderNum", orderNum);
                values.put("qpId", qpId);

                RNetworkTask networkTask = new RNetworkTask(url,values);
                networkTask.execute();
            }
        });

    }

    public class SNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public SNetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.e("INFO","SNETWORKTASK doInBackground에 들어왔어여");

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

            Log.e("INFO","onPostExecute에 온 파라매터는"+s);

            if(s!=null){

                try {
                    JSONArray ja = new JSONArray(s);
                    Log.e("INFO","jsonArray는 "+ja.toString());

                    if(ja.length()>0){//뭔가 리스트가 왔다면
                        Intent i1 = new Intent(ctx,SPayCheckActivity.class); //결제확인창으로 보낸다.
                        i1.putExtra("unpayedCallNumber",ja.length());
                        i1.putExtra("JSONArray",ja.toString());
                        startActivity(i1);
                    } else {
                        Intent i2 =new Intent(ctx,QPBeamSActivity.class);//바로 nfc태깅창으로 보낸다.
                        startActivity(i2);
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {//온 리스트가 없다면..?
                Toast.makeText(ctx,"서버 전송 중 오류가 생겼습니다.",Toast.LENGTH_SHORT); //이렇게 나오는지 확인하기...

            }
        }
    }




    public class RNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public RNetworkTask(String url, ContentValues values) {

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

            Log.e("INFO","onPostExecute에 온 파라매터는"+s);

            if(s!=null){

                try {
                    JSONArray ja = new JSONArray(s);
                    Log.e("INFO","jsonArray는 "+ja.toString());

                    if(ja.length()>0){//뭔가 리스트가 왔다면
                        Intent i1 = new Intent(ctx,RPayCheckActivity.class); //결제확인창으로 보낸다.
                        i1.putExtra("unpayedCallNumber",ja.length());
                        i1.putExtra("JSONArray",ja.toString());
                        startActivity(i1);
                    } else {
                        Intent i2 =new Intent(ctx,QPBeamRActivity.class);//바로 nfc태깅창으로 보낸다.
                        startActivity(i2);
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {//온 리스트가 없다면..?
                Toast.makeText(ctx,"서버 전송 중 오류가 생겼습니다.",Toast.LENGTH_SHORT); //이렇게 나오는지 확인하기...

            }
        }
    }
}

