package com.coloring.page;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.util.Log;

import java.util.Random;

public class MyMediaPlayer {
    MediaPlayer a = null;
    Context b;
    private String colorSoundString;

    public int length = 0;

    public MyMediaPlayer(Context context) {
        this.b = context;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    private String getRandomApplause() {
        int nextInt = new Random().nextInt(4) + 1;
        return nextInt != 1 ? nextInt != 2 ? nextInt != 3 ? nextInt != 4 ? nextInt != 5 ? "applause_excellent" : "applause_youdid" : "applause_terrific" : "applause_intelligent" : "applause_greatjob" : "applause_excellent";
    }

    public void StopMp() {
        MediaPlayer mediaPlayer = this.a;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                this.a.reset();
                this.a.release();
                this.a = null;
                this.length = 0;
            } catch (Exception e) {
                Log.d("error", e.toString());
            }
        }
    }

    public void StopMp(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                this.length = 0;
            } catch (Exception e) {
                Log.d("error", e.toString());
            }
        }
    }
    String str;
    public String getRandomColorSound() {

        switch (new Random().nextInt(14) + 1) {
            case 2:
                str = "colortouch2";
                break;
            case 3:
                str = "colortouch3";
                break;
            case 4:
                str = "colortouch4";
                break;
            case 5:
                str = "colortouch5";
                break;
            case 6:
                str = "colortouch6";
                break;
            case 7:
                str = "colortouch7";
                break;
            case 8:
                str = "colortouch8";
                break;
            case 9:
                str = "colortouch9";
                break;
            case 10:
                str = "colortouch10";
                break;
            case 11:
                str = "colortouch11";
                break;
            case 12:
                str = "colortouch12";
                break;
            case 13:
                str = "colortouch13";
                break;
            case 14:
                str = "colortouch14";
                break;
            case 15:
                str = "colortouch15";
                break;
            default:
                this.colorSoundString = "colortouch1";
                break;
        }
        this.colorSoundString = str;
        return this.colorSoundString;
    }

    public String getRandomMonsterSound() {
        int nextInt = new Random().nextInt(3) + 1;
        return nextInt != 1 ? nextInt != 2 ? "monstertouch3" : "monstertouch2" : "monstertouch1";
    }

    public String getRandomSelectArtSound() {
        switch (new Random().nextInt(7) + 1) {
            case 1:
                return "art1";
            case 2:
                return "art2";
            case 4:
                return "art4";
            case 5:
                return "art5";
            case 6:
                return "art6";
            case 7:
                return "art7";
            case 8:
                return "art8";
            default:
                return "art3";
        }
    }

    public void playClickSound() {
        StopMp();
        playSound(R.raw.click);
    }

    public void playColorRandomSound() {
        int identifier = this.b.getResources().getIdentifier(getRandomColorSound().toLowerCase(), "raw", this.b.getPackageName());
        if (identifier != 0) {
            playSound(identifier);
        }
    }

    public void playMonsterRandomSound() {
        int identifier = this.b.getResources().getIdentifier(getRandomMonsterSound().toLowerCase(), "raw", this.b.getPackageName());
        if (identifier != 0) {
            playSound(identifier);
        }
    }

    public void playSelectArtRandomSound() {
        int identifier = this.b.getResources().getIdentifier(getRandomSelectArtSound().toLowerCase(), "raw", this.b.getPackageName());
        if (identifier != 0) {
            playSound(identifier);
        }
    }

    public void playSound(int i) {
        this.a = MediaPlayer.create(this.b, i);
        if (this.a != null) {
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    PlaybackParams playbackParams = new PlaybackParams();
                    playbackParams.setPitch(1.18f);
                    this.a.setPlaybackParams(playbackParams);
                }
                this.a.seekTo(this.length);
                this.a.start();
                this.a.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        int unused = MyMediaPlayer.this.length = 0;
                    }
                });
            } catch (Exception e) {
                Log.d("error", e.toString());
            }
        }
    }

    public void speakApplause() {
        int identifier = this.b.getResources().getIdentifier(getRandomApplause().toLowerCase(), "raw", this.b.getPackageName());
        if (identifier != 0) {
            playSound(identifier);
        }
    }
}
