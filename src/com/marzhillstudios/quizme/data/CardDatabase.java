/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * The Data access layer for our Card Database.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
// TODO(jwall): Unit tests for this class.
public class CardDatabase extends SQLiteOpenHelper {
	
    public static final int CARD_ID_COLUMN = 0;
    public static final int CARD_TITLE_COLUMN = 1;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "/sdcard/quizme/quiz_me_db";
    private static final String CARDS_TABLE_NAME = "card_table";
    private static final String STATS_TABLE_NAME = "card_stats_table";
    private static final String TAGS_TABLE_NAME = "card_tags_table";

    private static final String[] CARDS_TABLE_COLUMNS_ARRAY =
        {"id", "title", "side1type", "side1", "side2type", "side2", "ef", "count", "interval", "last"};
    private static final String CARDS_TABLE_COLUMNS =
        "id INTEGER PRIMARY KEY ASC, title TEXT, side1type INTEGER, "
	+ " side1 BLOB, side2type INTEGER, side2 BLOB, ef REAL, "
	+ "count INTEGER, interval INTEGER, last INTEGER";
    private static final String STATS_TABLE_COLUMNS =
        "card_id INTEGER PRIMARY KEY ASC, stat TEXT, value INTEGER";
    private static final String TAGS_TABLE_COLUMNS = "card_id INTEGER PRIMARY KEY ASC, tag TEXT";

    private static final String CARDS_TITLE_INDEX_NAME = "card_title_index";
    private static final String CARDS_TITLE_INDEX_COLUMN =
        CARDS_TABLE_COLUMNS_ARRAY[CARD_TITLE_COLUMN];
    
    public CardDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String card_sql = "CREATE TABLE " + CARDS_TABLE_NAME + " ( "
            + CARDS_TABLE_COLUMNS + " );";
        String stats_sql = "CREATE TABLE " + STATS_TABLE_NAME + " ( "
            + STATS_TABLE_COLUMNS + " );";
        String tags_sql = "CREATE TABLE " + TAGS_TABLE_NAME + " ( "
            + TAGS_TABLE_COLUMNS + " );";
        String card_title_index_sql = "CREATE INDEX "
            + CARDS_TITLE_INDEX_NAME + " ON "
            + CARDS_TABLE_NAME + " ( " + CARDS_TITLE_INDEX_COLUMN + " )";
        db.execSQL(card_sql);
        db.execSQL(stats_sql);
        db.execSQL(tags_sql);
        db.execSQL(card_title_index_sql);
    }
    
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }

    public Cursor getAllCards() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
            CARDS_TABLE_NAME, CARDS_TABLE_COLUMNS_ARRAY, null, null, null,
            null, null, null);
    }

    // TODO(jwall): Actually do the Database operations below
    /**
     * Get a card from the database.
     *
     * @param id The primary key for the card in the database.
     * @returns Maybe card. @see com.marzhillstudios.util.Maybe
     */
    public Card<?, ?> getCard(Integer id) {
    	SQLiteDatabase db = getReadableDatabase();
    	String[] selectionArgs  = { id.toString() };
    	Cursor cur = db.query(CARDS_TABLE_NAME, CARDS_TABLE_COLUMNS_ARRAY, "id = ?", selectionArgs, null, null, null);
    	if (cur.getColumnCount() == 1) {
    		Integer ident = cur.getInt(0);
    		String title = cur.getString(1);
    		Integer side1Type = cur.getInt(2);
    		Integer side2Type = cur.getInt(4);
    		Float ef = cur.getFloat(6);
    		Integer count = cur.getInt(7);
    		Integer interval = cur.getInt(8);
    		Long last = cur.getLong(9);
    		if (side1Type == Card.IMAGE_TYPE && side2Type == Card.TEXT_TYPE) {
    			byte[] data = cur.getBlob(3);
    			Bitmap img1 = BitmapFactory.decodeByteArray(data, 0, data.length);
    			String txt2 = cur.getString(5);
    			Card<Bitmap, String> card = new Card<Bitmap, String>(ident, title, img1, txt2, ef);
    			card.setCount(count);
    			card.setInterval(interval);
    			card.setLastTime(last);
    			card.side1Type = side1Type;
    			card.side2Type = side2Type;
    			return card;
    		} else if (side1Type == Card.IMAGE_TYPE && side2Type == Card.IMAGE_TYPE) {
    			byte[] data1 = cur.getBlob(3);
    			Bitmap img1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);
    			byte[] data2 = cur.getBlob(5);
    			Bitmap img2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);
    			Card<Bitmap, Bitmap> card = new Card<Bitmap, Bitmap>(ident, title, img1, img2, ef);
    			card.setCount(count);
    			card.setInterval(interval);
    			card.setLastTime(last);
    			card.side1Type = side1Type;
    			card.side2Type = side2Type;
    			return card;
    		} else if (side1Type == Card.TEXT_TYPE && side2Type == Card.IMAGE_TYPE) {
    			byte[] data = cur.getBlob(5);
    			Bitmap img2 = BitmapFactory.decodeByteArray(data, 0, data.length);
    			String txt1 = cur.getString(3);
    			Card<String, Bitmap> card = new Card<String, Bitmap>(ident, title, txt1, img2, ef);
    			card.setCount(count);
    			card.setInterval(interval);
    			card.setLastTime(last);
    			card.side1Type = side1Type;
    			card.side2Type = side2Type;
    			return card;
    		} else if (side1Type == Card.TEXT_TYPE && side2Type == Card.TEXT_TYPE) {
    			String txt1 = cur.getString(3);
    			String txt2 = cur.getString(5);
    			Card<String, String> card = new Card<String, String>(ident, title, txt1, txt2, ef);
    			card.setCount(count);
    			card.setInterval(interval);
    			card.setLastTime(last);
    			card.side1Type = side1Type;
    			card.side2Type = side2Type;
    			return card;
    		}
    	}
        return null;
    }
    
    /**
     * Update/Insert a card in the database.
     *
     * @param card a Card<S1, S2>.
     * @returns a Card<S1, S2>.
     */
    public Card<?, ?> upsertCard(Card<?, ?> card) {
    	SQLiteDatabase db = getReadableDatabase();
    	ContentValues values = new ContentValues();
		values.put("title", card.getId());
		values.put("side1Type", card.side1Type);
		if (card.side1Type == Card.IMAGE_TYPE) {
			byte[] img1 = { };
			values.put("side1", img1);
		} else {
			values.put("side1", (String) card.side1); 
		}
		values.put("side2Type", card.side2Type);
    	Card<?, ?> c1 = getCard(card.getId());
    	if (c1 == null) {
    		// an insert then
    		db.insertOrThrow(CARDS_TABLE_NAME, null, values);
    	} else {
    		String[] whereArgs = { new Integer(card.getId()).toString() };
        	db.update(CARDS_TABLE_NAME, values, "id = ?", whereArgs);
    		// an update
    	}
    	return card;
    }
    
    
    /** Delete a card in the database. */
    public void deleteCard(Card<?, ?> card) {
        deleteCard(card.getId());
    }
    
    /**
     * Delete a card in the database.
     *
     * @param id The integer primary key for the key in the database.
     */
    public void deleteCard(Integer id) {
    	SQLiteDatabase db = getReadableDatabase();
    	String[] whereArgs = { id.toString() };
    	db.delete(CARDS_TABLE_NAME, "id = ?", whereArgs);
    }
}
