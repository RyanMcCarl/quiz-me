/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import android.app.Activity;
import android.widget.ListView;
import android.view.View;
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

    // TODO(jwall): ActivityTest for this class.
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardmanager);
        db = new CardDatabase(this);
        L.d("CardManagerActivity onCreate", "Got db %s", db); 
        listAdapter = new CardListAdapter(this, db);
        L.d("CardManagerActivity onCreate", "Got listAdapter %s", listAdapter); 
        listView = (ListView) findViewById(R.id.CardManagerList);
        L.d("CardManagerActivity onCreate", "Got listView %s", listView); 
        listView.setAdapter(listAdapter);
        L.d("CardManagerActivity onCreate", "Set the adapter"); 
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
