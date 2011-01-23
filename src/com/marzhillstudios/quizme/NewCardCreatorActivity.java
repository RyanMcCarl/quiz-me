/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.marzhillstudios.quizme.adapter.CardListAdapter;
import com.marzhillstudios.quizme.data.CardDatabase;
import com.marzhillstudios.quizme.util.L;

/**
 * Custom activity class for creating new cards.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
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
    private TextView titleTextBox;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_card_dialog);
        final Activity mainContext = this;
        // TODO(jwall): I need to override the onActivityResult
        // Callback.
        side1ImageBtn =
            (Button) findViewById(R.id.NewCardSide1ImageBtn);

        OnClickListener side1ImageBtnListener = new OnClickListener() {
            public void onClick(View v) {
                // TODO(jwall): we need to start an activity to get an image.
                mainContext.startActivityForResult(
                    new Intent(MediaStore.INTENT_ACTION_MEDIA_SEARCH),
                    REQUEST_SIDE1_IMAGE_RESULT);
            }
        };

        side1ImageBtn.setOnClickListener(side1ImageBtnListener);

        side1TextBtn =
            (Button) mainContext.findViewById(R.id.NewCardSide1TextBtn);

        OnClickListener side1TextBtnListener = new OnClickListener() {
            public void onClick(View v) {
                // TODO(jwall): we need to start an activity to write text.
                Intent intent = new Intent(Intent.ACTION_EDIT);
                Uri uri = Uri.parse("file:///sdcard/dummyfile.txt");
                intent.setDataAndType(uri, "text/plain");
                mainContext.startActivityForResult(
                    intent, REQUEST_SIDE1_TEXT_RESULT);
            }
        };

        side1TextBtn.setOnClickListener(side1TextBtnListener);

        side2ImageBtn =
            (Button) mainContext.findViewById(R.id.NewCardSide2ImageBtn);

        OnClickListener side2ImageBtnListener = new OnClickListener() {
            public void onClick(View v) {
                // TODO(jwall): we need to start an activity to get an image.
                mainContext.startActivityForResult(
                    new Intent(MediaStore.INTENT_ACTION_MEDIA_SEARCH),
                    REQUEST_SIDE2_IMAGE_RESULT);
            }
        };

        side2ImageBtn.setOnClickListener(side2ImageBtnListener);

        side2TextBtn =
            (Button) mainContext.findViewById(R.id.NewCardSide2TextBtn);

        OnClickListener side2TextBtnListener = new OnClickListener() {
            public void onClick(View v) {
                // TODO(jwall): we need to start an activity to write text.
                Intent intent = new Intent(Intent.ACTION_EDIT);
                Uri uri = Uri.parse("file:///sdcard/quizme/dummyfile.txt");
                intent.setDataAndType(uri, "text/plain");
                mainContext.startActivityForResult(
                    intent, REQUEST_SIDE2_TEXT_RESULT);
            }
        };

        side2TextBtn.setOnClickListener(side2TextBtnListener);

        titleTextBox =
            (TextView) findViewById(R.id.NewCardTitleEditable);

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO(jwall): dispatch for the various request codes and result codes
    }
}
