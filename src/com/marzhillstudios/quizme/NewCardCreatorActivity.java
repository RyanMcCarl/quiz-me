/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
    
    private String side1;
    private String side2;
    
    private String fileNamePrefix;

	protected Uri imageUriSide2;

	protected Uri imageUriSide1;
    
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
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	File photo = new File(Environment.getExternalStorageDirectory(),  fileNamePrefix + "side1.png");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUriSide1 = Uri.fromFile(photo);
                mainContext.startActivityForResult(intent,
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
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	File photo = new File(Environment.getExternalStorageDirectory(),  fileNamePrefix + "side2.png");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUriSide2 = Uri.fromFile(photo);
                mainContext.startActivityForResult(intent,
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
            	// TODO side1 = (Bitmap) data.getExtras().getParcelable("data");
                setSide1Type(Card.IMAGE_TYPE);
                side1 = imageUriSide1.toString();
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved image result for side 1 image: %s", side1);
                break;
            case REQUEST_SIDE2_IMAGE_RESULT:
                setSide2Type(Card.IMAGE_TYPE);
                side2 = imageUriSide2.toString();
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved image result for side 2 %s", side2);
                break;
            case REQUEST_SIDE1_TEXT_RESULT:
            	setSide1Type(Card.TEXT_TYPE);
            	side1 = data.getExtras().getString(TextCardEditActivity.EXTRA_KEY);
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved text result for side 1 %s", side1);
                break;
            case REQUEST_SIDE2_TEXT_RESULT:
            	setSide2Type(Card.TEXT_TYPE);
            	side2 = data.getExtras().getString(TextCardEditActivity.EXTRA_KEY);
                L.d("NewCardCreatorActivity onActivityResult",
                    "Recieved text result for side 2 %s", side2);
                break;
        }
    }

	public int getSide1Type() {
		return side1Type;
	}

	public void setSide1Type(int side1Type) {
		this.side1Type = side1Type;
	}

	public int getSide2Type() {
		return side2Type;
	}

	public void setSide2Type(int side2Type) {
		this.side2Type = side2Type;
	}
}
