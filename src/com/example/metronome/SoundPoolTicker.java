package com.example.metronome;

/**
 * Created by JG on 2015-12-06.
 */


import android.content.Context;
import android.media.SoundPool;
import android.os.Handler;

import static android.media.AudioManager.STREAM_MUSIC;

public class SoundPoolTicker {

    private SoundPool soundpool;

    private Context context;
    private Handler handler;

    private int normalTickId, strongTickId;


    public SoundPoolTicker (Context context) {
        this.soundpool = new SoundPool(2, STREAM_MUSIC, 0);
        this.strongTickId = soundpool.load(context, R.raw.hi_click, 1);
        this.normalTickId = soundpool.load(context, R.raw.lo_click, 1);
        this.context = context;
        this.handler = new Handler();
    }

    public void release(){
        this.soundpool.release();
    }

    public void play(int tickId, int m, int n, int o, int p, int q){
        soundpool.play(tickId, m, n, o, p, q);


    }

    public int getNormalTickId(){
        return normalTickId;
    }

    public int getStrongTickId(){
        return strongTickId;
    }

}
