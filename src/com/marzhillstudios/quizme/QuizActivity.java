/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import java.util.List;

import com.marzhillstudios.quizme.data.Card;
import com.marzhillstudios.quizme.data.CardDatabase;
import com.marzhillstudios.quizme.util.L;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Quizzes the user with the currently scheduled cards.
 *
 *  @author Jeremy Wall <jeremy@marzhillstudios.com>
 *  
 */
public class QuizActivity extends Activity {
    
	protected static final int CARD_RATING_RESULT = 0;
	
	private CardDatabase db;
    private List<Card> cards;
    
    private LinearLayout quizView;
    private LinearLayout cardView;
    private Button startBtn;
    private Button stopBtn;
    private Button seeAnswerBtn;
    private TextView cardTitleViewer;
    private FrameLayout sideView;
    private TextView textSideViewer;
    private ImageView imgSideViewer;
    
    private int currentIndex = 0;
    
    // TODO(jwall): should we handle onSaveInstanceState?
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        final QuizActivity self = this;
    	Resources res = getResources();
        super.onCreate(savedInstanceState);
        quizView = (LinearLayout) getLayoutInflater().inflate(R.layout.quiz, null);
        cardView = (LinearLayout) getLayoutInflater().inflate(R.layout.card_view, null);
        setContentView(quizView);
        db = new CardDatabase(this);
        final List<Card> cards = db.cursorToCards(db.getCardsForQuiz());
        setCards(cards);
        
        startBtn = new Button(this);
        startBtn.setText(res.getString(R.string.StartQuizButtonText));
        stopBtn = new Button(this);
        stopBtn.setText(res.getString(R.string.StopQuizButtonText));
        seeAnswerBtn = (Button) cardView.findViewById(R.id.CardViewAnswerButton);
        cardTitleViewer = (TextView)  cardView.findViewById(R.id.CardViewTitleText);
        sideView = (FrameLayout) cardView.findViewById(R.id.CardViewSideFrame);
        textSideViewer = new TextView(this);
        imgSideViewer = new ImageView(this);
        
        startBtn.setText(res.getString(R.string.StartQuizButtonText));
        stopBtn.setText(res.getString(R.string.StopQuizButtonText));
        quizView.addView(startBtn, 0);
        
        OnClickListener startClickListener = new OnClickListener() {
        	public void onClick(View v) {
        		if (cards != null) {
        			Card currentCard = cards.get(currentIndex);
    				showStopButton();
    				quizView.addView(cardView, 1);
    				showCard(currentCard);
        		} else {
        			handleNoCards();
        		}
			}
        };
        
        OnClickListener stopClickListener = new OnClickListener() {
        	public void onClick(View v) {
				showStartButton();
				quizView.removeView(cardView);
			}
        };
        
        OnClickListener seeAnswerListener = new OnClickListener() {
        	public void onClick(View v) {
        		Card currentCard = self.getCards().get(currentIndex);
        		L.d(QuizActivity.class.getName(), "Seeing Answer for card index: %d, id: %d",
        				currentIndex, currentCard.getId());
        		Intent intent = new Intent(self, RateCardActivity.class);
        		intent.putExtra(RateCardActivity.CARD_RATING_INTENT_ID_KEY, currentCard.getId());
        		self.startActivityForResult(intent, CARD_RATING_RESULT);
        	}
        };
        
        startBtn.setOnClickListener(startClickListener);
        stopBtn.setOnClickListener(stopClickListener);
        seeAnswerBtn.setOnClickListener(seeAnswerListener);
        
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// after rate activity returns we change card view for next card.
    	if (requestCode == CARD_RATING_RESULT) {
    		currentIndex++;
    		if (currentIndex < cards.size()) {
    			showCurrentCard();
    		} else {
    			setCards(db.cursorToCards(db.getCardsForQuiz()));
    			if (cards != null) {
    				currentIndex = 0;
    				showCurrentCard();
    			} else {
    				handleNoCards();
    			}
    		}
    	}
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	db.close();
    }
    

    private void showStopButton() {
    	quizView.removeView(startBtn);
        quizView.addView(stopBtn, 0);
    }
    
    private void showStartButton() {
    	quizView.removeView(stopBtn);
        quizView.addView(startBtn, 0);
    }
    
    public void handleNoCards() {
    	Toast msg = Toast.makeText(getApplicationContext(), R.string.NoMoreCards, Toast.LENGTH_LONG);
    	msg.show();
    }
    
	public List<Card> getCards() {
		return cards;
	}
	

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	private void showCurrentCard() {
		Card card = cards.get(currentIndex);
		showCard(card);
		L.d(QuizActivity.class.getName(), "Showing current card index: %d id: %d",
				currentIndex, card.getId());
	}
	
	private void showCard(Card currentCard) {
		sideView.removeAllViews();
		cardTitleViewer.setText(currentCard.getTitle());
		// card view shows side 1 first
		if (currentCard.getSide1Type() == Card.TEXT_TYPE) {
			L.d("onClick StartButton", "Showing text side for card: %d", currentCard.getId());
			// we show a textSideViewer
			textSideViewer.setText(currentCard.getSide1Text());
			sideView.addView(textSideViewer);
		} else {
			L.d("onClick StartButton", "Showing image side for card: %d", currentCard.getId());
			// we show an imageSideViewer
			imgSideViewer.setImageURI(currentCard.getSide1URI());
			sideView.addView(imgSideViewer);
		}
	}
}
