package com.coloring.page;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class UnicornActivity extends DrawActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_unicorn);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DrawActivity.drawActivity = this;
        d();
        e();
        initializeMediaPlayer();
        b();
        a(a(0));
        drawerImplementationForBrush();
        drawerImplementationForColor();
        a();
        setDefaultColor();
    }

    @Override
    public void onResume() {
        b(this.b);
        a(this.a);
        super.onResume();
        c();
        f();
    }
}
