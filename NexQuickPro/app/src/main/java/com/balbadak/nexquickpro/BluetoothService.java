package com.balbadak.nexquickpro;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothService extends Service {

    BluetoothSocket mSocket = null;
    InputStream mInputStream = null;
    OutputStream mOutputStream = null;
    Thread mWorkerThread = null;
    byte[] readBuffer;
    int readBufferPosition;
    String mainUrl;
    BluetoothDevice mRemoteDevie;
    private SharedPreferences loginInfo;
    // 스마트폰과 페어링 된 디바이스간 통신 채널에 대응 하는 BluetoothSocket

    IBinder mBinder = new MyBinder();

    class MyBinder extends Binder {
        BluetoothService getService() { // 서비스 객체를 리턴
            return BluetoothService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mainUrl = getResources().getString(R.string.main_url);
        loginInfo = getSharedPreferences("setting", 0);

        try {
            mSocket = BluetoothActivity.mSocket;
            Log.e("mSocket", "....on");
            mInputStream = mSocket.getInputStream();
            Log.e("mInputStream", "....on");
        } catch (IOException e) {
            e.printStackTrace();
        }
        beginListenForData();
        mWorkerThread.start();
        return START_STICKY;
    }


    // 데이터 수신(쓰레드 사용 수신된 메시지를 계속 검사함)
    void beginListenForData() {
        final Handler handler = new Handler();

        readBufferPosition = 0;                 // 버퍼 내 수신 문자 저장 위치.
        readBuffer = new byte[1024];            // 수신 버퍼.

        // 문자열 수신 쓰레드.
        mWorkerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // interrupt() 메소드를 이용 스레드를 종료시키는 예제이다.
                // interrupt() 메소드는 하던 일을 멈추는 메소드이다.
                // isInterrupted() 메소드를 사용하여 멈추었을 경우 반복문을 나가서 스레드가 종료하게 된다.
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        // InputStream.available() : 다른 스레드에서 blocking 하기 전까지 읽은 수 있는 문자열 개수를 반환함.
                        int byteAvailable = mInputStream.available();   // 수신 데이터 확인
                        if (byteAvailable > 0) {                        // 데이터가 수신된 경우.
                            byte[] packetBytes = new byte[byteAvailable];
                            mInputStream.read(packetBytes);
                            for (int i = 0; i < byteAvailable; i++) {
                                byte b = packetBytes[i];

                                if (b == '\n') {

                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    //  System.arraycopy(복사할 배열, 복사시작점, 복사된 배열, 붙이기 시작점, 복사할 개수)
                                    //  readBuffer 배열을 처음 부터 끝까지 encodedBytes 배열로 복사.
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        // 수신된 문자열 데이터에 대한 처리.
                                        @Override
                                        public void run() {

                                            String humidity = data.substring(7);
                                            String temperature = data.substring(0, 6);
//                                          DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
//                                          Date date = new Date();
                                            String url = mainUrl + "data/weatherSensorData.do";
                                            ContentValues value = new ContentValues();
                                            int humiInt = Integer.parseInt(data.substring(7, 9));
                                            int tempInt = Integer.parseInt(data.substring(1, 3));
                                            Log.e("humidity", humidity);
                                            Log.e("temperature", temperature);

                                            value.put("humidity", humiInt);
                                            value.put("temperature", tempInt);
                                            value.put("latitude", loginInfo.getString("latitude", ""));
                                            value.put("longitude", loginInfo.getString("longitude", ""));

                                            SendDataTask sendDataTask = new SendDataTask(url, value);
                                            sendDataTask.execute();

                                        }

                                    });

                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }

                    } catch (Exception e) {    // 데이터 수신 중 오류 발생.
                        Log.e("beginListenForData err", e.toString());
                    }
                }
            }

        });


    }


    public class SendDataTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public SendDataTask(String url, ContentValues values) {
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
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

        }
    }

    @Override
    public void onDestroy() {
        try {
            mWorkerThread.interrupt(); // 데이터 수신 쓰레드 종료
            mInputStream.close();
            mSocket.close();
        } catch (Exception e) {
        }
        super.onDestroy();
    }
}


