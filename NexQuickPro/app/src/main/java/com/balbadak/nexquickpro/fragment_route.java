package com.balbadak.nexquickpro;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.balbadak.nexquickpro.vo.ListViewItem;
import com.balbadak.nexquickpro.vo.OnDelivery;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;


@SuppressLint("ValidFragment")
public class fragment_route extends Fragment {

    Context context;
    ViewPager viewPager;
    TMapView tMapView;
    Spinner quickSpinner;
    String mainUrl;
    ArrayList<ListViewItem> quickList;
    ArrayList<OnDelivery> list;

    //이은진 추가
    int callNum;
    int orderNum;
    int qpId;
    SharedPreferences loginInfo;
    boolean pickChackFlag;
    String phoneNumber = "tel:"+"00000000000";
    String pickUrl;
    String chackUrl;
    double qpLatitude;
    double qpLongitude;
    Button finishBtn;

    Button phoneBtn;
    Button cancelBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        loginInfo=getActivity().getSharedPreferences("setting",0);
        qpId=loginInfo.getInt("qpId",0);

        mainUrl = getActivity().getResources().getString(R.string.main_url);
        pickUrl = mainUrl + "list/afterBeamforQPS.do";
        chackUrl = mainUrl + "list/afterBeamforQPR.do";


        View view = inflater.inflate(R.layout.fragment_route, container, false);
        context = this.getActivity();
        cancelBtn = (Button) view.findViewById(R.id.quick_cancel);
        phoneBtn = (Button) view.findViewById(R.id.quick_phone);
        finishBtn = (Button) view.findViewById(R.id.quick_finish);
        viewPager = getActivity().findViewById(R.id.pager);
        quickSpinner = (Spinner) view.findViewById(R.id.quick_spinner);

        LinearLayout linearLayoutTmap = (LinearLayout) view.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(context);

        tMapView.setSKTMapApiKey("2c831aee-8c6e-444b-82ed-1a23b76e504c");
        linearLayoutTmap.addView(tMapView);
        qpLatitude = Double.parseDouble(loginInfo.getString("latitude", "0"));
        qpLongitude = Double.parseDouble(loginInfo.getString("longitude", "0"));

