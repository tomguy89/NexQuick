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
        return mInstance;
    }
    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            if(mRequestQueue==null){
            }

        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}