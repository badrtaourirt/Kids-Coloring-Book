package com.coloring.page;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.PrintStream;

public class SharedPreference {
    public static final String PREFS_KEY_ADFREE = "pref_key_adfree";
    public static final String PREFS_KEY_AL = "pref_key";
    public static final String PREFS_KEY_IMAGE = "pref_key_image";
    public static final String PREFS_KEY_IMAGE_LINK = "pref_key_image_link";
    public static final String PREFS_KEY_IMAGE_NAME = "pref_key_image_name";
    public static final String PREFS_KEY_ISSHOWNEWAPP = "pref_key_isshownewapp";
    public static final String PREFS_KEY_LANG = "pref_key_lang";
    public static final String PREFS_KEY_NS = "pref_key_nevershow";
    public static final String PREFS_KEY_SOUND = "pref_key_sound";
    public static final String PREFS_NAME_ADFREE = "pref_name_adfree";
    public static final String PREFS_NAME_AL = "pref_name";
    public static final String PREFS_NAME_IMAGE = "pref_name_image";
    public static final String PREFS_NAME_IMAGE_LINK = "pref_name_image_link";
    public static final String PREFS_NAME_IMAGE_NAME = "pref_name_image_name";
    public static final String PREFS_NAME_ISSHOWNEWAPP = "pref_name_isshownewapp";
    public static final String PREFS_NAME_LANG = "pref_name_lang";
    public static final String PREFS_NAME_NS = "pref_name_nevershow";
    public static final String PREFS_NAME_SOUND = "pref_name_sound";
    public String PREFS_KEY = "";
    public String PREFS_NAME = "";
    private SharedPreferences sharedPrefisShow;

    public SharedPreference(String str, String str2) {
        this.PREFS_NAME = str;
        this.PREFS_KEY = str2;
    }


    public String getImageName(Context context) {
        return context.getSharedPreferences(PREFS_NAME_IMAGE_NAME, 0).getString(PREFS_KEY_IMAGE_NAME, "");
    }

    public int getValue(Context context) {
        return context.getSharedPreferences(this.PREFS_NAME, 0).getInt(this.PREFS_KEY, 0);
    }


    public void save(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(this.PREFS_NAME, 0).edit();
        edit.putInt(this.PREFS_KEY, i);
        edit.commit();
        edit.apply();
    }


    public void setDialogNoShow(boolean z) {
        PrintStream printStream = System.err;
        printStream.println(" MyApplication.getAppContext():" + com.coloring.page.MyApplication.getAppContext());
        this.sharedPrefisShow = com.coloring.page.MyApplication.getAppContext().getSharedPreferences(PREFS_NAME_ISSHOWNEWAPP, 0);
        SharedPreferences.Editor edit = this.sharedPrefisShow.edit();
        edit.putBoolean(PREFS_KEY_ISSHOWNEWAPP, z);
        edit.commit();
    }
}
