package com.balbadak.nexquickpro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.maps.helper.Utility.getPackageInfo;

public class  LoginActivity extends AppCompatActivity {

    private Context context = this;
    String qpPhone;
    String qpPassword;
    EditText etLogin;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        Button signBtn = (Button) findViewById(R.id.SignBtn);

        etLogin = (EditText)findViewById(R.id.etLogin);
        etPassword = (EditText)findViewById(R.id.etPassword);


        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                qpPhone = etLogin.getText().toString().trim();
                Log.e("qp다", qpPhone);
                qpPassword = etPassword.getText().toString().trim();
                Log.e("비밀", qpPassword);
                Toast.makeText(context, "여기 왔숑", Toast.LENGTH_SHORT).show();
                Log.e("",qpPhone+qpPassword);
                // URL 설정.
                String url = "http://70.12.109.173:9090/NexQuick/qpAccount/qpSignIn.do";

                ContentValues values = new ContentValues();
                values.put("qpPhone", qpPhone);
                values.put("qpPassword", qpPassword);
                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KeyHashActivity.class);
                startActivity(intent);
            }
        });


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
            if(s!=null){
               Log.e("s값",s);
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }

            //tv_outPut.setText(s);
        }
    }

}
