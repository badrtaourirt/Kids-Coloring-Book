package com.coloring.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {
    public static int appStartTimes;
    public static int appStartTimesShowSetting;

    private SharedPreference sharedPreferenceLoadTime;

  //  AdmobAds admobAds;

    public String interID = "Interstitial_Android";

    public void appStartCounter() {
        this.sharedPreferenceLoadTime.save(this, appStartTimes + 1);
        appStartTimes = this.sharedPreferenceLoadTime.getValue(this);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);

     //   ApplovinAds.init(this);
      //  UnityAds.load(interID);

     //   admobAds = new AdmobAds(this);
      //  new AdmobAds(this).initialize();

        MyConstant.appStartFirstTime = true;
        if (this.sharedPreferenceLoadTime == null) {
            this.sharedPreferenceLoadTime = new SharedPreference(SharedPreference.PREFS_NAME_AL, SharedPreference.PREFS_KEY_AL);
        }
        appStartTimes = this.sharedPreferenceLoadTime.getValue(this);
        appStartTimesShowSetting = appStartTimes;
        appStartCounter();
//        finish();


        //Ad_class.loadAd(SplashActivity.this);
        handler();

    }


    void handler() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();



            }
        }, 5000);
    }

}

