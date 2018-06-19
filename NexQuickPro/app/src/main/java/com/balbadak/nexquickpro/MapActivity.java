package com.balbadak.nexquickpro;

import android.app.Activity;
import android.location.Location;

import com.skt.Tmap.TMapGpsManager;

public class MapActivity extends Activity implements TMapGpsManager.onLocationChangedCallback {

    public MapActivity(){
        TMapGpsManager gps = new TMapGpsManager(this);
        gps.setProvider(gps.GPS_PROVIDER);
        gps.setMinTime(5000);
        gps.setMinDistance(5);
        gps.OpenGps();
    }


    @Override
    public void onLocationChange(Location location) {

    }
}