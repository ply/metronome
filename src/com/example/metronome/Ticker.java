package com.example.metronome;

import android.content.Context;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;

import java.io.Closeable;

import static android.media.AudioManager.STREAM_MUSIC;

/**
 * Metronome backend - asynchronous task responsible for executing metronome tick methods (sound)
 */
public class Ticker extends AsyncTask<Void, Void, Void> implements Closeable {
    public final int DEFAULT_BPM = 120;
    public final int MAX_BPM = 900;
    public final int MIN_BPM = 20;
    public final int DEFAULT_MEASURE = 4;
    private int bpm = DEFAULT_BPM;

    private int measure = DEFAULT_MEASURE;

    private boolean audible = false;
    private boolean running = false;
    private boolean firstbeat = false;
    private int nextBeat;
    private long nextTickTime = 0;

    private Context context;
    private SoundPool soundpool;
    private int normalTickId, strongTickId;

    public Ticker (Context context) {
        this.context = context;
        this.soundpool = new SoundPool(2, STREAM_MUSIC, 0);
        this.strongTickId = soundpool.load(context, R.raw.hi_click, 1);
        this.normalTickId = soundpool.load(context, R.raw.lo_click, 1);
    }

    public void close() {
        this.soundpool.release();
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (this.nextTickTime + 60e3/this.bpm < SystemClock.uptimeMillis()) {
            this.nextTickTime = SystemClock.uptimeMillis();
        } else {
            while (SystemClock.uptimeMillis() < this.nextTickTime) {}
        }

        if (this.audible) {
            int tickId;
            if (this.firstbeat && this.nextBeat == 1) {
                tickId = this.strongTickId;
            } else {
                tickId = this.normalTickId;
            }
            soundpool.play(tickId, 1, 1, 1, 0, 1);
        }

        // TODO: add drawing circles (Dots)

        this.nextTickTime += 60e3/this.bpm;
        this.nextBeat = this.nextBeat % this.measure + 1;
        try {
            Thread.sleep(this.nextBeat - SystemClock.elapsedRealtime() - 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.execute();
        return null;
    }

    public boolean isAudible() {
        return audible;
    }

    public void enableSound () {
        this.audible = true;
    }

    public void disableSound() {
        this.audible = false;
    }

    public void setAudible(boolean audible) {
        if(audible) {
            enableSound();
        } else {
            disableSound();
        }
    }

    public int getMeasure() {
        return measure;
    }

    public void setMeasure(int measure) {
        this.measure = measure;
        // TODO
    }

    public boolean getFirstBeat() {
        return firstbeat;
    }

    public void setFirstBeat(boolean firstbeat) {
        this.firstbeat = firstbeat;
    }

    public void start () {
        this.running = true;
        this.nextBeat = 1;
        this.execute();
    }

    public void stop () {
        this.cancel(false);
    }

    protected void onCanceled(Object o) {
        this.running = false;
        this.nextTickTime = 0;
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getBPM() { return this.bpm; }

    public void setBPM(int bpm) {
        if (bpm < MIN_BPM) { bpm = MIN_BPM; }
        else if (bpm > MAX_BPM) { bpm = MAX_BPM; }
        this.bpm = bpm;
    }
}
