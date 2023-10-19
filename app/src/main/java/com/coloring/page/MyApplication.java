package com.coloring.page;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import android.widget.RelativeLayout;

public class MyApplication extends Application {

    public String interID = "Interstitial_Android";
     static Context context;

     static Context mContext;

    RelativeLayout bannerView;
    String Nativeadsf;



    public static Context getAppContext() {
        return context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context2) {
    }

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();



    }

}



