package com.balbadak.nexquickpro;


import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private static final int PICK_PICTURE_FROM_GALLERY = 1;
    private static final int PICK_LICENSE_FROM_GALLERY = 2;
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


    Button picture_button;
    String filePath;
    boolean pictureCheck=false;

    Button license_button;
    String filePath2;
    boolean licenseCheck=false;

    String lineEnd = "\r\n";

    String qpId;

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
                        String url = "http://70.12.109.164:9090/NexQuick/qpAccount/qpPhoneDuplCheck.do";
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



        ////0613 이은진 추가파트

        picture_button=findViewById(R.id.picture_button);
        picture_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (ActivityCompat.checkSelfPermission(SignupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_PICTURE_FROM_GALLERY);
                    } else {

                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                       galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, PICK_PICTURE_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        license_button = findViewById(R.id.license_button);
        license_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(SignupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_PICTURE_FROM_GALLERY);
                    } else {

                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, PICK_LICENSE_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });







    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_PICTURE_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, PICK_PICTURE_FROM_GALLERY);

                } else {
                    Toast.makeText(this,"갤러리 권한을 허용해주세요.",Toast.LENGTH_SHORT).show();
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery

                }
                break;


            case PICK_LICENSE_FROM_GALLERY:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, PICK_LICENSE_FROM_GALLERY);

                } else {
                    Toast.makeText(this,"갤러리 권한을 허용해주세요.",Toast.LENGTH_SHORT).show();
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery

                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("INFO","onActivityResult에 들어옴");

        if (requestCode == PICK_PICTURE_FROM_GALLERY)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                filePath = getPath(selectedImage); //Cursor 이용해서
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                Log.e("filePath : ",filePath);


                if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                    //FINE
                    Log.e("INFO","적절한 형식의 파일이 왔음");
                    pictureCheck=true;
                    Toast.makeText(this,"프로필 사진이 선택되었습니다.",Toast.LENGTH_SHORT).show();
                    //upload(); //나중에 이건 회원가입 버튼 눌렀을 때로 바꾸기..

                } else {
                    //NOT IN REQUIRED FORMAT
                    Toast.makeText(this,"올바른 파일 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                    pictureCheck=false;
                }

            }

        if (requestCode == PICK_LICENSE_FROM_GALLERY)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                filePath2 = getPath(selectedImage); //Cursor 이용해서
                String file_extn = filePath2.substring(filePath2.lastIndexOf(".") + 1);
                Log.e("filePath2 : ",filePath2);


                if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                    //FINE
                    Log.e("INFO","적절한 형식의 파일이 왔음");
                    licenseCheck=true;
                    Toast.makeText(this,"면허증이 선택되었습니다.",Toast.LENGTH_SHORT).show();
                    //upload(); //나중에 이건 회원가입 버튼 눌렀을 때로 바꾸기..

                } else {
                    //NOT IN REQUIRED FORMAT
                    Toast.makeText(this,"올바른 파일 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                    licenseCheck=false;
                }

            }


    }

    void upload(){


        PictureBackThread bt = new PictureBackThread();
        bt.start();
    }

    void upload2(){
    LicenseBackThread lt = new LicenseBackThread();
    lt.start();

    }


    class PictureBackThread extends Thread{
        @Override
        public void run() {
            super.run();

            Log.e("INFO","프사 쓰레드 런...");

            try{
                FileInputStream fis =new FileInputStream(filePath);
                URL url=new URL("http://70.12.109.173:9090/NexQuick/qpAccount/uploadPicture.do");//나중에 태진오빠 주소로 바꾸기!
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                //웹서버를 통해 입출력 가능하도록 설정
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);//캐쉬 사용하지 않음
                conn.setRequestMethod("POST");
                //정해진 시간 내에 재접속할 경우 소켓을 새로 생성하지 않고 기존연결 사용
                //대소문자 주의
                conn.setRequestProperty("Connection", "Keep-Alive");
                //첨부파일에 대한 정보
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=files");

                //데이터 아웃풋 스트림
                DataOutputStream dos =new DataOutputStream(conn.getOutputStream());
                //form-data;name=파일변수명;filename="첨부파일이름"
                //작은따옴표를 사용할 수 없음
                dos.writeBytes("--files\r\n"); // --은 파일 시작 알림 표시
                dos.writeBytes("Content-Disposition: form-data; name=\"file1\"; filename=\""
                        +filePath+"\""+"\r\n");

                dos.writeBytes("\r\n");//줄바꿈 문자
                int bytes=fis.available();
                int maxBufferSize=1024;
                //Math.min(A, B)둘중 작은값;
                int bufferSize =Math.min(bytes, maxBufferSize);
                byte[] buffer=new byte[bufferSize];
                int read=fis.read(buffer, 0, bufferSize);
                while(read >0){
                    //서버에 업로드
                    dos.write(buffer,0, bufferSize);
                    bytes=fis.available();
                    bufferSize=Math.min(bytes, maxBufferSize);
                    //읽은 바이트 수
                    read=fis.read(buffer, 0, bufferSize);
                }
                              dos.writeBytes("\r\n");//줄바꿈 문자

           /*boundary=경계문자 => 경계문자의 이름
        --경계문자 => 첨부파일 전송 시작부분
        --경계문자--      => 첨부파일 전송 끝부분*/


                ////여기에 파라매터를 추가하는 것 추가추가
                //dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("--files\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"StringParameter1\"" + lineEnd);
                dos.writeBytes(lineEnd); dos.writeBytes(qpPhone + lineEnd);
                ///여기까지 파라매터 추가하는 것



                dos.writeBytes("--files--\r\n");
                fis.close();//스트림 닫기
                dos.flush();//버퍼 클리어
                dos.close();//출력 스트림 닫기


                //서버의 응답을 처리
                int ch;
                InputStream is=conn.getInputStream(); //입력스트림
                StringBuffer sb=new StringBuffer();
                while( (ch=is.read()) != -1){ // 내용이 없을 때까지 반복
                    sb.append((char)ch); // 문자를 읽어서 저장
                }
                // 스트링.trim() 스트링의 좌우 공백 제거
                String str = sb.toString().trim();
                if(str.equals("success")||str.equals("OK")){
                    Log.e("INFO","프사 업로드 성공");
                }else if(str.equals("fail")){
                    Log.e("INFO","프사 업로드 실패");
               }
//안드로이드에서는 백그라운드 스레드에서 메인UI를 터치할 수 없음
// runOnUiThread()를 사용하면 백그라운드 스레드에서
//   메인UI를 직접 수정할 수 있음

                is.close();
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }


    class LicenseBackThread extends Thread{
        @Override
        public void run() {
            super.run();

            Log.e("INFO","면허 쓰레드 런...");

            try{
                FileInputStream fis =new FileInputStream(filePath2);
                URL url=new URL("http://70.12.109.173:9090/NexQuick/qpAccount/uploadLicense.do");//나중에 태진오빠 주소로 바꾸기!
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                //웹서버를 통해 입출력 가능하도록 설정
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);//캐쉬 사용하지 않음
                conn.setRequestMethod("POST");
                //정해진 시간 내에 재접속할 경우 소켓을 새로 생성하지 않고 기존연결 사용
                //대소문자 주의
                conn.setRequestProperty("Connection", "Keep-Alive");
                //첨부파일에 대한 정보
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=files");

                //데이터 아웃풋 스트림
                DataOutputStream dos =new DataOutputStream(conn.getOutputStream());
                //form-data;name=파일변수명;filename="첨부파일이름"
                //작은따옴표를 사용할 수 없음
                dos.writeBytes("--files\r\n"); // --은 파일 시작 알림 표시
                dos.writeBytes("Content-Disposition: form-data; name=\"file1\"; filename=\""
                        +filePath2+"\""+"\r\n");

                dos.writeBytes("\r\n");//줄바꿈 문자  line End
                int bytes=fis.available();
                int maxBufferSize=1024;
                //Math.min(A, B)둘중 작은값;
                int bufferSize =Math.min(bytes, maxBufferSize);
                byte[] buffer=new byte[bufferSize];
                int read=fis.read(buffer, 0, bufferSize);
                while(read >0){
                    //서버에 업로드
                    dos.write(buffer,0, bufferSize);
                    bytes=fis.available();
                    bufferSize=Math.min(bytes, maxBufferSize);
                    //읽은 바이트 수
                    read=fis.read(buffer, 0, bufferSize);
                }
                dos.writeBytes("\r\n");//줄바꿈 문자

           /*boundary=경계문자 => 경계문자의 이름
        --경계문자 => 첨부파일 전송 시작부분
        --경계문자--      => 첨부파일 전송 끝부분*/

           ////여기에 파라매터를 추가하는 것 추가추가
                //dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("--files\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"StringParameter1\"" + lineEnd);
                dos.writeBytes(lineEnd); dos.writeBytes(qpPhone + lineEnd);
            ///여기까지 파라매터 추가하는 것

                dos.writeBytes("--files--\r\n");
                fis.close();//스트림 닫기
                dos.flush();//버퍼 클리어
                dos.close();//출력 스트림 닫기


                //서버의 응답을 처리
                int ch;
                InputStream is=conn.getInputStream(); //입력스트림
                StringBuffer sb=new StringBuffer();
                while( (ch=is.read()) != -1){ // 내용이 없을 때까지 반복
                    sb.append((char)ch); // 문자를 읽어서 저장
                }
                // 스트링.trim() 스트링의 좌우 공백 제거
                String str = sb.toString().trim();
                if(str.equals("success")||str.equals("OK")){
                    Log.e("INFO","면허 업로드 성공");
                }else if(str.equals("fail")){
                    Log.e("INFO","면허 업로드 실패패");
                }
//안드로이드에서는 백그라운드 스레드에서 메인UI를 터치할 수 없음
// runOnUiThread()를 사용하면 백그라운드 스레드에서
//   메인UI를 직접 수정할 수 있음

                is.close();
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
       int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        Log.e("imagePath : ",imagePath);

        return cursor.getString(column_index);


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


        if(!licenseCheck){
            Toast.makeText(this,"면허 등록증을 등록하셔야 합니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pictureCheck){
            Toast.makeText(this,"프로필 사진을 등록하셔야 합니다.",Toast.LENGTH_SHORT).show();
            return;
        }



        if (usablePhone && usablePw && pwPaired) {
            String url = "http://70.12.109.164:9090/NexQuick/qpAccount/qpSignUp.do"; //이것도 태진오빠껄로 바꾸기
            ContentValues values = new ContentValues();
            values.put("qpPhone", qpPhone);
            values.put("qpPassword", qpPassword);
            values.put("qpName", qpName);
            values.put("vehicleType", vehicleType);
            values.put("qpBank", qpBank);
            values.put("qpAccount", qpAccount);
            UserSignUpTask userSignUp = new UserSignUpTask(url, values);  //여기서 가입함....
            userSignUp.execute();

            upload(); //사진 업로드
            upload2();//면허 업로드
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
            if(url.substring(45, url.length()).equals("qpPhoneDuplCheck.do")){//중복체크하러 간 애
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
            } else if(url.substring(45, url.length()).equals("qpSignUp.do")){//가입하러 간 애
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

