package com.coloring.page;

import android.app.Activity;
import android.widget.FrameLayout;

public class Ad_class {

    static onLisoner onClickListener;

    public static void loadAd(Activity activity) {
        //ApplovinAds.loadInterstitial();
        //AdmobAds.loadInterAdmob();
    }


    public static void showInterstitial(Activity activity, onLisoner onLisoner) {
//        ApplovinAds.showInter(new ApplovinAds.AdFinished() {
//            @Override
//            public void onAdFinished() {
//                onLisoner.click();
//            }
//        });

//        AdmobAds.showInterAdmob(new AdmobAds.AdsFinished() {
//            @Override
//            public void onAdsFinished() {
//                onLisoner.click();
//            }
//        });
    }


    public static void refreshAd(FrameLayout frameLayout, Activity activity) {

        //ApplovinAds.showBanner(activity, frameLayout);
        //AdmobAds.showBannerAdmob(frameLayout, activity);

    }

    public static void Show_banner(Activity activity, FrameLayout mAdView) {
     //   ApplovinAds.showBanner(activity, mAdView);
        //AdmobAds.showBannerAdmob(mAdView, activity);
    }

    public interface onLisoner {
        void click();
    }


}
