package com.balbadak.nexquickapplication;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

public class  LoginActivity extends AppCompatActivity {

    private Context context = this;

    //.173 태진, .164 승진
    private String mainUrl = "http://70.12.109.173:9090/NexQuick/";

    String csId;
    String csPassword;
    String token;
    EditText etLogin;
    EditText etPassword;
    Switch autoSwitch;
    private boolean remember;
    private SharedPreferences loginInfo;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        Button signBtn = (Button) findViewById(R.id.SignBtn);

        etLogin = (EditText)findViewById(R.id.etLogin);
        etPassword = (EditText)findViewById(R.id.etPassword);
        autoSwitch = (Switch)findViewById(R.id.autoSwitch);
        loginInfo = getSharedPreferences("setting", 0);
        editor = loginInfo.edit();
        token = loginInfo.getString("token", "");

        if(loginInfo!=null && loginInfo.getString("rememberId", "")!=null && loginInfo.getString("rememberId", "").length()!=0){
            csId = loginInfo.getString("rememberId", "");
            csPassword = loginInfo.getString("rememberPassword", "");
            signIn();
        }


        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) remember = true;
                else remember = false;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                csId = etLogin.getText().toString().trim();
                csPassword = etPassword.getText().toString().trim();
                if (csId == null || csId.length() == 0) {
                    Toast.makeText(context, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (csPassword == null || csPassword.length() == 0) {
                    Toast.makeText(context, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (remember) {
                    editor.putString("rememberId", csId);
                    editor.putString("rememberPassword", csPassword);
                    editor.commit();
                } else{
                    editor.remove("rememberId");
                    editor.remove("rememberPassword");
                    editor.commit();
                }

                signIn();

            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void signIn(){
        // URL 설정. 173 태진햄 / 164 승진
        String url = mainUrl+"account/csSignIn.do";

        ContentValues values = new ContentValues();
        values.put("csId", csId);
        values.put("csPassword", csPassword);
        values.put("token", token);
        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, values);
        networkTask.execute();

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
            String csId = null;
            String csName = null;
            String csPhone = null;
            int csType = 0;
            super.onPostExecute(s);

            if(s!=null){
                try {
                    JSONObject object = new JSONObject(s);
                    csId = object.getString("csId");
                    csName = object.getString("csName");
                    csPhone = object.getString("csPhone");
                    csType = object.getInt("csType");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(csName != null){
                    Log.e("csId", csId);
                    Log.e("csName", csName);
                    editor.putString("csId", csId);
                    editor.putString("csName", csName);
                    editor.putString("csPhone", csPhone);
                    editor.putInt("csType", csType);
                    editor.commit();
                    Toast.makeText(context, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else{
                    Toast.makeText(context, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
