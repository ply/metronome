package com.example.metronome;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.EditText;

import static com.example.metronome.R.*;

public class Metronome extends Activity {
    EditText bpmEditText;
    public final int DEFAULT_BPM = 120;
    public int bpm;
    //public boolean audible;
    //public boolean running;


    //do kółek
    public int screenWidthPx;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.metronome);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidthPx = size.x;

        bpmEditText = (EditText) findViewById(R.id.bpmEditText);
        bpmEditText.setText(Integer.toString(DEFAULT_BPM));

    }

    public void setBPM(int newBPM){
        bpm = newBPM;
        //todo ustaw edittext
        //todo przelicz co ile tick
    }


}