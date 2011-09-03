/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import java.util.List;

import com.marzhillstudios.quizme.data.Card;
import com.marzhillstudios.quizme.data.CardDatabase;

import android.app.Activity;
import android.os.Bundle;

/**
 * Quizzes the user with the currently scheduled cards.
 *
 *  @author Jeremy Wall <jeremy@marzhillstudios.com>
 *  
 */
public class QuizActivity extends Activity {
    
    @SuppressWarnings("unused")
    private CardDatabase db;
    private List<Card> cards;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CardDatabase(this);
        cards = db.cursorToCards(db.getCardsForQuiz());
        // now we need to go in a loop showing each card and scoring it
        // for each card which
        setContentView(R.layout.quiz);
    }
}
