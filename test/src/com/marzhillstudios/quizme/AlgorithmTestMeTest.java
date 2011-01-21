
/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import com.marzhillstudios.quizme.algorithm.TestMe;
import com.marzhillstudios.quizme.data.Card;

import android.test.ActivityInstrumentationTestCase2;

import java.net.URL;

import junit.framework.TestCase;

/**
 * TODO:Class Description here.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */

public class AlgorithmTestMeTest extends TestCase {

  private URL fakeURL;
   
  protected void setUp() throws Exception {
    super.setUp();
    fakeURL = new URL("file:///fake/url");
  }

  public void testScoreCardHonorsFloor() throws Exception {
      Card card = new Card("fake title", fakeURL, fakeURL);
      card.setEFactor(1.0f);
      TestMe.scoreCard(card, 1);
      assertEquals(1.3f, card.getEFactor());
  }

  public void testScoreCard() throws Exception {
      Card card = new Card("fake title", fakeURL, fakeURL);
      card.setEFactor(2.5f);
      TestMe.scoreCard(card, 3);
      assertTrue(card.getEFactor() >= 2.35f);
  }

  public void testCalculateIntervalForCountOne() throws Exception {
      Card card = new Card("fake title", fakeURL, fakeURL);
      card.setCount(1);
      TestMe.calculateInterval(card);
      assertEquals(1, card.getInterval());
  }

  public void testCalculateIntervalForCountTwo() throws Exception {
      Card card = new Card("fake title", fakeURL, fakeURL);
      card.setEFactor(3);
      card.setCount(2);
      TestMe.calculateInterval(card);
      assertEquals(6, card.getInterval());
  }

  public void testCalculateIntervalEFactorTooLow() throws Exception {
      Card card = new Card("fake title", fakeURL, fakeURL);
      card.setCount(2);
      card.setEFactor(2);
      TestMe.calculateInterval(card);
      assertEquals(1, card.getInterval());
      assertEquals(1, card.getCount());
  }

  public void testCalculateIntervalNoExceptionCases() throws Exception {
      Card card = new Card("fake title", fakeURL, fakeURL);
      card.setCount(3);
      card.setInterval(6);
      card.setEFactor(3);
      TestMe.calculateInterval(card);
      assertEquals(18, card.getInterval());
  }
}
