package com.example.metronome;

import android.content.Context;
import android.media.SoundPool;
import android.os.Handler;

import static android.media.AudioManager.STREAM_MUSIC;

public class SoundPoolTicker {

    private SoundPool soundpool;

    private Context context;

    private int normalTickId, strongTickId;


    public SoundPoolTicker (Context context) {
        this.soundpool = new SoundPool(2, STREAM_MUSIC, 0);
        this.strongTickId = soundpool.load(context, R.raw.hi_click, 1);
        this.normalTickId = soundpool.load(context, R.raw.lo_click, 1);
        this.context = context;
    }

    public void release(){
        this.soundpool.release();
    }

    public void play(boolean strong){
        soundpool.play(
                strong ? this.strongTickId : this.normalTickId, // soundID
                1.0f, 1.0f, // left and right volume
                1, // priority
                0, // no loop
                1.0f // playback rate
                );
    }
}
