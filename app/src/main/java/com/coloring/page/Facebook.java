package com.coloring.page;



import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;

public class Facebook {

    public static InterstitialAd interstitialAd;

    public static RewardedVideoAd rewardedVideoAd;

    private AdView adView;

    private NativeAd nativeAd;

    ProgressBar animationView;


    String TAG = "badrfb";

    public String  fan_banner="7200449183304169_7200624573286630";
    public String  fan_interstitial="7200449183304169_7200478343301253";
    public String  fan_native="7200449183304169_7200738216608599";


    private AdClosedListener adClosedListener;

    private AppCompatActivity activity;


    public void fbinit(Activity activity) {
        interstitialAd = new InterstitialAd(activity, fan_interstitial);
        rewardedVideoAd = new RewardedVideoAd(activity, "YOUR_PLACEMENT_ID");
        interstitialAd.loadAd();
        rewardedVideoAd.loadAd();
    }

    public void showinterfb(final AdClosedListener adFinished) {

        if (interstitialAd.isAdLoaded()) {

            interstitialAd.show();
        } else {

            interstitialAd.loadAd();
        }


        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {


            @Override
            public void onError(Ad ad, AdError adError) {

                Log.d("badrfb", "inter fb erorr  ");

                interstitialAd.loadAd();
                adFinished.onAdClosed();

            }

            @Override
            public void onAdLoaded(Ad ad) {

                Log.d("badrfb", "inter fb load  ");
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                interstitialAd.loadAd();
                adFinished.onAdClosed();


            }


        };

        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    public void showbanner(Activity activity) {

        adView = new AdView(activity, fan_banner, AdSize.BANNER_HEIGHT_50);
        FrameLayout banner = activity.findViewById(R.id.banner);
        banner.addView(adView);
        adView.loadAd();



    }




    private NativeAdLayout nativeAdLayout;



    public void loadNativeAd(Activity activity) {

            nativeAd = new NativeAd(activity, fan_native);

            animationView = activity.findViewById(R.id.progressBar);


        NativeAdListener nativeAdListener = new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {

                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd,activity);
                animationView.setVisibility(View.GONE);

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    private LinearLayout adViewl;

    private void inflateAd(NativeAd nativeAd , Activity activity) {

        nativeAd.unregisterView();
        // Add the Ad view into the ad container.
        nativeAdLayout = activity.findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adViewl = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adViewl);


        // Add the AdOptionsView
        LinearLayout adChoicesContainer = activity.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = activity.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = activity.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = activity.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = activity.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = activity.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = activity.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = activity.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adViewl, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    public void showreward(final AdClosedListener adFinished) {

        if(rewardedVideoAd.isAdLoaded()){

            rewardedVideoAd.show();
        }else{

            rewardedVideoAd.loadAd();

        }
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                adFinished.onAdClosed();
                rewardedVideoAd.loadAd();
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d(TAG, "Rewarded video completed!");
                rewardedVideoAd.loadAd();
                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                rewardedVideoAd.loadAd();
                adFinished.onAdClosed();
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };

        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

}
