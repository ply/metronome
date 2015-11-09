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
    private int bpm;
    public int newbpm;
    public int measure;
    public long timeMs;
    public long differenceMs;

    public boolean audible;
    public boolean running;
    public boolean firstbeat;


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
        firstbeat = false;

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
                setBPM(bpm+6);
            }
        });
        plusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm + 1);
            }
        });
        plusThirtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm + 30);
            }
        });
        minusSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm - 6);
            }
        });
        minusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm - 1);
            }
        });
        minusThirtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm - 30);
            }
        });
        divideTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm/2);
            }
        });
        divideThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm / 3);
            }
        });
        timesThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm * 3);
            }
        });
        timesTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(bpm * 2);
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
                newbpm = 60000 / safeLongToInt(differenceMs);
                if (newbpm >= 15) {
                    setBPM(newbpm);
                }
            }
        });

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                audible = b;
                firstBeatSwitch.setEnabled(b);
                if(!b) {
                    firstbeat = false;
                } //<- this may be unnecessary tho

            }
        });

        firstBeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                firstbeat = b;
            }
        });


    }

    public void setBPM(int bpm) {
        if (bpm < MIN_BPM) { bpm = MIN_BPM; }
        else if (bpm > MAX_BPM) { bpm = MAX_BPM; }

        this.bpm = bpm;
        bpmEditText.setText(Integer.toString(this.bpm));

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