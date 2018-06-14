package com.balbadak.nexquickpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class DialogCalculateEndActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_calculate_end);

        ImageButton cancelBtn = (ImageButton) findViewById(R.id.dialogCancelBtn);
        Button continueBtn = findViewById(R.id.continueBtn);
        Button leaveWorkBtn = findViewById(R.id.leaveWorkBtn);

        Intent gotIntent = getIntent();


        tv = findViewById(R.id.quick_allocate_contents);
        tv.setText(gotIntent.getStringExtra("qpBank")+" "+ gotIntent.getStringExtra("qpAccount")+"로 "+  gotIntent.getIntExtra("money",0)+"원이 입금되었습니다.");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             finish();
                                         }
                                     }
        );

        continueBtn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               finish();
                                           }
                                       }
        );

        leaveWorkBtn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Log.e("INFO","퇴근 버튼을 눌렀습니다...");

                                               Intent i = new Intent(getApplicationContext(),LocationService.class);
                                               stopService(i);
                                               Intent i1 = new Intent(getApplicationContext(), GoToWorkActivity.class);
                                               startActivity(i1);
                                               finish();
                                           }
                                       }
        );

    }


}


