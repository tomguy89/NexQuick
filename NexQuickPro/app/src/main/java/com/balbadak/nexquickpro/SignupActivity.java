package com.balbadak.nexquickpro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);

    }


    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    Context context = this;

    // UI references.
    private EditText mUserNameView;
    private AutoCompleteTextView mUserPhoneView;
    private EditText mPasswordView;
    private EditText mPasswordRe;
    private EditText mUserBankView;
    private EditText mUserAccountView;

    Drawable confirmIcon;
    private View focusView = null;

    boolean usablePhone = false;
    boolean usablePw = false;
    boolean pwPaired = false;

    private String qpPhone;
    private int vehicleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Set up the login form.
        mUserNameView = (EditText) findViewById(R.id.username);
        mUserPhoneView = (AutoCompleteTextView) findViewById(R.id.userPhone);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordRe = (EditText) findViewById(R.id.passwordRe);
        mUserBankView = (EditText) findViewById(R.id.account_bank);
        mUserAccountView = (EditText) findViewById(R.id.account);
        confirmIcon = getResources().getDrawable(R.drawable.confirm, null);
        confirmIcon.setBounds(0, 0, 50, 50);

        RadioGroup rg = (RadioGroup)findViewById(R.id.vehicleType);
        rg.check(R.id.vehicleBtn1);
        vehicleType = 1;
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.vehicleBtn1:
                        vehicleType = 1;
                        break;
                    case R.id.vehicleBtn2:
                        vehicleType = 2;
                        break;
                    case R.id.vehicleBtn3:
                        vehicleType = 3;
                        break;
                    case R.id.vehicleBtn4:
                        vehicleType = 4;
                        break;
                }
            }
        });

        mUserPhoneView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                qpPhone = mUserPhoneView.getText().toString().trim();
                if(!hasFocus){
                    if(qpPhone == null || qpPhone.length()==0) {
                        mUserPhoneView.setError(null);
                    }else{
                        String url = "http://70.12.109.173:9090/NexQuick/qpAccount/qpPhoneDuplCheck.do";
                        ContentValues values = new ContentValues();
                        values.put("qpPhone", qpPhone);
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

        // Reset errors.
        mUserNameView.setError(null);
        mUserBankView.setError(null);
        mUserAccountView.setError(null);

        // Store values at the time of the login attempt.
        qpPhone = mUserPhoneView.getText().toString();
        String qpPassword = mPasswordView.getText().toString();
        String qpPasswordRe = mPasswordRe.getText().toString();
        String qpName = mUserNameView.getText().toString();
        String qpBank = mUserBankView.getText().toString();
        String qpAccount = mUserAccountView.getText().toString();

        focusView = null;

        if(qpName == null || qpName.length()==0){
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            return;
        }

        if(qpPhone == null || qpPhone.length()==0){
            mUserPhoneView.setError(getString(R.string.error_field_required));
            focusView = mUserPhoneView;
            usablePhone = false;
            return;
        }

        if(qpPassword == null || qpPassword.length()==0){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            usablePw = false;
            return;
        }

        if(qpPasswordRe == null || qpPasswordRe.length()==0){
            mPasswordRe.setError(getString(R.string.error_field_required));
            focusView = mPasswordRe;
            pwPaired = false;
            return;
        }

        if(qpBank == null || qpBank.length()==0){
            mUserBankView.setError(getString(R.string.error_field_required));
            focusView = mUserBankView;
            return;
        }

        if(qpAccount == null || qpAccount.length()==0){
            mUserAccountView.setError(getString(R.string.error_field_required));
            focusView = mUserAccountView;
            return;
        }

        if (usablePhone && usablePw && pwPaired) {
            String url = "http://70.12.109.173:9090/NexQuick/qpAccount/qpSignUp.do";
            ContentValues values = new ContentValues();
            values.put("qpPhone", qpPhone);
            values.put("qpPassword", qpPassword);
            values.put("qpName", qpName);
            values.put("vehicleType", vehicleType);
            values.put("qpBank", qpBank);
            values.put("qpAccount", qpAccount);
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

        mUserPhoneView.setAdapter(adapter);
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

        UserSignUpTask(String url, ContentValues values)  {
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
            Log.e("check", check.toString());
            if(url.substring(45, url.length()).equals("qpPhoneDuplCheck.do")){
                if(!check){
                    mUserPhoneView.setError(getString(R.string.error_invalid_phone));
                    focusView = mUserPhoneView;
                    usablePhone = false;
                }else{
                    //mUserIdView.setError(getString(R.string.error_valid_csId));
                    mUserPhoneView.setError(getString(R.string.error_valid_phone), confirmIcon);
                    //mUserIdView.setError(null);
                    usablePhone = true;
                }
            } else if(url.substring(45, url.length()).equals("qpSignUp.do")){
                Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}

