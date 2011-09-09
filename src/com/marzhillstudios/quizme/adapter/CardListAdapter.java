/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marzhillstudios.quizme.NewCardCreatorActivity;
import com.marzhillstudios.quizme.R;
import com.marzhillstudios.quizme.data.CardDatabase;
import com.marzhillstudios.quizme.util.L;

/**
 * An adapter to the Card Database for ListViews.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */

// TODO(jwall): UnitTests for this.
public class CardListAdapter extends BaseAdapter {

	private Cursor cursor;
    private Context context;
    private static final int ID_COLUMN = CardDatabase.CARD_ID_COLUMN;
    private static final int TITLE_COLUMN = CardDatabase.CARD_TITLE_COLUMN;

    public CardListAdapter(Context context, CardDatabase db) {
    	this(context, db.getAllCards());
    }

    private CardListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    private class CardView extends LinearLayout {

      @SuppressWarnings("unused")
      private String title;
      private Long id;
      private LinearLayout container;
      private TextView listText;
      
      public CardView(final Context context, final Long id, String title) {
        super(context);
        final CardView self = this;
        this.title = title;
        this.id = id;
        L.d("CardView", "Creating card listing for id: %d", id);
        LayoutInflater inflater = LayoutInflater.from(context);
        container = (LinearLayout) inflater.inflate(R.layout.card_list_item, null);
        listText = (TextView) container.findViewById(R.id.CardListTitleView);
        Button deleteButton = (Button) container.findViewById(R.id.CardListButtonView);
        
        // TextViews need to respond to the onClick event
        OnClickListener updateCardListener = new OnClickListener() {
            public void onClick(View v) {
            	L.d("onClick", "Opening card editor for card id: %d spawned by view id: %d",
            			self.id, v.getId());
            	Intent intent = new Intent(context, NewCardCreatorActivity.class);
            	intent.putExtra(NewCardCreatorActivity.CARD_INTENT_KEY, self.id);
                context.startActivity(
                    intent);
            }
        };
        
        OnClickListener deleteButtonListener = new OnClickListener() {
        	public void onClick(View v) {
        		L.d("onClick", "Deleting card id: %d spawned by button id: %d",
        				self.id, v.getId());
            	CardDatabase db = new CardDatabase(context);
        		db.deleteCard(self.id);
            	db.close();
            	self.invalidate();
        	}
        };
        
        deleteButton.setOnClickListener(deleteButtonListener);
        listText.setOnClickListener(updateCardListener);
        setTitle(title);
        addView(container,
                new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
      }

      public void setTitle(String title) {
        this.title = title;
        listText.setText(title);
      }

      public void setId(Long id) {
          this.id = id;
      }
    }
    
    public int getCount() {
       return cursor.getCount();
    }

    public Object getItem(int position) {
       cursor.moveToPosition(position);
       return cursor.getString(TITLE_COLUMN);
    }

    public long getItemId(int position) {
       cursor.moveToPosition(position);
       return cursor.getInt(ID_COLUMN);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       cursor.moveToPosition(position);
       String title = cursor.getString(TITLE_COLUMN);
       Long id = cursor.getLong(ID_COLUMN);
       CardView cv;
       if (convertView != null) {
           cv = (CardView)convertView;
           cv.setTitle(title);
           cv.setId(id);
       } else {
           cv = new CardView(context, id, title);
       }
       return cv;
    }
    
    public void reset(Cursor cursor) {
    	this.cursor = cursor;
    	this.notifyDataSetInvalidated();
    }
}
