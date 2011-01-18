/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.algorithm;

import com.marzhillstudios.quizme.data.Card;

import java.lang.Math;

/**
 * An implementation of the Spaced Repetition algorithm.
 *
 * The algorithm is inspired by the SuperMemo 2 algorithm:
 * http://www.supermemo.com/english/ol/sm2.htm
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */

public class TestMe {

  public static final int MAX_QUALITY = 5;
  public static final int QUALITY_SUBTRACTOR = 5;
  public static final float E_FACTOR_FLOOR = 1.3f;
  
  /**
   * Score a Card and set it's correct E-Factor.
   */
  public static void scoreCard(Card card, int quality) {
      float qFactor = (QUALITY_SUBTRACTOR - quality);
      float newFactor = card.getEFactor() + (0.1f - qFactor * (0.08f + qFactor * 0.02f));
      if (newFactor < E_FACTOR_FLOOR) {
          newFactor = E_FACTOR_FLOOR;
      }
      card.setEFactor(newFactor);
  }

  public static void calcuateInterval(Card card) {
      if (card.getEFactor() < 3) {
          card.setCount(1);
      }
      int count = card.getCount();
      int interval = 1;
      if (count == 2) {
          interval = 6;
      } else if (count > 2) {
         interval =  Math.round(card.getInterval() * card.getEFactor());
      }
      card.setInterval(interval);
  }
}

