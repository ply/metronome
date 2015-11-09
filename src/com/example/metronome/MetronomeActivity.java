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

public class MetronomeActivity extends Activity {

    private MetronomeBackend backend;
    private long lastTapTime = 0;

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

    //we'll need that for the Dots class
    private int screenWidthPx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        backend = new MetronomeBackend();

        super.onCreate(savedInstanceState);
        setContentView(layout.metronome);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidthPx = size.x;

        //let's find dem buttons and the rest
        bpmEditText = (EditText) findViewById(R.id.bpmEditText);
        bpmEditText.setText(Integer.toString(backend.getBPM()));

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
                setBPM(backend.getBPM() + 6);
            }
        });
        plusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() + 1);
            }
        });
        plusThirtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() + 30);
            }
        });
        minusSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() - 6);
            }
        });
        minusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() - 1);
            }
        });
        minusThirtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() - 30);
            }
        });
        divideTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() / 2);
            }
        });
        divideThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() / 3);
            }
        });
        timesThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() * 3);
            }
        });
        timesTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(backend.getBPM() * 2);
            }
        });
        timeSignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                backend.setMeasure(Integer.valueOf(timeSignSpinner.getSelectedItem().toString()));
                // TODO przeliczenie ilości kółek
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startStopButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    backend.start();
                    startStopButton.setTextOn("Stop");
                } else {
                    // The toggle is disabled
                    backend.stop();
                    startStopButton.setTextOff("Start");
                }
            }
        });

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newbpm = 60000 / safeLongToInt(SystemClock.elapsedRealtime() - lastTapTime);
                lastTapTime = SystemClock.elapsedRealtime();
                if (newbpm >= 15) {
                    setBPM(newbpm);
                }
            }
        });

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                backend.setAudible(b);
                firstBeatSwitch.setEnabled(b);
            }
        });

        firstBeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                backend.setFirstBeat(b);
            }
        });
    }

    public void setBPM(int bpm) {
        backend.setBPM(bpm);
        bpmEditText.setText(Integer.toString(backend.getBPM()));
    }
    private static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}