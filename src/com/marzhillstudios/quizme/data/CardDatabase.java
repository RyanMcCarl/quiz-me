/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.marzhillstudios.util.Maybe;
import com.marzhillstudios.util.MaybeDo;

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
    private static final String[] STATS_TABLE_COLUMNS_ARRAY =
        {"card_id", "stat", "value"};
    private static final String STATS_TABLE_COLUMNS =
        "card_id INTEGER PRIMARY KEY ASC, stat TEXT, value INTEGER";
    private static final String[] TAGS_TABLE_COLUMNS_ARRAY =
        {"card_id", "tag"};
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

    /**
     * Get a card from the database.
     *
     * @param id The primary key for the card in the database.
     * @returns Maybe card. @see com.marzhillstudios.util.Maybe
     */
    public Maybe<Card> getCard(int id) {
        return new Maybe<Card>();
    }
    
    /**
     * Update/Insert a card in the database.
     *
     * Maybe updates or Inserts a card in the database. For details about
     * The Maybe generic class @see @see com.marzhillstudios.util.Maybe
     * and @see com.marzhillstudios.util.MaybeDo.
     * 
     * @param card a Maybe<Card> .
     * @returns a Maybe<Card>.
     */
    public Maybe<Card> upsertCard(Maybe<Card> card) {
        return new Maybe<Card>();
    }
    
    /** Delete a card in the database. */
    public void deleteCard(Card card) {
        deleteCard(card.getId());
    }
    
    /**
     * Delete a card in the database.
     *
     * @param id The integer primary key for the key in the database.
     */
    public void deleteCard(int id) {
    }
}
