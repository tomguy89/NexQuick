package com.balbadak.nexquickapplication;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserInfoUpdateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);

    }
    private String mainUrl;

    // UI references.
    private Context context = this;
    private View focusView = null;

    private SharedPreferences loginInfo;
    private String csId;
    private String csName;
    private int csType;

    boolean usableId = false;
    boolean usablePw = false;
    boolean pwPaired = false;

    private EditText mUserIdView;
    private EditText mPasswordView;
    private EditText mPasswordRe;
    private EditText mNameView;
    private EditText mPhoneView;
    private EditText mBNum;
    private EditText mBName;
    private EditText mBDept;
    RadioGroup rg;
    Drawable confirmIcon;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_update);
        mainUrl = getResources().getString(R.string.main_url);
        // Set up the login form.
        mUserIdView = (EditText) findViewById(R.id.userId);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordRe = (EditText) findViewById(R.id.passwordRe);
        mNameView = (EditText) findViewById(R.id.username);
        mPhoneView = (EditText) findViewById(R.id.userPhone);
        mBName = (EditText) findViewById(R.id.businessName);
        mBNum = (EditText) findViewById(R.id.businessNum);
        mBDept = (EditText) findViewById(R.id.businessDept);

        rg = (RadioGroup)findViewById(R.id.radioGroup);
        rg.setEnabled(false);

        loginInfo = getSharedPreferences("setting", 0);
        if (loginInfo != null && loginInfo.getString("csId", "") != null && loginInfo.getString("csId", "").length() != 0) {
            csId = loginInfo.getString("csId", "");
            csName = loginInfo.getString("csName", "");
        }


        String url = mainUrl+"account/app/getCsInfo.do";
        ContentValues values = new ContentValues();
        values.put("csId", csId);
        GetInfoTask getInfoTask =  new GetInfoTask(url, values);
        getInfoTask.execute();

        confirmIcon = getResources().getDrawable(R.drawable.confirm, null);
        confirmIcon.setBounds(0, 0, 50, 50);

        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password = mPasswordView.getText().toString();
                if(!hasFocus){
                    if(password==null || password.length()==0){
                        mPasswordView.setError(null);
                    }else if(password.length()<4){
                        mPasswordView.setError(getString(R.string.error_invalid_password));
                        focusView = mPasswordView;
                        usablePw = false;
                    } else{
                        mPasswordView.setError(getString(R.string.error_valid_password), confirmIcon);
                        usablePw = true;
                    }
                }
            }
        });

        mPasswordRe.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = mPasswordView.getText().toString().trim();
                String passwordRe = mPasswordRe.getText().toString().trim();
                if(passwordRe==null || passwordRe.length()==0){
                    mPasswordRe.setError(null);
                }else if(password.equals(passwordRe)){
                    mPasswordRe.setError(getString(R.string.error_correct_password), confirmIcon);
                    pwPaired = true;
                }else{
                    mPasswordRe.setError(getString(R.string.error_incorrect_password));
                    pwPaired = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });



        // 내비게이션 서랍을 위한 툴바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 내비게이션 서랍 관련 설정
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignUp() {

/*        // Reset errors.
        mUserIdView.setError(null);
        mPasswordView.setError(null);
        mPasswordRe.setError(null);*/
        mNameView.setError(null);
        mPhoneView.setError(null);

        // Store values at the time of the login attempt.
        csId = mUserIdView.getText().toString().trim();
        String csPassword = mPasswordView.getText().toString().trim();
        String csPasswordRe = mPasswordRe.getText().toString().trim();
        String csName = mNameView.getText().toString().trim();
        String csPhone = mPhoneView.getText().toString().trim();
        String bNum = mBNum.getText().toString().trim();
        String bName = mBName.getText().toString().trim();
        String bDept = mBDept.getText().toString().trim();

        focusView = null;

        if(csId == null || csId.length()==0){
            mUserIdView.setError(getString(R.string.error_field_required));
            focusView = mUserIdView;
            usableId = false;
            return;
        }

        if(csPassword == null || csPassword.length()==0){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            usablePw = false;
            return;
        }

        if(csPasswordRe == null || csPasswordRe.length()==0){
            mPasswordRe.setError(getString(R.string.error_field_required));
            focusView = mPasswordRe;
            pwPaired = false;
            return;
        }

        if(csName == null || csName.length()==0){
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            return;
        }

        if(csPhone == null || csPhone.length()==0){
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            return;
        }

        if(csType != 3){
            if(bNum == null || bNum.length()==0){
                mBNum.setError(getString(R.string.error_field_required));
                focusView = mBNum;
                return;
            }
            if(bName == null || bName.length()==0){
                mBName.setError(getString(R.string.error_field_required));
                focusView = mBName;
                return;
            }
        }

        if(csType == 1){
            if(bDept == null || bDept.length()==0){
                mBDept.setError(getString(R.string.error_field_required));
                focusView = mBDept;
                return;
            }
        }

        if (usableId && usablePw && pwPaired) {
            String url = mainUrl +"account/app/csModify.do";
            ContentValues values = new ContentValues();
            values.put("csId", csId);
            values.put("csPassword", csPassword);
            values.put("csName", csName);
            values.put("csPhone", csPhone);
            values.put("csType", csType);
            values.put("csBusinessName", bName);
            values.put("csBusinessNumber", bNum);
            values.put("csDepartment", bDept);
            UserUpdateTask userSignUp = new UserUpdateTask(url, values);
            userSignUp.execute();
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserUpdateTask extends AsyncTask<Void, Void, Boolean> {

        private String url;
        private ContentValues values;

        public UserUpdateTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            String result = requestHttpURLConnection.request(url, values);
            if(result.equals("true")) return true;
            else return false;
        }

        @Override
        protected void onPostExecute(Boolean check) {
            super.onPostExecute(check);

            Toast.makeText(context, "정보수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    public class GetInfoTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        GetInfoTask(String url, ContentValues values) {
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

            if (s != null) {
                Log.e("받아온 것", s);
                try {
                    JSONObject data = new JSONObject(s);

                    mUserIdView.setText(data.getString("csId"));
                    mUserIdView.setEnabled(false);
                    mNameView.setText(data.getString("csName"));
                    mNameView.setEnabled(false);
                    mPhoneView.setText(data.getString("csPhone"));
                    mBNum.setText(data.getString("csBusinessNumber"));
                    mBNum.setEnabled(false);
                    mBName.setText(data.getString("csBusinessName"));
                    mBName.setEnabled(false);
                    mBDept.setText(data.getString("csDepartment"));

                    int csType = data.getInt("csType");
                    switch (csType) {
                        case 1:
                            rg.check(R.id.userBtn1);
                            mBNum.setVisibility(View.VISIBLE);
                            mBName.setVisibility(View.VISIBLE);
                            mBDept.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            rg.check(R.id.userBtn2);
                            mBNum.setVisibility(View.VISIBLE);
                            mBName.setVisibility(View.VISIBLE);
                            mBDept.setVisibility(View.GONE);
                            break;
                        case 3:
                            rg.check(R.id.userBtn3);
                            mBNum.setVisibility(View.GONE);
                            mBName.setVisibility(View.GONE);
                            mBDept.setVisibility(View.GONE);
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //------------------------------여기부터 내비 영역 -----------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_order) {
            Intent intent = new Intent(getApplicationContext(), Order1Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order_list) {
            Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
            startActivity(intent);
        } else if(id == R.id.chatBot) {
            Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
            startActivity(intent);
        } else if(id == R.id.userUpdate) {
            Intent intent = new Intent(getApplicationContext(), UserInfoUpdateActivity.class);
            startActivity(intent);
        } else if(id == R.id.insuindo) {
            Intent intent = new Intent(getApplicationContext(), CSBeamActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout) {
            SharedPreferences.Editor editor = getSharedPreferences("setting", 0).edit();
            editor.clear().commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

