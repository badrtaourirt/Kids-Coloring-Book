package com.coloring.page.util;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;

import com.coloring.page.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NotificationUtils {

    private Context mContext;

    public NotificationUtils(Context context) {
        this.mContext = context;
    }

    public static void clearNotifications(Context context) {
        ((NotificationManager) context.getSystemService("notification")).cancelAll();
    }

    public static long getTimeMilliSec(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        boolean z = true;
        if (Build.VERSION.SDK_INT <= 20) {
            return !activityManager.getRunningTasks(1).get(0).topActivity.getPackageName().equals(context.getPackageName());
        }
        for (ActivityManager.RunningAppProcessInfo next : activityManager.getRunningAppProcesses()) {
            if (next.importance == 100) {
                boolean z2 = z;
                for (String equals : next.pkgList) {
                    if (equals.equals(context.getPackageName())) {
                        z2 = false;
                    }
                }
                z = z2;
            }
        }
        return z;
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder builder, int i, String str, String str2, String str3, PendingIntent pendingIntent, Uri uri) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(str);
        bigPictureStyle.setSummaryText(Html.fromHtml(str2).toString());
        bigPictureStyle.bigPicture(bitmap);
        ((NotificationManager) this.mContext.getSystemService("notification")).notify(101, builder.setSmallIcon(i).setTicker(str).setWhen(0).setAutoCancel(true).setContentTitle(str).setContentIntent(pendingIntent).setSound(uri).setStyle(bigPictureStyle).setWhen(getTimeMilliSec(str3)).setSmallIcon(R.mipmap.logo).setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), i)).setContentText(str2).build());
    }

    private void showSmallNotification(NotificationCompat.Builder builder, int i, String str, String str2, String str3, PendingIntent pendingIntent, Uri uri) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(str2);
        ((NotificationManager) this.mContext.getSystemService("notification")).notify(100, builder.setSmallIcon(i).setTicker(str).setWhen(0).setAutoCancel(true).setContentTitle(str).setContentIntent(pendingIntent).setSound(uri).setStyle(inboxStyle).setWhen(getTimeMilliSec(str3)).setSmallIcon(R.mipmap.logo).setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), i)).setContentText(str2).build());
    }

    public Bitmap getBitmapFromURL(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void playNotificationSound() {
        try {
            RingtoneManager.getRingtone(this.mContext, Uri.parse("android.resource://" + this.mContext.getPackageName() + "/raw/notification")).play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNotificationMessage(String str, String str2, String str3, Intent intent) {
        showNotificationMessage(str, str2, str3, intent, (String) null);
    }

    public void showNotificationMessage(String str, String str2, String str3, Intent intent, String str4) {
        if (!TextUtils.isEmpty(str2)) {
            intent.setFlags(603979776);
            PendingIntent activity = PendingIntent.getActivity(this.mContext, 0, intent, PendingIntent.FLAG_MUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.mContext);
            Uri parse = Uri.parse("android.resource://" + this.mContext.getPackageName() + "/raw/notification");
            if (TextUtils.isEmpty(str4)) {
                showSmallNotification(builder, R.mipmap.logo, str, str2, str3, activity, parse);
                playNotificationSound();
            } else if (str4 != null && str4.length() > 4 && Patterns.WEB_URL.matcher(str4).matches()) {
                Bitmap bitmapFromURL = getBitmapFromURL(str4);
                if (bitmapFromURL != null) {
                    showBigNotification(bitmapFromURL, builder, R.mipmap.logo, str, str2, str3, activity, parse);
                } else {
                    showSmallNotification(builder, R.mipmap.logo, str, str2, str3, activity, parse);
                }
            }
        }
    }
}
