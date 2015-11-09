package com.example.metronome;

/**
 * Metronome backend
 */
public class MetronomeBackend {
    public final int DEFAULT_BPM = 120;
    public final int MAX_BPM = 900;
    public final int MIN_BPM = 20;
    public final int DEFAULT_MEASURE = 4;
    private int bpm;

    private int measure = DEFAULT_MEASURE;

    private boolean audible = false;
    private boolean running = false;
    private boolean firstbeat = false;

    public boolean isAudible() {
        return audible;
    }

    public void enableSound () {
        this.audible = true;
        // TODO
    }

    public void disableSound() {
        this.audible = false;
        // TODO
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
        // TODO
    }

    public void stop () {
        this.running = false;
        // TODO
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getBPM() { return this.bpm; }

    public void setBPM(int bpm) {
        if (bpm < MIN_BPM) { bpm = MIN_BPM; }
        else if (bpm > MAX_BPM) { bpm = MAX_BPM; }

        this.bpm = bpm;
        //todo przeliczenie co ile ms tick
    }
}
