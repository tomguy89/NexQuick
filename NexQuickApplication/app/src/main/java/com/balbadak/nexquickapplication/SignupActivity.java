package com.balbadak.nexquickapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);

    }

    private String mainUrl;

    // UI references.
    private Context context = this;
    private View focusView = null;

    boolean usableId = false;
    boolean usablePw = false;
    boolean pwPaired = false;

    private AutoCompleteTextView mUserIdView;
    private EditText mPasswordView;
    private EditText mPasswordRe;
    private EditText mNameView;
    private EditText mPhoneView;
    private EditText mBNum;
    private EditText mBName;
    private EditText mBDept;

    Drawable confirmIcon;

    private String csId;
    private int csType;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mainUrl = getResources().getString(R.string.main_url);
        // Set up the login form.
        mUserIdView = (AutoCompleteTextView) findViewById(R.id.userId);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordRe = (EditText) findViewById(R.id.passwordRe);
        mNameView = (EditText) findViewById(R.id.username);
        mPhoneView = (EditText) findViewById(R.id.userPhone);
        mBName = (EditText) findViewById(R.id.businessName);
        mBNum = (EditText) findViewById(R.id.businessNum);
        mBDept = (EditText) findViewById(R.id.businessDept);

        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        rg.check(R.id.userBtn1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.userBtn1:
                        csType = 1;
                        mBNum.setVisibility(View.VISIBLE);
                        mBName.setVisibility(View.VISIBLE);
                        mBDept.setVisibility(View.VISIBLE);
                        break;
                    case R.id.userBtn2:
                        csType = 2;
                        mBNum.setVisibility(View.VISIBLE);
                        mBName.setVisibility(View.VISIBLE);
                        mBDept.setVisibility(View.GONE);
                        break;
                    case R.id.userBtn3:
                        csType = 3;
                        mBNum.setVisibility(View.GONE);
                        mBName.setVisibility(View.GONE);
                        mBDept.setVisibility(View.GONE);
                        break;
                }
            }
        });


        confirmIcon = getResources().getDrawable(R.drawable.confirm, null);
        confirmIcon.setBounds(0, 0, 50, 50);

        mUserIdView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                csId = mUserIdView.getText().toString().trim();
                if(!hasFocus){
                    if(csId == null || csId.length()==0) {
                        mUserIdView.setError(null);
                    }else{
                        String url = mainUrl+"account/csIdDuplCheck.do";
                        ContentValues values = new ContentValues();
                        values.put("csId", csId);
                        UserSignUpTask duplCheckTask = new UserSignUpTask(url, values);
                        duplCheckTask.execute();
                    }
                }
            }
        });

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
            String url = mainUrl+"account/csSignUp.do";
            ContentValues values = new ContentValues();
            values.put("csId", csId);
            values.put("csPassword", csPassword);
            values.put("csName", csName);
            values.put("csPhone", csPhone);
            values.put("csType", csType);
            values.put("csBusinessName", bName);
            values.put("csBusinessNumber", bNum);
            values.put("csDepartment", bDept);
            UserSignUpTask userSignUp = new UserSignUpTask(url, values);
            userSignUp.execute();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SignupActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mUserIdView.setAdapter(adapter);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserSignUpTask extends AsyncTask<Void, Void, Boolean> {

        private String url;
        private ContentValues values;

        public UserSignUpTask(String url, ContentValues values) {
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
            if(url.substring(43, url.length()).equals("csIdDuplCheck.do")){
                if(!check){
                    mUserIdView.setError(getString(R.string.error_invalid_csId));
                    focusView = mUserIdView;
                    usableId = false;
                }else{
                    //mUserIdView.setError(getString(R.string.error_valid_csId));
                    mUserIdView.setError(getString(R.string.error_valid_csId), confirmIcon);
                    //mUserIdView.setError(null);
                    usableId = true;
                }
            } else if(url.substring(43, url.length()).equals("csSignUp.do")){
                Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}

