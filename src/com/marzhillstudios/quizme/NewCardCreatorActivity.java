/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.marzhillstudios.quizme.data.Card;
import com.marzhillstudios.quizme.data.CardDatabase;
import com.marzhillstudios.quizme.util.L;

/**
 * Custom activity class for creating new cards.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
// TODO(jwall): unit tests for this class.
// TODO(jwall): should this activity implement some sendTo intents?
// TODO(jwall): Audio card sides?
public class NewCardCreatorActivity extends Activity {

	private CardDatabase db;

    public static final int REQUEST_SIDE1_IMAGE_RESULT = 1;
    public static final int REQUEST_SIDE1_TEXT_RESULT = 2;
    public static final int REQUEST_SIDE2_IMAGE_RESULT = 3;
    public static final int REQUEST_SIDE2_TEXT_RESULT = 4;

    public static final String CARD_INTENT_KEY = "card_for_update";

    private Button side1ImageBtn;
    private Button side1TextBtn;
    private Button side2ImageBtn;
    private Button side2TextBtn;
    private EditText titleTextBox;

    private Card card;
    private String fileNamePrefix;
    private String side1Text;
    private String side2Text;
	private Uri imageUriSide2;
	private Uri imageUriSide1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_card_dialog);
        db = new CardDatabase(this);
        final Activity mainContext = this;
        Intent intention = getIntent();
        titleTextBox =
                (EditText) findViewById(R.id.NewCardTitleEditable);
        side1ImageBtn =
                (Button) findViewById(R.id.NewCardSide1ImageBtn);
        side1TextBtn =
                (Button) mainContext.findViewById(R.id.NewCardSide1TextBtn);
        side2ImageBtn =
                (Button) mainContext.findViewById(R.id.NewCardSide2ImageBtn);
        side2TextBtn =
                (Button) mainContext.findViewById(R.id.NewCardSide2TextBtn);

        Resources res = getResources();
    	side1Text = res.getString(R.string.Side1Text);
        side2Text = res.getString(R.string.Side2Text);

        if (intention.hasExtra(CARD_INTENT_KEY)) {
        	// we are editing a card
        	Long id = intention.getExtras().getLong(CARD_INTENT_KEY);
        	L.d("NewCardCreatorActivity onCreate", "Retrieving Card with id %d", id);
        	card = db.getCard(intention.getExtras().getLong(CARD_INTENT_KEY));
        	fileNamePrefix = String.format("card_%d_", card.getId());
        } else {
        	// new card
        	card = new Card(
        			res.getString(R.string.NewCardDialogTitleDefault), side1Text, side2Text);
        	fileNamePrefix = String.format("card_%d_", db.getNextCardId());
        }

        titleTextBox.setText(card.getTitle());

        imageUriSide1 = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),  fileNamePrefix + "side1.png"));
        OnClickListener side1ImageBtnListener = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriSide1);
                L.i("onClick side1ImageBtnListener", "image uri: %s", imageUriSide1.toString());
                mainContext.startActivityForResult(intent,
                    REQUEST_SIDE1_IMAGE_RESULT);
            }
        };

        side1ImageBtn.setOnClickListener(side1ImageBtnListener);

        OnClickListener side1TextBtnListener = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mainContext, TextCardEditActivity.class);
                intent.putExtra(TextCardEditActivity.EXTRA_KEY, card.side1);
                intent.setType("text/plain");
                L.d("NewCardCreatorAtvivtyService side1TextBtnListener",
                		"Launching the Text editing service.");
                mainContext.startActivityForResult(
                    intent, REQUEST_SIDE1_TEXT_RESULT);
            }
        };

        side1TextBtn.setOnClickListener(side1TextBtnListener);

        imageUriSide2 = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),  fileNamePrefix + "side2.png"));
        OnClickListener side2ImageBtnListener = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriSide2);
                L.i("onClick side2ImageBtnListener", "image uri: %s", imageUriSide2.toString());
                mainContext.startActivityForResult(intent,
                    REQUEST_SIDE2_IMAGE_RESULT);
            }
        };

        side2ImageBtn.setOnClickListener(side2ImageBtnListener);

        OnClickListener side2TextBtnListener = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mainContext, TextCardEditActivity.class);
                intent.putExtra(TextCardEditActivity.EXTRA_KEY, card.side2);
                intent.setType("text/plain");
                L.d("NewCardCreatorAtvivtyService side2TextBtnListener",
                		"Launching the Text editing service.");
                mainContext.startActivityForResult(
                    intent, REQUEST_SIDE2_TEXT_RESULT);
            }
        };

        side2TextBtn.setOnClickListener(side2TextBtnListener);
    }

    public CardDatabase getDb() { return db; }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.d("NewCardCreatorActivity onActivityResult",
            "Recieved result from an activity resultCode: %d, requestCode %d",
            resultCode, requestCode);
        switch(requestCode) {
            // TODO(jwall): the following is now testable so go write some :-)
            case REQUEST_SIDE1_IMAGE_RESULT:
            	card.setSide1Type(Card.IMAGE_TYPE);
                card.side1 = imageUriSide1.toString();
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved image result for side 1 image: %s", card.side1);
                break;
            case REQUEST_SIDE2_IMAGE_RESULT:
                card.setSide2Type(Card.IMAGE_TYPE);
                card.side2 = imageUriSide2.toString();
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved image result for side 2 %s", card.side2);
                break;
            case REQUEST_SIDE1_TEXT_RESULT:
            	card.setSide1Type(Card.TEXT_TYPE);
            	if (data != null && data.hasExtra(TextCardEditActivity.EXTRA_KEY)) {
            		card.side1 = data.getExtras().getString(TextCardEditActivity.EXTRA_KEY);
            	}
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved text result for side 1 %s", card.side1);
                break;
            case REQUEST_SIDE2_TEXT_RESULT:
            	card.setSide2Type(Card.TEXT_TYPE);
            	if (data != null && data.hasExtra(TextCardEditActivity.EXTRA_KEY)) {
            		card.side2 = data.getExtras().getString(TextCardEditActivity.EXTRA_KEY);
            	}
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved text result for side 2 %s", card.side2);
                break;
        }
    }

   @Override
    public void onPause() {
    	super.onPause();
    	card.setTitle(titleTextBox.getText().toString());
    	Long id = db.upsertCard(card);
    	card.setId(id);
    	db.close();

    }
}
