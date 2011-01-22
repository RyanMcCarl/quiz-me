/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;

import com.marzhillstudios.quizme.adapter.CardListAdapter;
import com.marzhillstudios.quizme.data.CardDatabase;
import com.marzhillstudios.quizme.util.L;

/**
 * 
 * Activity for managing cards. This Activity will be responsible for
 * handling Card Creation/Editing/Deletion and Import/Export.
 * 
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 */
public class CardManagerActivity extends Activity {
    
    @SuppressWarnings("unused")
    private CardDatabase db;
    private CardListAdapter listAdapter;
    private ListView listView;

    public static final int DIALOG_NEW_CARD = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardmanager);
        db = new CardDatabase(this);
        Button newCardBtn = (Button) findViewById(R.id.NewCardButton);
        listAdapter = new CardListAdapter(this, db);
        listView = (ListView) findViewById(R.id.CardManagerList);
        listView.setAdapter(listAdapter);

        OnClickListener newCardListener = new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_NEW_CARD);
            }
        };

        newCardBtn.setOnClickListener(newCardListener);
    }

    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch(id) {
            case DIALOG_NEW_CARD:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater =
                    (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup layoutRoot =
                    (ViewGroup) findViewById(R.id.NewCardLayout);
                builder.setView(
                    inflater.inflate(R.layout.new_card_dialog, layoutRoot));
                dialog = builder.create();
                break;
            default:
                dialog = null;
        }
        return dialog;
    }

    public CardListAdapter getListAdapter() {
        return listAdapter;
    }

    public ListView getListView() {
        return listView;
    }

    public CardDatabase getDb() {
        return db;
    }
}
