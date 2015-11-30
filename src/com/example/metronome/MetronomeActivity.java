package com.example.metronome;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.view.SurfaceHolder.Callback;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.example.metronome.*;
/**
 * @author Paweł Łyżwa
 * @author Joanna Grochal
 */
public class MetronomeActivity extends Activity {

    private Ticker ticker;
    private long lastTapTime = 0;

    //interface elements
    EditText bpmEditText;
    Switch soundSwitch, firstBeatSwitch;
    Spinner timeSignSpinner;
    DotsSurfaceView dots;
    ToggleButton startStopButton;

    private int screenWidthPx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metronome);

        dots = (DotsSurfaceView) findViewById(R.id.dots);
        ticker = new Ticker(this, dots);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        /*
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidthPx = size.x;
        */

        //let's find dem buttons and the rest
        bpmEditText = (EditText) findViewById(R.id.bpmEditText);
        bpmEditText.setText(Integer.toString(ticker.getBPM()));

        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        firstBeatSwitch = (Switch) findViewById(R.id.firstBeatSwitch);
        timeSignSpinner = (Spinner) findViewById(R.id.timeSignSpinner);
        startStopButton = (ToggleButton) findViewById(R.id.startStopButton);



        //button related actions
        findViewById(R.id.plusSixButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() + 6);
            }
        });
        findViewById(R.id.plusOneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() + 1);
            }
        });
        findViewById(R.id.plusThirtyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() + 30);
            }
        });
        findViewById(R.id.minusSixButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() - 6);
            }
        });
        findViewById(R.id.minusOneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() - 1);
            }
        });
        findViewById(R.id.minusThirtyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() - 30);
            }
        });
        findViewById(R.id.divideTwoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() / 2);
            }
        });
        findViewById(R.id.divideThreeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() / 3);
            }
        });
        findViewById(R.id.timesThreeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() * 3);
            }
        });
        findViewById(R.id.timesTwoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBPM(ticker.getBPM() * 2);
            }
        });

        timeSignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ticker.setMeasure(Integer.valueOf(timeSignSpinner.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startStopButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    ticker.start();
                    startStopButton.setTextOn("Stop");

                } else {
                    // The toggle is disabled
                    ticker.stop();
                    startStopButton.setTextOff("Start");
                }
            }
        });

        findViewById(R.id.tapButton).setOnClickListener(new View.OnClickListener() {
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
                ticker.setAudible(b);
                firstBeatSwitch.setEnabled(b);
            }
        });

        bpmEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    setBPM(Integer.parseInt(bpmEditText.getText().toString()));
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            bpmEditText.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });

        firstBeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ticker.setFirstBeat(b);
            }
        });
    }

    public void setBPM(int bpm) {
        ticker.setBPM(bpm);
        bpmEditText.setText(Integer.toString(ticker.getBPM()));
    }
    private static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}