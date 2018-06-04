package com.balbadak.nexquickpro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.widget.Button;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;



@SuppressLint("ValidFragment")
public class Fragment2 extends Fragment {


    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_route, container, false);

        Button cancelBtn = (Button) view.findViewById(R.id.quick_cancel);
        Button phoneBtn = (Button) view.findViewById(R.id.quick_phone);
        Button finishBtn = (Button) view.findViewById(R.id.quick_finish);
        viewPager = getActivity().findViewById(R.id.pager);



        /*        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("네이티브 앱 키 입력");
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);
        container.addView(mapView);*/


        MapView mapView = new MapView(getActivity());

        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

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

/*      marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.*/
        mapView.addPOIItem(marker2);

        MapPOIItem marker3 = new MapPOIItem();
        marker3.setItemName("수령자 위치");
        marker3.setTag(3);
        MapPoint POINT3 = MapPoint.mapPointWithGeoCoord(37.500090, 127.1100);
        marker3.setMapPoint(POINT3);
        marker3.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker3.setCustomImageResourceId(R.drawable.location_primary);


/*        marker3.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker3.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.*/
        mapView.addPOIItem(marker3);



        MapPointBounds mapPointBounds = new MapPointBounds();

        mapPointBounds.add(POINT1);
        mapPointBounds.add(POINT2);
        mapPointBounds.add(POINT3);
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds,padding));




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
