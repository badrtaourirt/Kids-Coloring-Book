package com.coloring.page;

import static com.coloring.page.Facebook.interstitialAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import java.io.PrintStream;

public class GridActivityColoringBookGlow extends Activity implements View.OnClickListener {
    ImageView back;
    GridView gridView;
    Intent intent;
    MediaPlayerSoundAndMusic mediaPlayerSoundAndMusic;
    MyMediaPlayer myMediaPlayer;
    LinearLayout topll;

    MyApplication myApplication;

    public String interID = "Interstitial_Android";

    private void hideNavigation() {
        View decorView;
        int i;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 14) {
            decorView = getWindow().getDecorView();
            i = 7686;
        } else if (i2 >= 11) {
            decorView = getWindow().getDecorView();
            i = 1;
        } else {
            return;
        }
        decorView.setSystemUiVisibility(i);
    }

    private void intialize() {
        MyConstant.selectedImageFromBitmap = -1;
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        MyConstant.heightInPixels = defaultDisplay.getHeight();
        MyConstant.widthInPixels = defaultDisplay.getWidth();
        MyConstant.selected_bitmapIds = MyConstant.bitmapGlowIds;
    }


    @SuppressLint("WrongConstant")
    public void finishActivity() {
        MyConstant.showNewApp = true;
        finish();
        this.intent = new Intent(this, MainActivity.class);
        this.intent.addFlags(335544320);
        this.intent.addFlags(32768);
        startActivity(this.intent);
    }

    public void finishActivityOnItemSelect() {
        System.err.println("finishActivityOnItemSelect");
        DrawActivityGlow drawActivityGlow = DrawActivityGlow.drawActivityGlow;
        if (drawActivityGlow != null) {
            drawActivityGlow.finish();
        }
        finish();
        startActivity(new Intent(this, DrawActivityGlow.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    private void NextActivity2() {
        finishActivity();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {

         //   shoInter();
            finishActivity();
        }
    }

    Facebook facebook = new Facebook();
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        intialize();
        setContentView(R.layout.grid_layout_glow);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.myMediaPlayer = new MyMediaPlayer(this);
        this.mediaPlayerSoundAndMusic = new MediaPlayerSoundAndMusic();
        this.mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.grid_menu);
        int i = MyConstant.widthInPixels / 20;
        this.topll = (LinearLayout) findViewById(R.id.topll);
        this.gridView = (GridView) findViewById(R.id.grid_view);
        this.gridView.setNumColumns(-1);
        facebook.fbinit(this);
        this.gridView.setGravity(17);
        this.gridView.setHorizontalSpacing(i);
        this.gridView.setVerticalSpacing(i);
        this.gridView.setFastScrollEnabled(true);
        this.back = (ImageView) findViewById(R.id.back);
        this.back.setOnClickListener(this);
        MyApplication.setContext(this);
        facebook.showbanner(this);

        //    Ad_class.Show_banner(this, findViewById(R.id.adView));

        //ApplovinAds.loadInterstitial();
     //   admobAds = new AdmobAds(this);
      //  FrameLayout frameLayout = findViewById(R.id.adView);
     //   AdmobAds.loadBannerAdmob();
        //AdmobAds.showBannerAdmob(frameLayout, GridActivityColoringBookGlow.this);
      //  AdmobAds.loadInterAdmob();

        this.gridView.setAdapter(new ImageAdapterGlow(this));
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
              //  UnityAds.show(GridActivityColoringBookGlow.this,interID);
               // UnityAds.load(interID);

                if(interstitialAd.isAdLoaded()) {
                    facebook.showinterfb(new AdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            PrintStream printStream = System.err;
                            printStream.println("posss::" + i);
                            if (i != 0) {

                                MyConstant.selectedImageFromBitmap = i;
                                MyConstant.fromGridActivityColoringBook = true;
                                MyConstant.selectedTool = 0;
                            }

                            GridActivityColoringBookGlow.this.


                                    finishActivityOnItemSelect();
                        }
                    });

                }else {

                    PrintStream printStream = System.err;
                    printStream.println("posss::" + i);
                    if (i != 0) {

                        MyConstant.selectedImageFromBitmap = i;
                        MyConstant.fromGridActivityColoringBook = true;
                        MyConstant.selectedTool = 0;
                    }

                    GridActivityColoringBookGlow.this.


                            finishActivityOnItemSelect();

                }



            }
        });
        this.gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
//                GridActivityColoringBookGlow.this.checkMoreAppsLongClick(i);
                return false;
            }
        });
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mediaPlayerSoundAndMusic.destroyMusic();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();

        //Ad_class.loadAd(GridActivityColoringBookGlow.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideNavigation();
        this.mediaPlayerSoundAndMusic.startMainMusic();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mediaPlayerSoundAndMusic.startMainMusic();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }

    @Override
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            hideNavigation();
        }
    }
}
