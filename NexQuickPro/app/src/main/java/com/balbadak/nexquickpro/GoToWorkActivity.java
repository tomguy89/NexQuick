package com.balbadak.nexquickpro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

public class GoToWorkActivity extends AppCompatActivity {

    Context context = this;
    private SharedPreferences loginInfo;
    String qpPhone;
    String qpName;
    Button workbtn;
    Button exitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_work);
        loginInfo = getSharedPreferences("setting", 0);

        if(loginInfo!=null){
            qpPhone = loginInfo.getString("qpPhone", "");
            qpName = loginInfo.getString("qpName", "");
        }

        TextView greeting = findViewById(R.id.greeting);

        greeting.setText(qpName + "프로님"+"\n안녕하세요?");

        workbtn = findViewById(R.id.workbtn);
        exitbtn = findViewById(R.id.exitbtn);

        if(!runtime_permissions()) {
            enable_buttons();
        }

        /*workbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //출근처리 메소드 넣어주세요.
                Intent i =new Intent(getApplicationContext(),LocationService.class);
                startService(i);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });*/


        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앱종료를 위한 코드

                //나중에 로그아웃, 정산 후 퇴근, 앱 종료 등을 할 때 복사
                Intent i = new Intent(getApplicationContext(),LocationService.class);
                stopService(i);
                //여기까지

                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });


    //은진이 파트 얹어주세용

    }

    private void enable_buttons() {

        workbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //출근처리 메소드 넣어주세요.


                Intent i = new Intent(getApplicationContext(), LocationService.class);
                startService(i);


                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }
}
