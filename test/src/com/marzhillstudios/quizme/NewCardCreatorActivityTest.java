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
import android.widget.Button;
import android.view.View;

/**
 * NewCardCreatorActivityTest unittests.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
public class NewCardCreatorActivityTest extends ActivityInstrumentationTestCase2<NewCardCreatorActivity> {

  private NewCardCreatorActivity activity;
  private Button side1ImgBtn;
  private Button side1TxtBtn;
  private Button side2ImgBtn;
  private Button side2TxtBtn;

  public NewCardCreatorActivityTest() {
      super("com.marzhillstudios.quizme", NewCardCreatorActivity.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    activity = this.getActivity();
    side1ImgBtn = (Button) activity.findViewById(R.id.NewCardSide1ImageBtn);
    side2ImgBtn = (Button) activity.findViewById(R.id.NewCardSide2ImageBtn);
    side1TxtBtn = (Button) activity.findViewById(R.id.NewCardSide1TextBtn);
    side2TxtBtn = (Button) activity.findViewById(R.id.NewCardSide2TextBtn);
  }

  public void testPreconditions() {
      assertNotNull(activity.getDb());
      assertNotNull(side1ImgBtn);
      assertNotNull(side2ImgBtn);
      assertNotNull(side1TxtBtn);
      assertNotNull(side2TxtBtn);
  }

}

