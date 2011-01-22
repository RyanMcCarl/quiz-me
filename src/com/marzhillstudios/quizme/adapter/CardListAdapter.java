/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.adapter;

import android.content.Context;
import android.database.Cursor;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import com.marzhillstudios.quizme.R;
import com.marzhillstudios.quizme.data.CardDatabase;

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

    public CardListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    private class CardView extends LinearLayout {

      private String title;
      private int id;
      private TextView listItem;
      
      public CardView(Context context, int id, String title) {
        super(context);
        this.title = title;
        this.id = id;
        listItem = new TextView(context);
        setTitle(title);
	addView(listItem,
                new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
      }

      public void setTitle(String title) {
        this.title = title;
        listItem.setText(title);
      }

      public void setId(int id) {
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
       int id = cursor.getInt(ID_COLUMN);
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
}
