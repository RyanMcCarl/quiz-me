/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.data;

import java.net.URL;

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
    // the deck this card is in 0-n
    public int deck;
    
    public Card(
        int id, String title, URL side1, URL side2, int deck) {
      this.id = id;
      this.title = title;
      this.side1 = side1;
      this.side2 = side2;
      this.deck = deck;
    }

    public Card(String title, URL side1, URL side2) {
      this.title = title;
      this.side1 = side1;
      this.side2 = side2;
      this.deck = 0;
    }

    public int getId() {
        return this.id;
    }
}
