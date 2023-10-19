package com.coloring.page;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerSoundAndMusic {
     Context context;
     MediaPlayer mPlayer1;

    public void destroyMusic() {
        this.mPlayer1.release();
    }

    public void instializeMusic(Context context2, int i) {
        this.context = context2;
        try {
            this.mPlayer1 = MediaPlayer.create(context2, i);
            this.mPlayer1.setLooping(true);
            this.mPlayer1.setAudioStreamType(3);
        } catch (Exception unused) {
        }
    }

    public void pauseMainMusic() {
        MediaPlayer mediaPlayer = this.mPlayer1;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mPlayer1.pause();
        }
    }

    public void startMainMusic() {
        MediaPlayer mediaPlayer = this.mPlayer1;
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !com.coloring.page.MyConstant.mute) {
            this.mPlayer1.start();
        }
    }
}
