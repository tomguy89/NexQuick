package com.balbadak.nexquickapplication;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tsengvn.typekit.Typekit;

/**
 * Created by WonHada.com on 2016-04-20.
 */
public class CustomStartApp extends Application {

    public static final String TAG = CustomStartApp.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static CustomStartApp mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
/*
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/barun_gothic.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/barun_gothic_bold.otf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/barun_gothic_light.otf"));
*/

        mInstance = this;

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/pen_r.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/pen_b.otf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/pen_r.otf"));



    }

    public static synchronized CustomStartApp getInstance() {
        Log.e("INFO","getInstance하러왔어여");
        return mInstance;
    }
    public RequestQueue getRequestQueue() {

        Log.e("INFO","getrequestq 왔어여");
        if (mRequestQueue == null) {
            Log.e("INFO","request q 가 널이군요.");
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            if(mRequestQueue==null){
                Log.e("INFO","여전히 널이군요.");
            }

        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        Log.e("INFO","addrequestq하러왔어여");
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}