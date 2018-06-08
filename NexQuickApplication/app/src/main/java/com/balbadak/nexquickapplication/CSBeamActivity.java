package com.balbadak.nexquickapplication;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CSBeamActivity extends AppCompatActivity {

    private static final String TAG = "BeamActivity";

    private boolean mWriteMode = false;
    NfcAdapter mNfcAdapter;
   // EditText etMsg;
    TextView tv;
    PendingIntent pIntent;
    IntentFilter[] mNdefFilters;

    Context context=this;
    JSONArray newJarray;


    private SharedPreferences loginInfo;
    private String csId;
    private String csPhone;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        setContentView(R.layout.activity_csbeam);
        tv=findViewById(R.id.csbeamtv);

        // Handle all of our received NFC intents in this activity.
        pIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Intent filters for reading a note from a tag or exchanging over p2p.
        IntentFilter ndefFilter = new IntentFilter(
                NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefFilter.addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
        mNdefFilters = new IntentFilter[] { ndefFilter };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(this, pIntent, mNdefFilters, null);

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {//얘는 뭐다?
            NdefMessage[] messages = getNdefMessages(getIntent());
            byte[] payload = messages[0].getRecords()[0].getPayload();
            setIntent(new Intent()); // Consume this intent.
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {

            NdefMessage[] msgs = getNdefMessages(intent);

           String preqpId = new String(msgs[0].getRecords()[0].getPayload());
           String qpId=preqpId.substring(1);


            loginInfo = getSharedPreferences("setting", 0);
            if (loginInfo != null && loginInfo.getString("csId", "") != null && loginInfo.getString("csId", "").length() != 0) {
                csId = loginInfo.getString("csId", "");
                csPhone = loginInfo.getString("csPhone", "");
            }



            if(preqpId.charAt(0)=='S'){//인수하기 버튼을 눌러 온 애라면
               csId = loginInfo.getString("csId", "");

                checkCallInfo(qpId,csId);

            } else if(preqpId.charAt(0)=='R'){//인도하기 버튼을 누르고 온 애라면
               csPhone = loginInfo.getString("csPhone", "");


               checkOrderInfo(qpId,csPhone);
            }



        }
    }

 protected void checkCallInfo(final String qpId, final String csId){

        String url = "http://70.12.109.173:9090/NexQuick/list/confirmCall.do";

        Log.e("INFO","callInfo에 있는지 체크하러 들어왔다"+qpId+"/"+csId);


        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,  new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        String newResponse =  " {"+"list"+":"+response+"}";
                        Log.e("INFO!!!",newResponse);


                        try {
                            final JSONArray jarray = new JSONObject(newResponse).getJSONArray("list");

                            if(jarray.length()<1){
                                tv.setText("배정된 기사님이 아닙니다!");
                            } else {//올바른 만남이라면 다이올로그를 띄운다. 다이올로그에서 YES를 누르면 배송상태를 업데이트 한다.


                                new AlertDialog.Builder(CSBeamActivity.this)
                                        .setTitle("퀵프로님께서 "+jarray.length()+"개의 주문 건을 인수하셨습니까?")
                                        .setPositiveButton("Yes",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        tv.setText("인수가 완료되었습니다!");



                                                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST,
                                                             "http://70.12.109.173:9090/NexQuick/list/updateCallAfterConfirm.do",
                                                                    new Response.Listener<String>() {
                                                                        @Override
                                                                        public void onResponse(String response) {
                                                                            Log.e("INFO","배송상태 업데이트 성공");
                                                                        }
                                                                    },
                                                                    new Response.ErrorListener() {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            Log.e("INFO","배송상태 업데이트 실패");
                                                                        }
                                                                    }){
                                                                @Override
                                                                protected Map<String, String> getParams(){
                                                                            Log.e("INFO","두번째 getParams호출");

                                                                            HashMap<String ,String> params=new HashMap<String, String>();
                                                                            JSONArray jar2 = new JSONArray();
                                                                            try {
                                                                                for(int i=0;i<jarray.length();i++){
                                                                                    JSONObject jsonObject=new JSONObject();
                                                                                    jsonObject.put("callNum",jarray.getJSONObject(i).getString("callNum"));
                                                                                    jar2.put(i,jsonObject);
                                                                                }

                                                                    } catch (JSONException e){
                                                                        tv.setText("처리 중 문제가 발생했습니다..");
                                                                    }
                                                                    params.put("params",jar2.toString());

                                                                    return params;
                                                                }
                                                            };
                                                        CustomStartApp.getInstance().addToRequestQueue(stringRequest2);


                                                    }//on click 메소드
                                                }//new Diolog
                                                )//positive버튼 선택
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                tv.setText("인수를 하지 않으셨습니다.");
                                            }
                                        }).show();

                            }


                        }catch (JSONException e){
                            Log.e("INFO","JsonException으로 떨어짐");
                            tv.setText("처리 중 문제가 발생했습니다..");
                            e.printStackTrace();
                        }

                    }//onResponse 메소드

                }, //리스너 클래스 끝...
                        new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("INFO","volley에서 에러났다.");
                        error.printStackTrace();
                        tv.setText("처리 중 문제가 발생했습니다..");
                    }
                })  {
            @Override
            protected Map<String, String> getParams(){
                Log.e("INFO","getParam에 들어왔다.");
                Map<String, String> params = new HashMap<>();
                params.put("qpId", qpId);
                params.put("csId", csId);
                return params;
            }
        };
        CustomStartApp.getInstance().addToRequestQueue(stringRequest);

        }//checkcallinfo 끝

    protected void checkOrderInfo(final String qpId, final String receiverPhone){

        String url = "http://70.12.109.173:9090/NexQuick/list/confirmOrder.do";

        Log.e("INFO","checkOrderInfo에 있는지 체크하러 들어왔다"+qpId+"/"+receiverPhone);


        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,  new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        String newResponse =  " {"+"list"+":"+response+"}";
                        Log.e("INFO!!!",newResponse);


                        try {
                            final JSONArray jarray = new JSONObject(newResponse).getJSONArray("list");

                            if(jarray.length()<1){
                                tv.setText("배정된 기사님이 아닙니다!");
                            } else {//올바른 만남이라면 다이올로그를 띄운다. 다이올로그에서 YES를 누르면 배송상태를 업데이트 한다.


                                new AlertDialog.Builder(CSBeamActivity.this)
                                        .setTitle("퀵프로님께서 "+jarray.length()+"개의 주문 건을 인도하셨습니까?")
                                        .setPositiveButton("Yes",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        Intent intent = new Intent(context, MainMenuActivity.class);
                                                        startActivity(intent);

                                                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST,
                                                                "http://70.12.109.173:9090/NexQuick/list/updateOrderAfterConfirm.do",
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        Log.e("INFO","배송상태 업데이트 성공");
                                                                    }
                                                                },
                                                                new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        Log.e("INFO","배송상태 업데이트 실패");
                                                                    }
                                                                }){
                                                            @Override
                                                            protected Map<String, String> getParams(){
                                                                Log.e("INFO","두번째 getParams호출");

                                                                HashMap<String ,String> params=new HashMap<String, String>();
                                                                JSONArray jar2 = new JSONArray();
                                                                try {
                                                                    for(int i=0;i<jarray.length();i++){
                                                                        JSONObject jsonObject=new JSONObject();
                                                                        jsonObject.put("orderNum",jarray.getJSONObject(i).getString("orderNum"));
                                                                        jar2.put(i,jsonObject);
                                                                    }

                                                                } catch (JSONException e){
                                                                    tv.setText("처리 중 문제가 발생했습니다..");
                                                                }
                                                                params.put("params",jar2.toString());

                                                                return params;
                                                            }
                                                        };
                                                        CustomStartApp.getInstance().addToRequestQueue(stringRequest2);


                                                    }//on click 메소드
                                                }//new Diolog
                                        )//positive버튼 선택
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                tv.setText("인도를 하지 않으셨습니다.");
                                            }
                                        }).show();

                            }


                        }catch (JSONException e){
                            Log.e("INFO","JsonException으로 떨어짐");
                            tv.setText("처리 중 문제가 발생했습니다..");
                            e.printStackTrace();
                        }

                    }//onResponse 메소드

                }, //리스너 클래스 끝...
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("INFO","volley에서 에러났다.");
                                error.printStackTrace();
                                tv.setText("처리 중 문제가 발생했습니다..");
                            }
                        })  {
            @Override
            protected Map<String, String> getParams(){
                Log.e("INFO","getParam에 들어왔다.");
                Map<String, String> params = new HashMap<>();
                params.put("qpId", qpId);
                params.put("receiverPhone", receiverPhone);
                return params;
            }
        };
        CustomStartApp.getInstance().addToRequestQueue(stringRequest);

    }//checkcallinfo 끝

    private NdefMessage[] getNdefMessages(Intent intent) {
        // Parse the intent
        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent
                    .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[] {};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
                        empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
                msgs = new NdefMessage[] { msg };
            }
        } else {
            Log.d(TAG, "Unknown intent.");
            finish();
        }
        return msgs;
    }
}
