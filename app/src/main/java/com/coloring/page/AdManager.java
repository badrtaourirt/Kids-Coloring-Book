package com.coloring.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

public class AdManager {
    public static String appName = "com.coloring.page";
    public static String appStoreURI;
    public static String devStoreURI;
    public static String promoteAppName;
    public static int promoteCode;
    public static String promoteLink;
    public static Bitmap promoteMain;
    public static Bitmap promoteNo;
    public static Bitmap promoteYevs;
    com.coloring.page.MainActivity a;
    String b = "f_unicorncoloring.txt";
    String c;

    private class DownloadOperation extends AsyncTask<Void, Void, Void> {
        private DownloadOperation() {
        }



        public Void doInBackground(Void... voidArr) {
            if (!AdManager.this.checkInternetConnection()) {
                return null;
            }
            AdManager.this.readDialogGameFile();
            AdManager.this.downloadDialogImages();
            return null;
        }



        public void onPostExecute(Void voidR) {
            if (AdManager.this.checkInternetConnection()) {
                PrintStream printStream = System.err;
                printStream.println("getDialogeNoShow change if before" + AdManager.this.a.getPromoteCode() + "," + AdManager.promoteCode);
                if (AdManager.promoteCode != AdManager.this.a.getPromoteCode()) {
                    PrintStream printStream2 = System.err;
                    printStream2.println("getDialogeNoShow change if" + AdManager.this.a.getPromoteCode() + "," + AdManager.promoteCode);
                    com.coloring.page.MainActivity.sharedPreference_isShowNewApp.setDialogNoShow(false);
                } else {
                    PrintStream printStream3 = System.err;
                    printStream3.println("getDialogeNoShow change else" + AdManager.this.a.getPromoteCode() + "," + AdManager.promoteCode);
                }
                AdManager.this.a.setPromoteCode(AdManager.promoteCode);
            }
        }


        public void onPreExecute() {
            Log.e("sds","");
        }
    }

    public AdManager(com.coloring.page.MainActivity mainActivity) {
        new Handler();
        this.c = "https://gunjanappstudios.com/wp-content/uploads/AdManagerInterstitial/";
        this.a = mainActivity;
        checkGooglePlayInstalled();
    }

    private void checkGooglePlayInstalled() {
        try {
            this.a.getPackageManager().getPackageInfo("com.android.vending", 0);
            appStoreURI = "market://details?id=";
            devStoreURI = "market://search?q=pub:GunjanApps Studios";
        } catch (Exception unused) {
            appStoreURI = "http://play.google.com/store/apps/details?id=";
            devStoreURI = "http://play.google.com/store/search?q=GunjanApps Studios";
        }
    }

    private Bitmap downloadImage(String str) {
        try {
            InputStream openHttpConnection = openHttpConnection(str);
            if (openHttpConnection == null) {
                return null;
            }
            Bitmap decodeStream = BitmapFactory.decodeStream(openHttpConnection);
            openHttpConnection.close();
            return decodeStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean exists(String str) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            return httpURLConnection.getResponseCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private InputStream openHttpConnection(String str) {
        URLConnection openConnection = null;
        try {
            openConnection = new URL(str).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (openConnection instanceof HttpURLConnection) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                httpURLConnection.setAllowUserInteraction(false);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    return httpURLConnection.getInputStream();
                }
                return null;
            } catch (Exception unused) {
                try {
                    throw new IOException("Error connecting");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                throw new IOException("Not an HTTP connection");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean checkInternetConnection() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.a.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void downloadDialogImages() {
        if (promoteAppName != "") {
            promoteMain = downloadImage(this.c + promoteAppName + ".png");
            PrintStream printStream = System.err;
            printStream.println("getDialogeNoShow png image:" + promoteMain + "," + promoteAppName);
            if (promoteMain == null) {
                com.coloring.page.MainActivity.sharedPreference_isShowNewApp.setDialogNoShow(true);
            }
        }
    }

    public void readDialogGameFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(this.c + this.b).openStream()));
            Random random = new Random();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int parseInt = Integer.parseInt(bufferedReader.readLine());
            System.err.println("getDialogeNoShow reading flag:" + parseInt);
            if (parseInt == 0) {
                promoteCode = Integer.parseInt(bufferedReader.readLine());
                System.err.println("getDialogeNoShow reading:" + promoteCode);
                bufferedReader.readLine();
                int parseInt2 = Integer.parseInt(bufferedReader.readLine());
                int i = parseInt2;
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    }
                    arrayList.add(bufferedReader.readLine());
                    i = i2;
                }
                int i3 = parseInt2;
                while (true) {
                    int i4 = i3 - 1;
                    if (i3 > 0) {
                        arrayList2.add(bufferedReader.readLine());
                        i3 = i4;
                    } else {
                        int nextInt = random.nextInt(parseInt2);
                        promoteAppName = (String) arrayList2.get(nextInt);
                        promoteLink = (String) arrayList.get(nextInt);
                        return;
                    }
                }
            } else {
                promoteCode = parseInt;
                System.err.println("getDialogeNoShow reading 2:" + promoteCode);
                promoteAppName = bufferedReader.readLine();
                promoteLink = appStoreURI + promoteAppName;
                bufferedReader.close();
            }
        } catch (Exception unused) {
        }
    }

    public void start() {
        new DownloadOperation().execute(new Void[]{null, null, null});
    }
}
