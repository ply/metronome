package com.example.metronome;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import android.widget.*;

import static com.example.metronome.R.*;

public class Metronome extends Activity {

    //interface elements
    EditText bpmEditText;

    Button tapButton;

    Switch soundSwitch;
    Switch firstBeatSwitch;
    Button plusSixButton;
    Button plusOneButton;
    Button plusThirtyButton;
    Button minusSixButton;
    Button minusOneButton;
    Button minusThirtyButton;
    Button divideThreeButton;
    Button divideTwoButton;
    Button timesTwoButton;
    Button timesThreeButton;
    Spinner timeSignSpinner;
    ToggleButton startStopButton;





    public final int DEFAULT_BPM = 120;
    public final int MAX_BPM = 900;
    public final int MIN_BPM = 20;
    public final int DEFAULT_MEASURE = 4;
    public int bpm;
    public int measure;
    public long timeMs;
    public long differenceMs;

    public boolean audible;
    public boolean running;


    //we'll need that for the Dots class
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

        bpm = DEFAULT_BPM;
        measure = DEFAULT_MEASURE;

        audible = false;
        running = false;

        timeMs = 0;

        //let's find dem buttons and the rest
        bpmEditText = (EditText) findViewById(R.id.bpmEditText);
        bpmEditText.setText(Integer.toString(bpm));

        soundSwitch = (Switch) findViewById(id.soundSwitch);
        firstBeatSwitch = (Switch) findViewById(id.firstBeatSwitch);
        //todo if we want first beat accent on then we want sound on as well?

        plusSixButton = (Button) findViewById(R.id.plusSixButton);
        plusOneButton = (Button) findViewById(R.id.plusOneButton);
        plusThirtyButton = (Button) findViewById(R.id.plusThirtyButton);
        minusSixButton = (Button) findViewById(R.id.minusSixButton);
        minusOneButton = (Button) findViewById(R.id.minusOneButton);
        minusThirtyButton = (Button) findViewById(R.id.minusThirtyButton);
        divideThreeButton = (Button) findViewById(R.id.divideThreeButton);
        divideTwoButton = (Button) findViewById(R.id.divideTwoButton);
        timesTwoButton = (Button) findViewById(R.id.timesTwoButton);
        timesThreeButton = (Button) findViewById(R.id.timesThreeButton);

        timeSignSpinner = (Spinner) findViewById(R.id.timeSignSpinner);

        startStopButton = (ToggleButton) findViewById(R.id.startStopButton);

        tapButton = (Button) findViewById(R.id.tapButton);

        //button related actions
        plusSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm + 6;
                updateBPM();
            }
        });
        plusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm + 1;
                updateBPM();
            }
        });
        plusThirtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm + 30;
                updateBPM();
            }
        });
        minusSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm - 6;
                updateBPM();
            }
        });
        minusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm - 1;
                updateBPM();
            }
        });
        minusThirtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm - 30;
                updateBPM();
            }
        });
        divideTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm / 2;
                updateBPM();
            }
        });
        divideThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm / 3;
                updateBPM();
            }
        });
        timesThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm * 3;
                updateBPM();
            }
        });
        timesTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpm = bpm * 2;
                updateBPM();
            }
        });
        timeSignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                measure = Integer.valueOf(timeSignSpinner.getSelectedItem().toString());
                //todo przeliczenie ilości kółek
                //test
                Toast.makeText(getApplicationContext(),
                        "Measure selected : " + Integer.toString(measure),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startStopButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    running = true;
                    startStopButton.setTextOn("Stop");
                } else {
                    // The toggle is disabled
                    running = false;
                    startStopButton.setTextOff("Start");
                }
            }
        });

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                differenceMs = SystemClock.elapsedRealtime() - timeMs;
                timeMs = SystemClock.elapsedRealtime();
                bpm = 60000 / safeLongToInt(differenceMs);
                updateBPM();
            }
        });


    }

    public void updateBPM(){

        if (bpm < MIN_BPM) { bpm = MIN_BPM; }
        else if (bpm > MAX_BPM) { bpm = MAX_BPM; }
        else {}

        bpmEditText.setText(Integer.toString(bpm));

        //todo przeliczenie co ile ms tick
    }


    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

}