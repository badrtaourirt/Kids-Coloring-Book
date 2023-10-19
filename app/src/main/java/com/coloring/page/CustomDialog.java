package com.coloring.page;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CustomDialog extends Activity implements View.OnClickListener {
    Button a;
    Button b;

    TextView d;

    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btn_no:
                finish();
                return;
            case R.id.btn_yes:
                finish();
                if (Build.VERSION.SDK_INT >= 16) {
                    finishAffinity();
                }
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                System.out.println("After Finish ::");
                startActivity(intent);
                return;
            default:
                return;
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_custom_dialog);
        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/english.ttf");
        this.a = (Button) findViewById(R.id.btn_yes);
        this.a.setTypeface(createFromAsset);
        this.b = (Button) findViewById(R.id.btn_no);
        this.b.setTypeface(createFromAsset);


        this.d = (TextView) findViewById(R.id.txt_dia);
        this.d.setTypeface(createFromAsset);
        this.a.setOnClickListener(this);
        this.b.setOnClickListener(this);


        FrameLayout frameLayout = findViewById(R.id.native_frame);
        Ad_class.refreshAd(frameLayout, CustomDialog.this);


    }
}
