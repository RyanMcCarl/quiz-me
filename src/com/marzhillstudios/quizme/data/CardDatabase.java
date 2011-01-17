/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.marzhillstudios.util.Maybe;

/**
 * @author jwall
 *
 */
public class CardDatabase extends SQLiteOpenHelper {
	
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quiz_me_db";
    private static final String CARD_TABLE_NAME = "card_table";
    private static final String STATS_TABLE_NAME = "card_stats_table";
    private static final String TAGS_TABLE_NAME = "card_tags_table";
    
    private static final String CARDS_TABLE_COLUMNS =
        "'id' INT, 'title' TEXT, 'file' TEXT, 'deck' INT";
    private static final String STATS_TABLE_COLUMNS =
        "'card_id' INT, 'stat' TEXT, 'value' INT";
    private static final String TAGS_TABLE_COLUMNS = "'card_id', 'tag' TEXT";
    
    public CardDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String card_sql = "CREATE TABLE " + CARD_TABLE_NAME + " ( "
            + CARDS_TABLE_COLUMNS + " );";
        String stats_sql = "CREATE TABLE " + STATS_TABLE_NAME + " ( "
            + STATS_TABLE_COLUMNS + " );";
        String tags_sql = "CREATE TABLE " + TAGS_TABLE_NAME + " ( "
            + TAGS_TABLE_COLUMNS + " );";
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
    
    public Maybe<Card> upsertCard(Card card) {
        return new Maybe<Card>();
    public Card upsertCard(Card card) {
        return null;
    }
    
    public void deleteCard(Card card) {
        deleteCard(card.getId());
    }
    
    public void deleteCard(int id) {
    }
}
