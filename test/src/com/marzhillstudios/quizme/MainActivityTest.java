/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import com.marzhillstudios.quizme.R;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;

import android.app.Activity;
import android.view.View;
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
    protected View mainLayout;
    protected View quizLayout;
    protected View managerLayout;
    
    public MainActivityTest() {
        super("com.marzhillstudios.quizme", MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        activity = this.getActivity();
        launchQuizBtn = (Button) activity.findViewById(R.id.LaunchQuizBtn);
        launchCardManagerBtn = (Button) activity.findViewById(R.id.launchManagerBtn);
        mainLayout = (View) activity.findViewById(R.id.MainLayout);
        quizLayout = (View) activity.findViewById(R.id.QuizLayout);
        managerLayout = (View) activity.findViewById(R.id.ManagerLayout);
        
    }

    public void testPreconditions() throws Exception {
        assertNotNull(mainLayout);
        assertNotNull(launchQuizBtn);
        assertNotNull(launchCardManagerBtn);
    }

    protected void testLaunchQuizBtnLaunchesQuizActivity() {
        // TODO(jwall): figure out how to actually test this.
        activity.runOnUiThread(
          new Runnable() {
              public void run() {
                  launchQuizBtn.requestFocus();
              }
          }
        );

        TouchUtils.clickView(this, launchQuizBtn);
        // TODO(jwall): check that QuizActivity has started.
        ViewAsserts.assertOnScreen(launchQuizBtn, quizLayout); 
    }

    public void testLaunchManagerBtnLaunchesCardManagerActivity() throws Exception {
        /*
        activity.runOnUiThread(
          new Runnable() {
              public void run() {
                  launchCardManagerBtn.requestFocus();
              }
          }
        );

        TouchUtils.clickView(this, launchCardManagerBtn);
        getInstrumentation().waitForIdle(
          new Runnable() {
              public void run() {
                  ViewAsserts.assertOnScreen(launchCardManagerBtn, managerLayout);
                  // TODO(jwall): check that QuizActivity has started.
              }
          }
        );
        */
    }
}
