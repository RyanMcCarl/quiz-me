/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
// TODO(jwall): perhaps this class can also be reused to edit cards?
public class NewCardCreatorActivity extends Activity {

    private CardDatabase db;

    public static final int REQUEST_SIDE1_IMAGE_RESULT = 1;
    public static final int REQUEST_SIDE1_TEXT_RESULT = 2;
    public static final int REQUEST_SIDE2_IMAGE_RESULT = 3;
    public static final int REQUEST_SIDE2_TEXT_RESULT = 4;

    private Button side1ImageBtn;
    private Button side1TextBtn;
    private Button side2ImageBtn;
    private Button side2TextBtn;
    private EditText titleTextBox;

    private int side1Type;
    private int side2Type;
    
    private Bitmap img1;
    private Bitmap img2;
    private String txt1;
    private String txt2;
    
    private String fileNamePrefix;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_card_dialog);
        db = new CardDatabase(this);
        final Activity mainContext = this;
        
        side1ImageBtn =
            (Button) findViewById(R.id.NewCardSide1ImageBtn);

        OnClickListener side1ImageBtnListener = new OnClickListener() {
            public void onClick(View v) {
                // TODO(jwall): we need to start an activity to capture an image.
            	Intent imgIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	imgIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileNamePrefix + "side1");
                mainContext.startActivityForResult(imgIntent,
                    REQUEST_SIDE1_IMAGE_RESULT);
            }
        };

        side1ImageBtn.setOnClickListener(side1ImageBtnListener);

        side1TextBtn =
            (Button) mainContext.findViewById(R.id.NewCardSide1TextBtn);

        OnClickListener side1TextBtnListener = new OnClickListener() {
            public void onClick(View v) {
                // TODO(jwall): we need to start an activity to write text.
                Intent intent = new Intent(mainContext, TextCardEditActivity.class);
                intent.putExtra(TextCardEditActivity.EXTRA_KEY, "Side 1");
                intent.setType("text/plain");
                L.d("NewCardCreatorAtvivtyService side1TextBtnListener",
                		"Launching the Text editing service.");
                mainContext.startActivityForResult(
                    intent, REQUEST_SIDE1_TEXT_RESULT);
            }
        };

        side1TextBtn.setOnClickListener(side1TextBtnListener);

        side2ImageBtn =
            (Button) mainContext.findViewById(R.id.NewCardSide2ImageBtn);

        OnClickListener side2ImageBtnListener = new OnClickListener() {
            public void onClick(View v) {
                // TODO(jwall): we need to start an activity to capture an image.
            	Intent imgIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	imgIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileNamePrefix + "side1");
                mainContext.startActivityForResult(imgIntent,
                    REQUEST_SIDE2_IMAGE_RESULT);
            }
        };

        side2ImageBtn.setOnClickListener(side2ImageBtnListener);

        side2TextBtn =
            (Button) mainContext.findViewById(R.id.NewCardSide2TextBtn);

        OnClickListener side2TextBtnListener = new OnClickListener() {
            public void onClick(View v) {
                // TODO(jwall): we need to start an activity to write text.
            	Intent intent = new Intent(mainContext, TextCardEditActivity.class);
                intent.putExtra(TextCardEditActivity.EXTRA_KEY, "Side 2");
                intent.setType("text/plain");
                L.d("NewCardCreatorAtvivtyService side2TextBtnListener",
                		"Launching the Text editing service.");
                mainContext.startActivityForResult(
                    intent, REQUEST_SIDE2_TEXT_RESULT);
            }
        };

        side2TextBtn.setOnClickListener(side2TextBtnListener);

        titleTextBox =
            (EditText) findViewById(R.id.NewCardTitleEditable);

        OnEditorActionListener titleTextBoxEditListener =
            new OnEditorActionListener() {
                public boolean onEditorAction(
                    TextView v, int action, KeyEvent event) {
                  // return true if I consume the action. (if event is not null)
                  if (event != null) {
                      L.d("NewCardCreatorActivity onEditorAction",
                          "encountered enter key event");
                      // TODO(jwall): we need to store this value in our
                      // activity. and use it to restore this activity in
                      // the bundle.
                      return true;
                  } else {
                      L.d("NewCardCreatorActivity onEditorAction",
                          "encountered action event %d", action);
                      return false;
                  }
                }
            };

        titleTextBox.setOnEditorActionListener(titleTextBoxEditListener);
    }

    public CardDatabase getDb() { return db; }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO(jwall): dispatch for the various request codes and result codes
        L.d("NewCardCreatorActivity onActivityResult",
            "Recieved result from an activity resultCode: %d, requestCode %d",
            resultCode, requestCode);
        switch(requestCode) {
            // TODO(jwall): the following is now testable so go write some :-)
            case REQUEST_SIDE1_IMAGE_RESULT:
            	// TODO(jwall): Verify the correct way to get the camera image.
                img1 = (Bitmap) data.getExtras().getParcelable("data");
                side1Type = Card.IMAGE_TYPE;
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved image result for side 1 image: %s", img1);
                break;
            case REQUEST_SIDE2_IMAGE_RESULT:
                img2 = (Bitmap) data.getExtras().getParcelable("data");
                side2Type = Card.IMAGE_TYPE;
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved image result for side 2 %s", img2);
                break;
            case REQUEST_SIDE1_TEXT_RESULT:
            	txt1 = data.getExtras().getString(TextCardEditActivity.EXTRA_KEY);
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved text result for side 1 %s", txt1);
                break;
            case REQUEST_SIDE2_TEXT_RESULT:
            	txt2 = data.getExtras().getString(TextCardEditActivity.EXTRA_KEY);
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved text result for side 2 %s", txt2);
                break;
        }
        if (side1Type == Card.IMAGE_TYPE && side2Type == Card.TEXT_TYPE) {
        	Card<Bitmap, String> card = new Card<Bitmap, String>("foo", img1, txt2);
            db.upsertCard(card);
        }
        if (side1Type == Card.TEXT_TYPE && side2Type == Card.TEXT_TYPE) {
        	Card<String, Bitmap> card = new Card<String, Bitmap>("foo", txt1, img2);
            db.upsertCard(card);
        }
        if (side1Type == Card.IMAGE_TYPE && side2Type == Card.IMAGE_TYPE) {
        	Card<Bitmap, Bitmap> card = new Card<Bitmap, Bitmap>("foo", img1, img2);
            db.upsertCard(card);
        }
        if (side1Type == Card.TEXT_TYPE && side2Type == Card.TEXT_TYPE) {
        	Card<String, String> card = new Card<String, String>("foo", txt1, txt2);
            db.upsertCard(card);
        }
    }
}
