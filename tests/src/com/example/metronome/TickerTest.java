package com.example.metronome;

import android.test.AndroidTestCase;

import android.content.Context;

/**
 * Tests for Ticker
 */
public class TickerTest extends AndroidTestCase {

    Ticker ticker;
    Context context;
    DotsSurfaceView dots;

    public void setUp() throws Exception {
        ticker = new Ticker(context, dots);
    }

    public void tearDown() throws Exception {

    }

    public void testRun() throws Exception {
        assertFalse(ticker.isRunning());
        ticker.start();
        assertTrue(ticker.isRunning());
        ticker.start();
        assertTrue(ticker.isRunning());
        ticker.stop();
        assertFalse(ticker.isRunning());
        ticker.stop();
        assertFalse(ticker.isRunning());
        ticker.start();
        assertTrue(ticker.isRunning());
        ticker.stop();
        assertFalse(ticker.isRunning());
    }

    public void testIsAudible() throws Exception {
        assertFalse(ticker.isAudible());
        ticker.enableSound();
        assertTrue(ticker.isAudible());
        ticker.disableSound();
        assertFalse(ticker.isAudible());

    }

    public void testSetBPM(){
        assertTrue(ticker.getBPM() == 2);
        ticker.setBPM(5);
        assertFalse(ticker.getBPM() == 2);
        ticker.setBPM(6);
        assertTrue(ticker.getBPM() == 6);
        ticker.setBPM(5);
        assertFalse(ticker.getBPM() == 6);
        ticker.setBPM(2);
        assertTrue(ticker.getBPM() == 2);
    }

    public void testSetMeasure(){
        assertTrue(ticker.getMeasure() == 120);
        ticker.setMeasure(60);
        assertFalse(ticker.getMeasure() == 120);
        ticker.setMeasure(300);
        assertFalse(ticker.getMeasure() == 60);
        ticker.setMeasure(300);
        assertTrue(ticker.getMeasure() == 300);
    }
}