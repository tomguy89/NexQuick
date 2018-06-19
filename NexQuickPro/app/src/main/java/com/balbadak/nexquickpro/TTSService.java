package com.balbadak.nexquickpro;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Locale;

public class TTSService extends Service {

    TextToSpeech tts;
    Context ctx = this;
    String message;
    int callNum;

    @Override
    public void onCreate() {

        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        message =intent.getStringExtra("message");

        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                    tts.setSpeechRate(1.0f);
                    tts.speak(message+" 수락하시겠습니까?",TextToSpeech.QUEUE_FLUSH, null);

                    while(true){
                        if(!tts.isSpeaking()){
                            Intent i = new Intent(ctx, GoogleService.class);
                            i.putExtra("message",message); //tts로 받았던 메세지를 들고 google service로 간다.
                            startService(i);
                            stopSelf();
                            break;
                        }
                    }

                }
            }
        });

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
