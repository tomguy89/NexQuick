package com.balbadak.nexquickpro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        TextView tv = (TextView)findViewById(R.id.quick_detail_contents);
        Intent intent = getIntent();

        String message = intent.getStringExtra("message");
        tv.setText(message);
        ImageButton cancelBtn = (ImageButton) findViewById(R.id.dialogCancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                                         }
                                     }


        );


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}


