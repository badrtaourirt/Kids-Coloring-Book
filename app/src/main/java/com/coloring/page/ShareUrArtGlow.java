package com.coloring.page;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.IOException;

public class ShareUrArtGlow extends Activity implements View.OnClickListener {
    ImageView a;
    Intent b;
    ImageView back;
    Bitmap bitmap;
    Bitmap image;
    Bitmap mImage;
    MyMediaPlayer mediaPlayer;
    ImageView shareBtn;

    private void finishActivity() {
        Intent intent;
            finish();
            intent = new Intent(this, GridActivityColoringBookGlow.class);
        this.b = intent;
        startActivity(this.b);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void finishActivityWhenUrlNull() {
        MyConstant.isBackFromDrawActivity = true;
        finish();
        startActivity(new Intent(this, GridActivityColoringBookGlow.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

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

    private Bitmap uriToBitmap(Uri uri) {
        try {
            ParcelFileDescriptor openFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            this.image = BitmapFactory.decodeFileDescriptor(openFileDescriptor.getFileDescriptor());
            openFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.image;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.back) {
            finishActivity();
        } else if (id == R.id.share_btn) {
            shareApp(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (CapturePhotoUtils.url == null) {
            finishActivityWhenUrlNull();
            return;
        }
        setContentView(R.layout.shareurart);
        this.back = (ImageView) findViewById(R.id.back);
        this.back.setOnClickListener(this);
        this.a = (ImageView) findViewById(R.id.iv_urart);
        this.mImage = uriToBitmap(CapturePhotoUtils.url);
        this.a.setImageBitmap(this.mImage);
        this.shareBtn = (ImageView) findViewById(R.id.share_btn);
        this.shareBtn.setOnClickListener(this);
        this.mediaPlayer = new MyMediaPlayer(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        hideNavigation();
    }

    public void shareApp(Activity activity) {
        Intent intent = new Intent();
        intent.addFlags(1);
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", CapturePhotoUtils.url);
        intent.setType("image/jpeg");
        startActivity(Intent.createChooser(intent, "Share"));
    }
}
