package com.balbadak.nexquickpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    //여기부터
        if(gotIntent.getStringExtra("qpAcount") != null) {
            tv.setText(gotIntent.getStringExtra("qpBank") + " " + gotIntent.getStringExtra("qpAccount") + "로 " + gotIntent.getIntExtra("money", 0) + "원이 입금되었습니다.");
        } else {
            tv.setText("보증금이 차감되었습니다. 현재 보증금은" +gotIntent.getIntExtra("money", 0) + "원입니다.");
        }
        //여기까지
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

                                               Intent i = new Intent(getApplicationContext(),LocationService.class);
                                               stopService(i);
                                               Intent i1 = new Intent(getApplicationContext(), GoToWorkActivity.class);
                                               i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                               startActivity(i1);
                                               finish();
                                           }
                                       }
        );

    }


}


