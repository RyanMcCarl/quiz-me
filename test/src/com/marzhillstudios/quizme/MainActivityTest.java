/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import com.marzhillstudios.quizme.R;

import android.test.ActivityInstrumentationTestCase2;

import android.app.Activity;
import android.widget.Button;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.marzhillstudios.quizme.MainActivityTest \
 * com.marzhillstudios.quizme.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    protected Activity activity;
    protected Button launchQuizBtn;
    protected Button launchCardManagerBtn;
    
    public MainActivityTest() {
        super("com.marzhillstudios.quizme", MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        activity = this.getActivity();
        launchQuizBtn = (Button) activity.findViewById(R.id.LaunchQuizBtn);
        launchCardManagerBtn = (Button) activity.findViewById(R.id.launchManagerBtn);
        
    }

    protected void testPreconditions() throws Exception {
        assertNotNull(launchQuizBtn);
        assertNotNull(launchCardManagerBtn);
    }
}
