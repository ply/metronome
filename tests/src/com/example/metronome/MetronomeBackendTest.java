package com.example.metronome;

import android.test.AndroidTestCase;

/**
 * Tests for MetronomeBackend
 */
public class MetronomeBackendTest extends AndroidTestCase {

    MetronomeBackend backend;

    public void setUp() throws Exception {
        backend = new MetronomeBackend();
    }

    public void tearDown() throws Exception {

    }

    public void testRun() throws Exception {
        assertFalse(backend.isRunning());
        backend.start();
        assertTrue(backend.isRunning());
        backend.start();
        assertTrue(backend.isRunning());
        backend.stop();
        assertFalse(backend.isRunning());
        backend.stop();
        assertFalse(backend.isRunning());
        backend.start();
        assertTrue(backend.isRunning());
        backend.stop();
        assertFalse(backend.isRunning());
    }

}