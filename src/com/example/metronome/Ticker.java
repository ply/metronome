package com.example.metronome;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;

import java.io.Closeable;

/**
 * Metronome backend - asynchronous task responsible for executing metronome tick methods (sound)
 */
public class Ticker implements Closeable, Runnable {
    private Constants constants = new Constants();

    private int bpm = constants.getDefaultBpm();

    private int measure;

    private boolean audible = false;
    private boolean running = false;
    private boolean firstbeat = false;
    private int currentBeat;
    private long nextTickTime = 0;

    private Context context;
    private Handler handler;
    private DotsSurfaceView dots;
    private SoundPoolTicker soundpoolticker;

    public Ticker (Context context, DotsSurfaceView dots) {
        this.context = context;
        this.handler = new Handler();
        this.soundpoolticker = new SoundPoolTicker(context);
        this.dots = dots;
    }
    public void close() {
        this.soundpoolticker.release();
    }

    @Override
    public void run() {
        if (!this.running) { return; }
        if (this.nextTickTime + 60e3/this.bpm < SystemClock.uptimeMillis()) {
            this.nextTickTime = SystemClock.uptimeMillis();
        } else {
            while (SystemClock.uptimeMillis() < this.nextTickTime) {}
        }
        this.currentBeat = this.currentBeat % this.measure + 1;

        if (this.audible) {
            soundpoolticker.play((this.firstbeat && this.currentBeat == 1));
        }
        dots.setCount(currentBeat);

        this.nextTickTime += 60e3/this.bpm;
        handler.postAtTime(this, this.nextTickTime - 40);
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

    public void setMeasure(int measure) {
        if (measure < 2 && measure > 7) throw new IllegalArgumentException();
        this.measure = measure;
        this.currentBeat = 0;
        dots.setParameters(currentBeat, measure);
    }

    public boolean getFirstBeat() {
        return firstbeat;
    }

    public void setFirstBeat(boolean firstbeat) {
        this.firstbeat = firstbeat;
    }

    public void start () {
        this.running = true;
        this.currentBeat = 0;
        this.run();
    }

    public void stop () {
        this.running = false;
        dots.setCount(0);
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getBPM() { return this.bpm; }

    public void setBPM(int bpm) {
        if (bpm < constants.getMinBpm()) { bpm = constants.getMinBpm(); }
        else if (bpm > constants.getMaxBpm()) { bpm = constants.getMaxBpm(); }
        this.bpm = bpm;
    }
    public int getCurrentBeat() { return currentBeat; }
    public int getMeasure() { return measure; }
}
