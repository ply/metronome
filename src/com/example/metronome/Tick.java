package com.example.metronome;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Asynchronous task responsible for executing metronome tick methods (sound)
 */
public class Tick extends AsyncTask<Void, Void, Void> {

    private Context context;

    public Tick(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // ticks happening here - call methods for sound (SoundPool?)
        // and drawing circles (Dots)

        return null;
    }
}
