/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.data;

import java.net.URL;
import java.util.Date;

/**
 * Describe a flash card.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
public class Card {

    // identifier in the database
    public int id;
    // the cards title
    public String title;
    // urls pointing to the files the card has
    public URL side1;
    public URL side2;
    // the current e-factor for this card
    public float eFactor = 2.5f;
    public int count = 0;
    public Date lastTime;
    public int interval;
    
    public Card(
        int id, String title, URL side1, URL side2, float eFactor) {
      this.id = id;
      this.title = title;
      this.side1 = side1;
      this.side2 = side2;
      this.eFactor = eFactor;
    }

    public Card(String title, URL side1, URL side2) {
      this.title = title;
      this.side1 = side1;
      this.side2 = side2;
      this.eFactor = 0;
    }

    public int getId() {
        return id;
    }

    public float getEFactor() {
        return eFactor;
    }

    public void setEFactor(float factor) {
        eFactor = factor;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
