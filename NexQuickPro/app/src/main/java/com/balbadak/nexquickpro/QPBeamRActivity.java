package com.balbadak.nexquickpro;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class QPBeamRActivity extends Activity { //nfc를 보내느 activity (xml에 nfc 관련 intent filter가 없어도 된당)


    //qpId 바꾸기

    private static final String TAG = "BeamActivity";

    NfcAdapter mNfcAdapter;
    EditText etMsg;

    PendingIntent pIntent;
    IntentFilter[] mNdefFilters;
    private SharedPreferences loginInfo;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        setContentView(R.layout.activity_qpbeamr);

        Button signBtn= findViewById(R.id.signBtn);
        //얘 setonClickListener해서 사인 받는 창으로 넘기기.

        pIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),KeyActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        mNfcAdapter.enableForegroundDispatch(this, pIntent, mNdefFilters, null);


        mNfcAdapter.setNdefPushMessageCallback(callback, this);
        mNfcAdapter.setOnNdefPushCompleteCallback(completeCallback, this);
    }

    private CreateNdefMessageCallback callback = new CreateNdefMessageCallback() {

        @Override
        public NdefMessage createNdefMessage(NfcEvent event) {//전송합니당
            // TODO Auto-generated method stub
           // loginInfo = getSharedPreferences("setting", 0);  //이걸로 바꿔야한다.
            //String qpId = "R"+loginInfo.getString("qpId", "");//이걸로 바꿔야한다.
            String qpId="R"+"2";

            return makeNdefMessage(qpId);
        }
    };

    private OnNdefPushCompleteCallback completeCallback = new OnNdefPushCompleteCallback() {

        @Override
        public void onNdefPushComplete(NfcEvent event) { //안드로이드 빔 전송성공하고 왔을 때
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(QPBeamRActivity.this, "인수/인도 확인을 요청했습니다.", Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }


    private NdefMessage makeNdefMessage(String msg) {

        byte[] textBytes = msg.getBytes();
        NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(), new byte[] {}, textBytes);
        return new NdefMessage(new NdefRecord[] { textRecord });
    }

}