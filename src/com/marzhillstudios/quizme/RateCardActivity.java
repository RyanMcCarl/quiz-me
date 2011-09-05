/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import java.util.Date;

import com.marzhillstudios.quizme.algorithm.SM2;
import com.marzhillstudios.quizme.data.Card;
import com.marzhillstudios.quizme.data.CardDatabase;
import com.marzhillstudios.quizme.util.L;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

/**
 * @author jwall
 *
 */
public class RateCardActivity extends Activity {
	
	public static final String CARD_RATING_INTENT_RESULT_KEY = "card_rating";
	public static final String CARD_RATING_INTENT_ID_KEY = "card_id";
	
	private CardDatabase db;
	private Card card;
	private Integer rating;
	
	private TextView textSideViewer;
    private ImageView imgSideViewer;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new CardDatabase(this);
		setContentView(R.layout.rate_card_view);
		Intent intent = getIntent();
		Long cardId = intent.getExtras().getLong(CARD_RATING_INTENT_ID_KEY);
		card = db.getCard(cardId);
		
		FrameLayout cardFrame = (FrameLayout) findViewById(R.id.CardFrame);
		TextView cardTitle = (TextView) findViewById(R.id.RateCardTitle);
		RatingBar rateCard = (RatingBar) findViewById(R.id.RateCardRatingBar);
		rateCard.setNumStars(5);
		rateCard.setStepSize(1.0f);
		rateCard.setRating(1f);
		rating = 1;
		Button doneBtn = (Button) findViewById(R.id.DoneRatingButton);
		textSideViewer = new TextView(this);
        imgSideViewer = new ImageView(this);
        
		final RateCardActivity self = this;
		
		if (card != null) {
			cardTitle.setText(card.getTitle());
			if (card.getSide2Type() == Card.TEXT_TYPE) {
				textSideViewer.setText(card.getSide2Text());
				cardFrame.addView(textSideViewer);
			} else {
				imgSideViewer.setImageURI(card.getSide2URI());
				cardFrame.addView(imgSideViewer);
			}
			
			OnRatingBarChangeListener ratingListener = new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar arg0, float ratingFloat,
						boolean arg2) {
					rating = Math.round(ratingFloat);
					L.d("onRatingChanged", "Rating is now %d from float: %f", rating, ratingFloat);
				}
			};
			
			OnClickListener doneListener = new OnClickListener() {
				public void onClick(View v) {
					card.incrementCount();
					SM2.scoreCardAndCalculateInterval(card, rating);
					L.d("onClick doneListener", "Cards ef after: %f", card.getEFactor());
					card.setLastTime(new Date().getTime());
					db.upsertCard(card);
					self.setResult(QuizActivity.CARD_RATING_RESULT);
					self.finish();
				}
			};
			
			doneBtn.setOnClickListener(doneListener);
			rateCard.setOnRatingBarChangeListener(ratingListener);
			
		} else {
			// Show an error message
		}
		
	}
	
	@Override
    public void onDestroy() {
    	super.onDestroy();
    	db.close();
    }
    
}
