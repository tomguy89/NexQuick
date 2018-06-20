package com.balbadak.nexquickpro;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class GoogleService extends Service implements RecognitionListener{


    private SpeechRecognizer recognizer;
    Context ctx = this;
    String message;
    int callNum;
    private SharedPreferences loginInfo;
    int qpId;
    String mainUrl;

    public void onCreate() {


        super.onCreate();
        mainUrl =  getResources().getString(R.string.main_url);


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        message = intent.getStringExtra("message");

        Intent i = new Intent();
        System.out.println("intent ����");
        i.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//?��?��?��?�� intent?��?��
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());//?��?��?�� ?��?��
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");//?��?��?��?�� ?��?�� ?��?��
        System.out.println(""
                + "2intent�� extra ���");
        recognizer=SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(this);
        recognizer.startListening(i);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {


    }

    @Override
    public void onError(int error) {
        Log.e("Error",error+"");
        stopSelf();
    }

    @Override
    public void onResults(Bundle results) {
        // TODO Auto-generated method stub
        System.out.println("onResults �޼ҵ忡 ����");

        //���� �ν� ��� ����

        ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        for(String str:result){
            if(str.charAt(0)=='예'||str.charAt(0)=='네'||str.charAt(0)=='뇌'||str.charAt(0)=='눼'||str.charAt(0)=='내'||str.charAt(0)=='넵'||str.charAt(0)=='냅'||str.charAt(0)=='냉'||str.charAt(0)=='넹'){
                Toast.makeText(this,"수락하셨습니다.",Toast.LENGTH_SHORT).show();

                for(int i=message.length()-1; i>=0; i--){
                    if(message.charAt(i) == '@'){
                        callNum = Integer.parseInt(message.substring(i+1));
                        message = message.substring(0, i);
                        break;
                    }
                }

                loginInfo = getSharedPreferences("setting", 0);
                qpId = loginInfo.getInt("qpId", 0);

                String url = mainUrl + "appCall/completeAllocate.do";
                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                values.put("qpId", qpId);
                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();






                break;
            } else {
                Toast.makeText(this,"거부하셨습니다.",Toast.LENGTH_SHORT).show();
            }
        }

        stopSelf();

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        // TODO Auto-generated method stub
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {



            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "배차가 완료됐습니다.", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }


}
