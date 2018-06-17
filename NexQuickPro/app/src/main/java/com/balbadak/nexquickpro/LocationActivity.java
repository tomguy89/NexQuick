package com.balbadak.nexquickpro;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LocationActivity extends AppCompatActivity {


    TextView tv;//안드로이드에서 정보 받는 거 확인용(나중에는 없앤다.)

    ToggleButton tb;//출퇴근버튼
    LocationManager lm;
    private SharedPreferences loginInfo;
    String qpId;
    boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            //버전 높으면 동적으로 권한 획득을 거쳐야 함.

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }

        tv = (TextView) findViewById(R.id.textView2);//얘 지우기

        tb = (ToggleButton)findViewById(R.id.toggle1);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        //나중에 이거 주석 풀어주기!!!
/*        loginInfo = getSharedPreferences("setting", 0);
             qpId=loginInfo.getString("qpId","");*/
            qpId="3";


        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(tb.isChecked()){//출근중으로 설정하면
                        flag=false;
                        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                                1000, // 통지사이의 최소 시간간격 (miliSecond)
                                1, // 통지사이의 최소 변경거리 (m)
                                mLocationListener);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내면 여기로 가게된다)
                                1000, // 통지사이의 최소 시간간격 (miliSecond)
                                1, // 통지사이의 최소 변경거리 (m)
                                mLocationListener);

                    }else{//퇴근하기로 설정하면
                        tv.setText("위치정보 미수신중");
                        lm.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 밧데리 소모가 덜하다...
                        deleteGPS(qpId);
                        //db에 테이블 날려버리는 것으로 컨트롤러 연결!!!!!
                    }
                }catch(SecurityException ex){
                }
            }
        });
    } // end of onCreate

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.

            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            float accuracy = location.getAccuracy();    //정확도...는 사실 그냥 내가 확인할 용이고 지울 것.

            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            tv.setText("\n위도 : " + longitude + "\n경도 : " + latitude +  "\n정확도 : "  + accuracy);

            //여기서 컨트롤러한테 보내야 한다. qpId랑  위도 경도 값을 갖고...... 그럼 그 컨트롤러에서 db를 업데이트한다.

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

        String url = "http://70.12.109.164:9090/NexQuick/qpPosition/insertPosition.do"; //url 주소는 태진오빠껄로 바꾸기!!

        ContentValues values = new ContentValues();

        values.put("qpId",qpId);  //나중에 이걸로 바꿔야함.
        values.put("qpLongitude", qpLongitude);
        values.put("qpLatitude", qpLatitude);

        NetworkTask networkTask = new NetworkTask(url,values);
        networkTask.execute();
    }


    private void updateGPS(double qpLongitude, double qpLatitude){


        // String url = "http://70.12.109.166:9090/NexQuick/qpPosition/updatePosition.do";
        String url = "http://70.12.109.173:9090/NexQuick/qpPosition/updatePosition.do"; //url 주소는 태진오빠껄로 바꾸기!!


        ContentValues values = new ContentValues();
        values.put("qpId",qpId);
        values.put("qpLongitude", qpLongitude);
        values.put("qpLatitude", qpLatitude);

        NetworkTask networkTask = new NetworkTask(url,values);
        networkTask.execute();
    }


    private void deleteGPS(String qpId){


        // String url = "http://70.12.109.166:9090/NexQuick/qpPosition/updatePosition.do";
        String url = "http://70.12.109.164:9090/NexQuick/qpPosition/deletePosition.do"; //url 주소는 태진오빠껄로 바꾸기!!

        ContentValues values = new ContentValues();
        values.put("qpId",qpId);

        NetworkTask networkTask = new NetworkTask(url,values);
        networkTask.execute();
    }

    public class NetworkTask extends  AsyncTask<Void,Void,Void>{

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values){
            this.url=url;
            this.values=values;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            requestHttpURLConnection.request(url, values);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }





}
