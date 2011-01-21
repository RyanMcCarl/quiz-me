/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.adapter;

import android.content.Context;
import android.database.Cursor;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;

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
    private static final int TITLE_COLUMN = 1;

    public CardListAdapter(Context context, CardDatabase db) {
        this(context, db.getAllCards());
    }

    public CardListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
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
       return cursor.getInt(0);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       return null; 
    }
}
