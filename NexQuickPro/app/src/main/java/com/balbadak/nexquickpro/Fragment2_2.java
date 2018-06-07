package com.balbadak.nexquickpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;


@SuppressLint("ValidFragment")
public class Fragment2_2 extends Fragment {

    Context context;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_route, container, false);
        context = this.getActivity();
        Button cancelBtn = (Button) view.findViewById(R.id.quick_cancel);
        Button phoneBtn = (Button) view.findViewById(R.id.quick_phone);
        Button finishBtn = (Button) view.findViewById(R.id.quick_finish);
        viewPager = getActivity().findViewById(R.id.pager);
        LinearLayout linearLayoutTmap = (LinearLayout) view.findViewById(R.id.linearLayoutTmap);
        TMapView tMapView = new TMapView(context);

        tMapView.setSKTMapApiKey( "2c831aee-8c6e-444b-82ed-1a23b76e504c" );
        linearLayoutTmap.addView( tMapView );

        TMapMarkerItem qpMarker = new TMapMarkerItem();
        TMapPoint tMapPoint1 = new TMapPoint(37.570841, 126.985302); // SKT타워

// 마커 아이콘
        Bitmap qpMark = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.scooter), 100, 100, true);


        qpMarker.setIcon(qpMark); // 마커 아이콘 지정
        qpMarker.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        qpMarker.setTMapPoint( tMapPoint1 ); // 마커의 좌표 지정
        qpMarker.setName("Pro님의 현재 위치"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem1", qpMarker); // 지도에 마커 추가

        tMapView.setCenterPoint( 126.985302, 37.570841 );

        TMapPoint tMapPointStart = new TMapPoint(37.570841, 126.985302); // SKT타워(출발지)
        TMapPoint tMapPointEnd = new TMapPoint(37.551135, 126.988205); // N서울타워(목적지)

        try {
            TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
            tMapPolyLine.setLineColor(Color.BLUE);
            tMapPolyLine.setLineWidth(3);
            tMapView.addTMapPolyLine("Line1", tMapPolyLine);

        }catch(Exception e) {
            e.printStackTrace();
        }



        /*        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("네이티브 앱 키 입력");
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);
        container.addView(mapView);*/


       /* MapView mapView = new MapView(getActivity());

        //ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        //mapViewContainer.addView(mapView);

        MapPOIItem marker1 = new MapPOIItem();
        marker1.setItemName("퀵 프로님 위치");
        marker1.setTag(0);
        MapPoint POINT1 = MapPoint.mapPointWithGeoCoord(37.500900, 127.036600);
        marker1.setMapPoint(POINT1);
        marker1.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker1.setCustomImageResourceId(R.drawable.scooter);
        //  marker1.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        //  marker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker1);


        MapPOIItem marker2 = new MapPOIItem();
        marker2.setItemName("발송자 위치");
        marker2.setTag(1);
        MapPoint POINT2 = MapPoint.mapPointWithGeoCoord(37.512312, 127.136677);
        marker2.setMapPoint(POINT2);
        marker2.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker2.setCustomImageResourceId(R.drawable.location_accent);

*//*      marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.*//*
        mapView.addPOIItem(marker2);

        MapPOIItem marker3 = new MapPOIItem();
        marker3.setItemName("수령자 위치");
        marker3.setTag(3);
        MapPoint POINT3 = MapPoint.mapPointWithGeoCoord(37.500090, 127.1100);
        marker3.setMapPoint(POINT3);
        marker3.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker3.setCustomImageResourceId(R.drawable.location_primary);


*//*        marker3.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker3.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.*//*
        mapView.addPOIItem(marker3);



        MapPointBounds mapPointBounds = new MapPointBounds();

        mapPointBounds.add(POINT1);
        mapPointBounds.add(POINT2);
        mapPointBounds.add(POINT3);
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds,padding));
*/



        //mapView.getMapPointBounds();

/*       try {
            PackageInfo info = getPackageManager().getPackageInfo("com.balbadak.nexquick", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/









        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }
}
