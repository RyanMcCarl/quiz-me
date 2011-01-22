
/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import com.marzhillstudios.quizme.adapter.CardListAdapter;
import com.marzhillstudios.quizme.data.CardDatabase;
import com.marzhillstudios.quizme.util.L;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Test the CardManager Activity class.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */

public class CardManagerActivityTest extends ActivityInstrumentationTestCase2<CardManagerActivity> {

  private CardManagerActivity activity;
  private Button newCardBtn;
  private LinearLayout mainLayout;

  public CardManagerActivityTest() {
      super("com.marzhillstudios.quizme", CardManagerActivity.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    activity = this.getActivity();
    newCardBtn = (Button) activity.findViewById(R.id.NewCardButton);
    mainLayout = (LinearLayout) activity.findViewById(R.id.ManagerLayout);
  }

  public void testPreconditions() {
      assertNotNull(activity.getDb());
      assertNotNull(activity.getListAdapter());
      assertNotNull(activity.getListView());
      assertNotNull(mainLayout);
      assertNotNull(newCardBtn);
  }
  
}

