/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.data;

import java.util.Date;

/**
 * Describe a flash card.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
public class Card {

    public static final int IMAGE_TYPE = 0;
    public static final int TEXT_TYPE = 1;

    // identifier in the database
    public int id;
    // the cards title
    public String title;
    
    public int side1Type;
    public int side2Type;
    public String side1;
    public String side2;
    // the current e-factor for this card
    public float eFactor = 2.5f;
    public int count = 0;
    public Date lastTime;
    public int interval;
    
    public Card(
        int id, String title, String side1, String side2, float eFactor) {
      this.id = id;
      this.title = title;
      this.side1 = side1;
      this.side2 = side2;
      this.eFactor = eFactor;
    }

    public Card(String title, String side1, String side2) {
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
    
    public void setLastTime(Long t) {
    	this.lastTime = new Date(t);
    }
    
    public Date getLastTime() {
    	return lastTime;
    }
    
    public Long getLastTimeMillis() {
    	return lastTime.getTime();
    }
}
