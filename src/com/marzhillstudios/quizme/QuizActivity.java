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
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Quizzes the user with the currently scheduled cards.
 *
 *  @author Jeremy Wall <jeremy@marzhillstudios.com>
 *  
 */
public class QuizActivity extends Activity {
    
    private CardDatabase db;
    private List<Card> cards;
    
    private LinearLayout quizView;
    private LinearLayout cardView;
    private Button startBtn;
    private Button stopBtn;
    private Button seeAnswerBtn;
    private TextView textSideViewer;
    private ImageView imgSideViewer;
    
    private int currentIndex = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Resources res = getResources();
        super.onCreate(savedInstanceState);
        quizView = (LinearLayout) getLayoutInflater().inflate(R.layout.quiz, null);
        cardView = (LinearLayout) getLayoutInflater().inflate(R.layout.card_view, null);
        setContentView(quizView);
        db = new CardDatabase(this);
        // TODO(jwall): onCreate or onStart?
        final List<Card> cards = db.cursorToCards(db.getCardsForQuiz());
        setCards(cards);
        
        startBtn = new Button(this);
        startBtn.setText(res.getString(R.string.StartQuizButtonText));
        stopBtn = new Button(this);
        stopBtn.setText(res.getString(R.string.StopQuizButtonText));
        seeAnswerBtn = new Button(this);
        seeAnswerBtn.setText(res.getString(R.string.SeeAnswerButtonText));
        textSideViewer = new TextView(this);
        imgSideViewer = new ImageView(this);
        
        startBtn.setText(res.getString(R.string.StartQuizButtonText));
        stopBtn.setText(res.getString(R.string.StopQuizButtonText));
        quizView.addView(startBtn, 0);
        
        OnClickListener startClickListener = new OnClickListener() {
        	public void onClick(View v) {
				Card currentCard = cards.get(currentIndex);
				// We need to show a card View
				showStopButton();
				quizView.addView(cardView, 1);
				// card view shows side 1 first
				if (currentCard.getSide1Type() == Card.TEXT_TYPE) {
					// we show a textSideViewer
					textSideViewer.setText(currentCard.getSide1Text());
					cardView.addView(textSideViewer);
				} else {
					// we show an imageSideViewer
					imgSideViewer.setImageURI(currentCard.getSide1URI());
					cardView.addView(imgSideViewer);
				}
				// and a see answer button.
				cardView.addView(seeAnswerBtn, 1);
		        
		        // after rate activity returns we change card view for next card.
			}
        };
        
        // TODO(jwall): on see answer button card view launches a rate activity
        startBtn.setOnClickListener(startClickListener);
        OnClickListener stopClickListener = new OnClickListener() {
        	public void onClick(View v) {
				// TODO Auto-generated method stub
				showStartButton();
				quizView.removeView(cardView);
				// start our cleanup here.
			}
        };
        stopBtn.setOnClickListener(stopClickListener);
        
        //cv.removeView(stopBtn);
        // after all cards we get next set of cards again.
        // repeat until user quits or we return no more cards.
    }

    private void showStopButton() {
    	quizView.removeView(startBtn);
        quizView.addView(stopBtn, 0);
    }
    
    private void showStartButton() {
    	quizView.removeView(stopBtn);
        quizView.addView(startBtn, 0);
    }
    
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
