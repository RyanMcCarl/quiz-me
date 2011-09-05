/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

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
    
	private CardDatabase db;
    private CardListAdapter listAdapter;
    private ListView listView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardmanager);
        db = new CardDatabase(this);
        final Activity mainContext = this;
        Button newCardBtn = (Button) findViewById(R.id.NewCardButton);
        listAdapter = new CardListAdapter(this, db);
        listView = (ListView) findViewById(R.id.CardManagerList);
        listView.setAdapter(listAdapter);

        OnClickListener newCardListener = new OnClickListener() {
            public void onClick(View v) {
                startActivity(
                    new Intent(mainContext, NewCardCreatorActivity.class));
            }
        };

        newCardBtn.setOnClickListener(newCardListener);
    }

    @Override
    public void onStart() {
    	// repaint the list
    	listAdapter.reset(db.getAllCards());
    	L.d("onStart", "Resetting list view to list of count %d", listAdapter.getCount());
    	super.onStart();
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
    
    @Override
    public void onDestroy() {
    	db.close();
    }
    
}
