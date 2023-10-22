package com.coloring.page;

import static com.coloring.page.Facebook.interstitialAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class GridActivityColoringBook extends Activity implements View.OnClickListener {
    public static int pos;
    ImageView back;
    GridView gridView;
    Intent intent;
    MediaPlayerSoundAndMusic mediaPlayerSoundAndMusic;
    MyMediaPlayer myMediaPlayer;
    LinearLayout topll;
    MyApplication myApplication;

  //  AdmobAds admobAds;

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
        com.coloring.page.MyConstant.selectedImageFromBitmap = -1;
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        com.coloring.page.MyConstant.heightInPixels = defaultDisplay.getHeight();
        com.coloring.page.MyConstant.widthInPixels = defaultDisplay.getWidth();
        int i = com.coloring.page.MyConstant.COlORING_BOOK_ID;
        if (i == 0) {
            com.coloring.page.MyConstant.selected_bitmapIds = com.coloring.page.MyConstant.bitmapUnicornIds;
        } else if (i == 1) {
            com.coloring.page.MyConstant.selected_bitmapIds = com.coloring.page.MyConstant.bitmapGlowIds;
        }
    }

    private void openUrl(String str) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (ActivityNotFoundException unused) {
            Log.w("", "Android Market is not installed");
        }
    }

    @SuppressLint("WrongConstant")
    public void finishActivity() {
        com.coloring.page.MyConstant.showNewApp = true;
        finish();
        this.myMediaPlayer.playSound(R.raw.click);
        this.intent = new Intent(this, com.coloring.page.MainActivity.class);
        this.intent.addFlags(335544320);
        this.intent.addFlags(32768);
        startActivity(this.intent);
    }

    public void finishActivityOnItemSelect() {
        Intent intent2;
        DrawActivity drawActivity = DrawActivity.drawActivity;
        if (drawActivity != null) {
            drawActivity.finish();
        }
        finish();
        int i = com.coloring.page.MyConstant.COlORING_BOOK_ID;
        if (i == 0) {
            intent2 = new Intent(this, UnicornActivity.class);
        } else if (i == 1) {
            intent2 = new Intent(this, DrawActivityGlow.class);
        } else {
            return;
        }
        startActivity(intent2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {


            finishActivity();
        }
    }

    Facebook facebook = new Facebook();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        intialize();
        setContentView(R.layout.grid_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.myMediaPlayer = new MyMediaPlayer(this);
        this.mediaPlayerSoundAndMusic = new com.coloring.page.MediaPlayerSoundAndMusic();
        this.mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.grid_menu);
        int i = com.coloring.page.MyConstant.widthInPixels / 20;
        this.topll = (LinearLayout) findViewById(R.id.topll);
        this.gridView = (GridView) findViewById(R.id.grid_view);
        this.gridView.setNumColumns(-1);
        this.gridView.setGravity(17);
        this.gridView.setHorizontalSpacing(i);
        this.gridView.setVerticalSpacing(i);
        this.gridView.setFastScrollEnabled(true);
        this.back = (ImageView) findViewById(R.id.back);
        this.back.setOnClickListener(this);
        MyApplication.setContext(this);
        facebook.showbanner(this);

        this.gridView.setAdapter(new ImageAdapter(this));
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {


                if(interstitialAd.isAdLoaded()) {
                    facebook.showinterfb(new AdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            GridActivityColoringBook.pos = i;
                            if (com.coloring.page.MyConstant.COlORING_BOOK_ID == 0) {
                                if (i != 0) {


                                    com.coloring.page.MyConstant.selectedImageFromBitmap = i;
                                    com.coloring.page.MyConstant.fromGridActivityColoringBook = true;
                                    com.coloring.page.MyConstant.selectedTool = 0;
                                }
                                GridActivityColoringBook.this.finishActivityOnItemSelect();
                            }

                        }
                    });
                }else{

                    GridActivityColoringBook.pos = i;
                    if (com.coloring.page.MyConstant.COlORING_BOOK_ID == 0) {
                        if (i != 0) {


                            com.coloring.page.MyConstant.selectedImageFromBitmap = i;
                            com.coloring.page.MyConstant.fromGridActivityColoringBook = true;
                            com.coloring.page.MyConstant.selectedTool = 0;
                        }
                        GridActivityColoringBook.this.finishActivityOnItemSelect();
                    }
                }
            }
        });
        this.gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {

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
        //Ad_class.loadAd(GridActivityColoringBook.this);
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
}
