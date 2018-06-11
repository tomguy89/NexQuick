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
                                               Intent i1 = new Intent(getApplicationContext(), GoToWorkActivity.class);
                                               startActivity(i1);
                                               finish();
                                           }
                                       }
        );

    }


}


