package com.coloring.page;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.coloring.page.util.NotificationUtils;



public class MainActivity extends Activity implements View.OnClickListener {

    private static final String ONESIGNAL_APP_ID = "642da466-3f87-4964-a9cb-9aa424f62a43";


    public static Context context;
    public static String getImageName;
    public static String imageEncoded;
    public static MainActivity mainActivity;
    public static SharedPreference sharedPreference;
    public static SharedPreference sharedPreference_isShowNewApp;
    public static SharedPreference sharedPreference_never;
    SharedPreferences a;

    Intent b;
    ImageView back;
    ImageView btnRateUs;
    ImageView btnShare;
    MyMediaPlayer c;
    SharedPreference d;
    SharedPreference e;
    SharedPreference f;
    ImageView btnprivacy;
    ImageView glow;
    Intent i;
    public boolean isRateDialogeShow = false;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    MediaPlayerSoundAndMusic mediaPlayerSoundAndMusic;
    MediaPlayer mp;
    MediaPlayer mp_welcome;
    ImageView unicorn;


    WifiManager.AddNetworkResult adNetworks;


    private void hideNavigation() {
        View decorView;
        int i2;
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 14) {
            decorView = getWindow().getDecorView();
            i2 = 7686;
        } else if (i3 >= 11) {
            decorView = getWindow().getDecorView();
            i2 = 1;
        } else {
            return;
        }
        decorView.setSystemUiVisibility(i2);
    }

    private void intialize() {

        this.isRateDialogeShow = false;
        getResources();
        this.c = new MyMediaPlayer(this);

        if (sharedPreference_isShowNewApp == null) {
            sharedPreference_isShowNewApp = new SharedPreference(SharedPreference.PREFS_NAME_ISSHOWNEWAPP, SharedPreference.PREFS_KEY_ISSHOWNEWAPP);
        }
        new AdManager(this).start();
        if (sharedPreference_never == null) {
            sharedPreference_never = new SharedPreference(SharedPreference.PREFS_NAME_NS, SharedPreference.PREFS_KEY_NS);
        }
        if (sharedPreference == null) {
            sharedPreference = new SharedPreference(SharedPreference.PREFS_NAME_AL, SharedPreference.PREFS_KEY_AL);
        }
        if (this.d == null) {
            this.d = new SharedPreference(SharedPreference.PREFS_NAME_IMAGE, SharedPreference.PREFS_KEY_IMAGE);
        }
        if (this.e == null) {
            this.e = new SharedPreference(SharedPreference.PREFS_NAME_IMAGE_NAME, SharedPreference.PREFS_KEY_IMAGE_NAME);
        }
        if (this.f == null) {
            this.f = new SharedPreference(SharedPreference.PREFS_NAME_IMAGE_LINK, SharedPreference.PREFS_KEY_IMAGE_LINK);
        }
        int value = sharedPreference.getValue(this);
        sharedPreference.save(this, value + 1);
        if (sharedPreference_never.getValue(this) == 0 && value % 3 == 0 && value != 0) {
            showRateAppDialog();
        }
    }

    private void intializeIds() {
        this.back = (ImageView) findViewById(R.id.back);
        this.back.setOnClickListener(this);
        this.btnprivacy = (ImageView) findViewById(R.id.btnprivacy);
        this.btnprivacy.setOnClickListener(this);
        this.btnShare = (ImageView) findViewById(R.id.btnShare);
        this.btnShare.setOnClickListener(this);
        this.btnRateUs = (ImageView) findViewById(R.id.btnRateUs);
        this.btnRateUs.setOnClickListener(this);
        this.unicorn = (ImageView) findViewById(R.id.unicorn);
        this.unicorn.setOnClickListener(this);
        this.glow = (ImageView) findViewById(R.id.glow);
        this.glow.setOnClickListener(this);

        getImageName = this.e.getImageName(this);

    }


    private void rateUs() {
        sharedPreference_never.save(this, 1);
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.coloring.page")));
        } catch (ActivityNotFoundException unused) {
            Log.w("", "Android Market is not installed");
        }
    }


    private void setupMediaPlayer() {
        this.mp = MediaPlayer.create(getBaseContext(), R.raw.click);
        this.mediaPlayerSoundAndMusic = new MediaPlayerSoundAndMusic();
        this.mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.welcomemain);
    }

    public static void shareApp(Context context2) {
        String packageName = context2.getPackageName();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.SUBJECT", "Coloring");
        intent.putExtra("android.intent.extra.TEXT", "Try this awesome coloring game: https://play.google.com/store/apps/details?id=" + packageName);
        intent.setType("text/plain");
        context2.startActivity(intent);
    }

    private void showRateAppDialog() {
        System.out.println("intValue:: showRateAppDialog");
        this.isRateDialogeShow = true;
        this.c.playSound(R.raw.please);
        AlertDialog.Builder builder = Build.VERSION.SDK_INT > 10 ? new AlertDialog.Builder(this, R.style.NoTitleDialog) : new AlertDialog.Builder(this);
        builder.setTitle(R.string.rate_title);
        builder.setMessage(R.string.rate_app_str).setPositiveButton(R.string.yes_rate, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.sharedPreference_never.save(MainActivity.this, 1);
                dialogInterface.dismiss();
                try {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.coloring.page")));
                } catch (ActivityNotFoundException unused) {
                    Log.w("", "Android Market is not installed");
                }
            }
        }).setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setNeutralButton(R.string.never, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.sharedPreference_never.save(MainActivity.this, 1);
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void softKeyboardVisibility() {
        ((InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 2);
    }

    public void animateButtons() {
        int[] iArr = {R.id.unicorn, R.id.glow};
        int i2 = 1;
        for (int i3 : iArr) {
            Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.flip);
            loadAnimation.setStartOffset((long) (i2 * ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            ((ImageView) findViewById(iArr[i2 - 1])).startAnimation(loadAnimation);
            i2++;
        }
    }


    public int getPromoteCode() {
        this.a = getSharedPreferences("SCORE", 0);
        return this.a.getInt("CODE", 0);
    }

    public void moreApps() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=pub:STORE")));
        } catch (ActivityNotFoundException unused) {
            Log.w("", "Android Market is not installed");
        }
    }

    @Override
    public void onBackPressed() {
        playClickSound();
        this.c.StopMp();
        this.b = new Intent(this, CustomDialog.class);
        startActivity(this.b);
    }

    Intent intent;

    public void onClick(View view) {

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setFocusableInTouchMode(false);
        this.c.StopMp();
        this.c.playSound(R.raw.click);
        switch (view.getId()) {

            case R.id.back:
                this.c.StopMp();
                this.b = new Intent(this, CustomDialog.class);
                startActivity(this.b);
                this.c.StopMp();
                return;
            case R.id.btnRateUs:
                playClickSound();
                rateUs();
                return;
            case R.id.btnShare:
                shareApp(this);
                return;
            case R.id.btnprivacy:

                String url = "https://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return;


            case R.id.unicorn:
                MyConstant.COlORING_BOOK_ID = 0;
                finish();


                //  UnityAds.show(MainActivity.this,interID);
//                ApplovinAds.showInter(this::NextActivity2);
                //   AdmobAds.showInterAdmob(this::NextActivity2);
//                        Ad_class.showInterstitial(MainActivity.this, new Ad_class.onLisoner() {
//
//                            public void click() {
//
//                            }
//                        });
                Intent intent2 = new Intent(MainActivity.this, GridActivityColoringBook.class);
                startActivity(intent2);
                break;
            case R.id.glow:
                MyConstant.COlORING_BOOK_ID = 1;
                finish();



              //  UnityAds.show(MainActivity.this,interID);

                Intent intent1 = new Intent(MainActivity.this, GridActivityColoringBookGlow.class);
                startActivity(intent1);
               // shoInter();

                //               ApplovinAds.showInter(this::NextActivity1);
                //               AdmobAds.showInterAdmob(this::NextActivity1);
//                Ad_class.showInterstitial(MainActivity.this, new Ad_class.onLisoner() {
//
//                    public void click() {
//
//
//                    }
//                });
//                Intent intent1 = new Intent(MainActivity.this, GridActivityColoringBookGlow.class);
//                startActivity(intent1);
                break;
            default:
                return;
        }

    }

    private void NextActivity2() {
        Intent intent2 = new Intent(MainActivity.this, GridActivityColoringBook.class);
        startActivity(intent2);
    }

    private void NextActivity1() {
        Intent intent1 = new Intent(MainActivity.this, GridActivityColoringBookGlow.class);
        startActivity(intent1);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        OneSignal.getNotifications().requestPermission(true, Continue.with(r -> {
            if (r.isSuccess()) {
                if (r.getData()) {
                    // `requestPermission` completed successfully and the user has accepted permission
                } else {
                    // `requestPermission` completed successfully but the user has rejected permission
                }
            } else {
                // `requestPermission` completed unsuccessfully, check `r.getThrowable()` for more info on the failure reason
            }
        }));



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SoundManager.getInstance();
        SoundManager.initSounds(this);
        MyApplication.setContext(this);
        mainActivity = this;
        intialize();
        context = this;

        intializeIds();
        setupMediaPlayer();
        softKeyboardVisibility();
        animateButtons();



        FrameLayout frameLayout = findViewById(R.id.native_frame);
        Ad_class.refreshAd(frameLayout, MainActivity.this);
        //ApplovinAds.loadInterstitial();



    }

    @Override
    public void onDestroy() {
        this.c.StopMp();
        this.mediaPlayerSoundAndMusic.destroyMusic();
        super.onDestroy();
    }



    LinearLayout adView;


    @Override
    public void onPause() {
        super.onPause();
        this.c.StopMp();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
        //Ad_class.loadAd(MainActivity.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideNavigation();

        LocalBroadcastManager.getInstance(this).registerReceiver(this.mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getApplicationContext());
        this.mediaPlayerSoundAndMusic.startMainMusic();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mediaPlayerSoundAndMusic.startMainMusic();
    }

    @Override
    public void onStop() {
        this.c.StopMp();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
        super.onStop();
    }

    public void playClickSound() {
        this.c.playSound(R.raw.click);
    }

    public void setPromoteCode(int i2) {
        this.a = getSharedPreferences("SCORE", 0);
        SharedPreferences.Editor edit = this.a.edit();
        edit.putInt("CODE", i2);
        edit.commit();
    }

  //  @Override
  //  public void onInitializationComplete() {

  //  }
//

}

