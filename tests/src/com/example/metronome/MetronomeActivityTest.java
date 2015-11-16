package com.example.metronome;

import android.test.ActivityUnitTestCase;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.example.metronome.MetronomeActivityTest \
 * com.example.metronome.tests/android.test.InstrumentationTestRunner
 */
public class MetronomeActivityTest extends ActivityUnitTestCase<MetronomeActivity> {

    public MetronomeActivityTest() {
        super(MetronomeActivity.class);
    }

}
