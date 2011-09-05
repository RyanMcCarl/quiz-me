/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import com.marzhillstudios.quizme.algorithm.SM2;
import com.marzhillstudios.quizme.data.Card;
import com.marzhillstudios.quizme.data.CardDatabase;

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
	public void onCreate(Bundle bundle) {
		db = new CardDatabase(this);
		setContentView(R.layout.rate_card_view);
		Intent intent = getIntent();
		Long cardId = intent.getExtras().getLong(CARD_RATING_INTENT_ID_KEY);
		card = db.getCard(cardId);
		
		FrameLayout cardFrame = (FrameLayout) findViewById(R.id.CardFrame);
		TextView cardTitle = (TextView) findViewById(R.id.RateCardTitle);
		RatingBar rateCard = (RatingBar) findViewById(R.id.RateCardRatingBar);
		Button doneBtn = (Button) findViewById(R.id.DoneRatingButton);
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
					// TODO(jwall): Handle the rating change.
					rating = Math.round(ratingFloat);
				}
			};
			
			OnClickListener doneListener = new OnClickListener() {
				public void onClick(View v) {
					// TODO(jwal): finalize the rating.
					SM2.scoreCardAndCalculateInterval(card, rating);
					db.upsertCard(card);
					self.finish();
				}
			};
			
			doneBtn.setOnClickListener(doneListener);
			rateCard.setOnRatingBarChangeListener(ratingListener);
			
		} else {
			// Show an error message
		}
		
	}
}
