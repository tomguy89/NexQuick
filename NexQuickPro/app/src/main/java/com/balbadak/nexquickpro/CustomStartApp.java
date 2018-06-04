package com.balbadak.nexquickpro;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by WonHada.com on 2016-04-20.
 */
public class CustomStartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
/*
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/barun_gothic.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/barun_gothic_bold.otf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/barun_gothic_light.otf"));
*/



        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/pen_r.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/pen_b.otf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/pen_r.otf"));

    }
}