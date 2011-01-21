
/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;

import android.app.Activity;
import android.view.View;

/**
 * Test the CardManager Activity class.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */

public class CardManagerActivityTest extends ActivityInstrumentationTestCase2<CardManagerActivity> {

    private CardManagerActivity activity;

    public CardManagerActivityTest() {
        super("com.marzhillstudios.quizme", CardManagerActivity.class);
    }

  protected void setUp() throws Exception {
    super.setUp();
    activity = this.getActivity();
  }
}

