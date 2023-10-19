package com.coloring.page;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

public class SoundManager {
    private static SoundManager instance;
    private static AudioManager mAudioManager;
    public static Context mContext;
    public static SoundPool mSoundPool;
    public static HashMap<Integer, Integer> mSoundPoolMap;

    private SoundManager() {
    }

    public static void addSound(int i, int i2) {
        mSoundPoolMap.put(Integer.valueOf(i), Integer.valueOf(mSoundPool.load(mContext, i2, 1)));
    }

    public static void cleanup() {
        mSoundPool.release();
        mSoundPool = null;
        mSoundPoolMap.clear();
        mAudioManager.unloadSoundEffects();
        instance = null;
    }

    public static synchronized SoundManager getInstance() {
        SoundManager soundManager;
        synchronized (SoundManager.class) {
            if (instance == null) {
                instance = new SoundManager();
            }
            soundManager = instance;
        }
        return soundManager;
    }

    public static void initSounds(Context context) {
        mContext = context;
        mSoundPool = Build.VERSION.SDK_INT >= 21 ? new SoundPool.Builder().setMaxStreams(4).build() : new SoundPool(4, 3, 1);
        mSoundPoolMap = new HashMap<>();
        mAudioManager = (AudioManager) mContext.getSystemService("audio");
    }

    public static void muteSound() {
        mSoundPool.setVolume(1, 0.0f, 0.0f);
    }

    public static void playLoopedSound(int i) {
        float streamVolume = ((float) mAudioManager.getStreamVolume(3)) / ((float) mAudioManager.getStreamMaxVolume(3));
        mSoundPool.play(mSoundPoolMap.get(Integer.valueOf(i)).intValue(), streamVolume, streamVolume, 1, -1, 1.0f);
    }

    public static void playSound(int i, float f) {
        try {
            mSoundPool.play(mSoundPoolMap.get(Integer.valueOf(i)).intValue(), 1.0f, 1.0f, 1, 0, f);
        } catch (Exception e) {
            System.out.print("testing");
            e.printStackTrace();
        }
    }

    public static void stopSound(int i) {
        mSoundPool.stop(mSoundPoolMap.get(Integer.valueOf(i)).intValue());
    }
}
