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
 * NewCardCreatorActivityTest unittests.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
public class NewCardCreatorActivityTest.javaTest extends ActivityInstrumentationTestCase2<NewCardCreatorActivityTest.java> {

    public NewCardCreatorActivityTest.javaTest() {
        super("com.marzhillstudios.quizme", NewCardCreatorActivityTest.java.class);
    }

  protected void setUp() throws Exception {
    super.setUp();
    activity = this.getActivity();
  }
}

