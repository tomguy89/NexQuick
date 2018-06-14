package com.balbadak.nexquickpro;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


public class LocationService extends Service {


    //  TextView tv;//안드로이드에서 정보 받는 거 확인용(나중에는 없앤다.)

    //   ToggleButton tb;//출퇴근버튼
    LocationManager lm;
    private SharedPreferences loginInfo;
    int qpId;
    String connectToken;
    boolean flag;


    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("INFO", "서비스onCreate");
        lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        //빨간줄 뜨지만 신경ㄴㄴ
        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("INFO", "permission을 못받아와서 종료됨");
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,mLocationListener);



        loginInfo = getSharedPreferences("setting", 0);
        qpId=loginInfo.getInt("qpId", 0);
        connectToken=loginInfo.getString("token", "");
        Log.e("INFO", flag+"");



        flag=false;
        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
      /*  lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내면 여기로 가게된다)
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("INFO","onDestroy호출됨");
        lm.removeUpdates(mLocationListener);
        deleteGPS(qpId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.

            Log.e("test", "onLocationChanged, location:" + location);
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            float accuracy = location.getAccuracy();    //정확도...는 사실 그냥 내가 확인할 용이고 지울 것.

            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.

            //여기서 컨트롤러한테 보내야 한다. qpId랑  위도 경도 값을 갖고...... 그럼 그 컨트롤러에서 db를 업데이트한다.

            Log.e("INFO","onLocationChanged호출됨...");

            if(!flag){
                insertGPS(longitude,latitude);
                flag=true;
            } else {
                updateGPS(longitude, latitude);
            }


        }
        public void onProviderDisabled(String provider) {
            // the provider is disabled by the user.
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Called when the provider is enabled by the user.
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // unable to fetch a location or if the provider has recently become available after a period of unavailability.
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);

        }

    };


    private  void insertGPS(double qpLongitude, double qpLatitude){
        Log.e("INFO","insertGPS호출됨");
        String url = "http://70.12.109.173:9090/NexQuick/qpPosition/insertPosition.do"; //url 주소는 태진오빠껄로 바꾸기!!

        ContentValues values = new ContentValues();

        values.put("qpId",qpId);  //나중에 이걸로 바꿔야함.
        values.put("qpLongitude", qpLongitude);
        values.put("qpLatitude", qpLatitude);
        values.put("connectToken",connectToken);

        NetworkTask networkTask = new NetworkTask(url,values);
        networkTask.execute();
    }


    private void updateGPS(double qpLongitude, double qpLatitude){

        Log.e("INFO","updateGPS호출됨");

        // String url = "http://70.12.109.166:9090/NexQuick/qpPosition/updatePosition.do";
        String url = "http://70.12.109.173:9090/NexQuick/qpPosition/updatePosition.do"; //url 주소는 태진오빠껄로 바꾸기!!


        ContentValues values = new ContentValues();
        values.put("qpId",qpId);
        values.put("qpLongitude", qpLongitude);
        values.put("qpLatitude", qpLatitude);
        values.put("connectToken",connectToken);


        NetworkTask networkTask = new NetworkTask(url,values);
        networkTask.execute();
    }

    private void deleteGPS(int qpId){

        Log.e("INFO","deleteGPS호출됨");

        // String url = "http://70.12.109.166:9090/NexQuick/qpPosition/updatePosition.do";
        String url = "http://70.12.109.173:9090/NexQuick/qpPosition/deletePosition.do"; //url 주소는 태진오빠껄로 바꾸기!!

        ContentValues values = new ContentValues();
        values.put("qpId",qpId);

        NetworkTask networkTask = new NetworkTask(url,values);
        networkTask.execute();
    }

    public class NetworkTask extends AsyncTask<Void,Void,Void> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values){
            this.url=url;
            this.values=values;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("INFO","doInBackground호출됨");
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            requestHttpURLConnection.request(url, values);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("INFO","postExecute호출됨");
        }
    }

}