        if (getArguments() != null) {
            quickList = getArguments().getParcelableArrayList("quickList");
            list = getArguments().getParcelableArrayList("list");
        }else {
            quickList = new ArrayList<>();
            list = new ArrayList<>();
        }
// 마커 아이콘
        Bitmap qpMark = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.scooter), 100, 100, true);
        Bitmap sender = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.location), 100, 100, true);
        Bitmap receiver = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.location_primary), 100, 100, true);



        TMapMarkerItem qpMarker = new TMapMarkerItem();
        TMapPoint qpPoint = new TMapPoint(qpLatitude, qpLongitude); // QP 위치
        qpMarker.setIcon(qpMark); // 마커 아이콘 지정
        qpMarker.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        qpMarker.setTMapPoint(qpPoint); // 마커의 좌표 지정
        qpMarker.setName("Pro님의 현재 위치"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("qpMarker", qpMarker); // 지도에 마커 추가
        tMapView.setCenterPoint(qpLongitude, qpLatitude);

        TMapPoint tMapPointStart = qpPoint; // 출발지

        ArrayList<TMapPoint> passList = new ArrayList<>();
        TMapMarkerItem newMarker;
        TMapPoint newPoint;
        for (int i = 0; i < list.size(); i++) {
            newMarker = new TMapMarkerItem();
            newPoint = new TMapPoint(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()));
            if(quickList.get(i).getQuickType()==1){
                newMarker.setIcon(sender); // 마커 아이콘 지정
            }else if (quickList.get(i).getQuickType()==2){
                newMarker.setIcon(receiver); // 마커 아이콘 지정
            }
            newMarker.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
            newMarker.setTMapPoint(newPoint); // 마커의 좌표 지정
            newMarker.setName((i + 1) + "번째 방문 위치"); // 마커의 타이틀 지정
            tMapView.addMarkerItem("Point" + (i + 1), newMarker); // 지도에 마커 추가
            passList.add(newPoint);
        }


        if(passList.size()>0){
            NetworkTask networkTask = new NetworkTask(qpPoint, passList.get(passList.size() - 1), passList);
            networkTask.execute();
        }






        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        finishBtn.setOnClickListener(new View.OnClickListener() { //이걸 누르는 순간 결제 해야 하는지 안 해야 하는지....
            @Override
            public void onClick(View v) {
                    if(pickChackFlag){//콜넘을 갖고 가야 하고... 발송지로 가는 것!!
                        ContentValues values = new ContentValues();
                        values.put("callNum", callNum);
                        values.put("qpId", qpId);

                        SNetworkTask networkTask = new SNetworkTask(pickUrl,values);
                        networkTask.execute();


                    } else {//오더넘을 갖고가야 한다... 수령지로 가는 것!!!

                        ContentValues values = new ContentValues();
                        values.put("orderNum", orderNum);
                        values.put("qpId", qpId);

                        RNetworkTask networkTask = new RNetworkTask(chackUrl,values);
                        networkTask.execute();


                    }
            }
        });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        ArrayList<String> quickTitleList = new ArrayList<>();
        String item;
        SpinnerAdapter spinnerAdapter;




        if (quickList != null && quickList.size() != 0) {
//            for (ListViewItem lv : quickList) {
//                item = lv.getTitleStr() + "/" + lv.getDescStr();
//                quickTitleList.add(item);
//            }
            spinnerAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, quickList);
            quickSpinner.setAdapter(spinnerAdapter);

            quickSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ListViewItem lv = (ListViewItem) quickSpinner.getItemAtPosition(position);


                    tMapView.setCenterPoint(Double.parseDouble(list.get(position).getLongitude()), Double.parseDouble(list.get(position).getLatitude()));
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tMapView.setCenterPoint(qpLongitude, qpLatitude);
                        }
                    }, 4000);
                    final String phoneNum;
                    if(lv.getQuickType() ==  1) {
                        finishBtn.setText("인수확인");
                        callNum=lv.getCallNum();
                        pickChackFlag=true;
                        phoneNum = list.get(position).getSenderPhone();
                    } else {
                        finishBtn.setText("인도확인");
                        orderNum=lv.getOrderNum();
                        pickChackFlag=false;
                        phoneNum = list.get(position).getReceiverPhone();
                    }
                    phoneBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+phoneNum)));
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelCallAlert();
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            item = "진행중인 배송 없음";
            quickTitleList.add(item);
            spinnerAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, quickTitleList);
            quickSpinner.setAdapter(spinnerAdapter);
        }



    }

    public class NetworkTask extends AsyncTask<Void, Void, TMapPolyLine> {

        private TMapPoint tMapPointStart;
        private TMapPoint tMapPointEnd;
        private ArrayList<TMapPoint> passList;

        public NetworkTask(TMapPoint tMapPointStart, TMapPoint tMapPointEnd, ArrayList<TMapPoint> passList) {

            this.tMapPointStart = tMapPointStart;
            this.tMapPointEnd = tMapPointEnd;
            this.passList = passList;
        }

        @Override
        protected TMapPolyLine doInBackground(Void... params) {

            TMapPolyLine tMapPolyLine = null;
            try {
                tMapPolyLine = new TMapData().findMultiPointPathData(tMapPointStart, tMapPointEnd, passList, 0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            return tMapPolyLine;
        }

        @Override
        protected void onPostExecute(TMapPolyLine tMapPolyLine) {
            super.onPostExecute(tMapPolyLine);
            tMapPolyLine.setLineColor(Color.BLUE);
            tMapPolyLine.setLineWidth(2);
            tMapView.addTMapPolyLine("Line1", tMapPolyLine);
        }
    }

    public class SNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public SNetworkTask(String url, ContentValues values) {

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


            if(s!=null){

                try {
                    JSONArray ja = new JSONArray(s);

                    if(ja.length()>0){//뭔가 리스트가 왔다면
                        Intent i1 = new Intent(getActivity(),SPayCheckActivity.class); //결제확인창으로 보낸다.
                        i1.putExtra("unpayedCallNumber",ja.length());
                        i1.putExtra("JSONArray",ja.toString());
                        i1.putExtra("callNum",callNum);
                        startActivity(i1);
                    } else {
                        Intent i2 =new Intent(getActivity(),QPBeamSActivity.class);//바로 nfc태깅창으로 보낸다.
                        i2.putExtra("callNum",callNum);
                        startActivity(i2);
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {//온 리스트가 없다면..?
                Toast.makeText(getActivity(),"서버 전송 중 오류가 생겼습니다.",Toast.LENGTH_SHORT); //이렇게 나오는지 확인하기...

            }
        }
    }




    public class RNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public RNetworkTask(String url, ContentValues values) {

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


            if(s!=null){

                try {
                    JSONArray ja = new JSONArray(s);

                    if(ja.length()>0){//뭔가 리스트가 왔다면
                        Intent i1 = new Intent(getActivity(),RPayCheckActivity.class); //결제확인창으로 보낸다.
                        i1.putExtra("unpayedCallNumber",ja.length());
                        i1.putExtra("JSONArray",ja.toString());
                        i1.putExtra("orderNum",orderNum);
                        startActivity(i1);
                    } else {
                        Intent i2 =new Intent(getActivity(),QPBeamRActivity.class);//바로 nfc태깅창으로 보낸다.
                        i2.putExtra("orderNum",orderNum);
                        startActivity(i2);
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {//온 리스트가 없다면..?
                Toast.makeText(getActivity(),"서버 전송 중 오류가 생겼습니다.",Toast.LENGTH_SHORT); //이렇게 나오는지 확인하기...

            }
        }
    }


    private void cancelCallAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        String msg= "취소 수수료가 부과됩니다.\n정말 취소하시겠습니까?";
        alert.setTitle("콜 취소");
        alert.setMessage(msg);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = mainUrl+"call/reRegistCall.do";
                ContentValues values = new ContentValues();
                values.put("callNum", callNum);
                CancelTask cancelTask = new CancelTask(url, values);
                cancelTask.execute();
            }
        });

        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        alert.show();
    }

    public class CancelTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public CancelTask(String url, ContentValues values) {

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
            if(s.toString().equals("true")) {
                Toast.makeText(getActivity(), "배차가 취소됐습니다.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        }
    }

}
