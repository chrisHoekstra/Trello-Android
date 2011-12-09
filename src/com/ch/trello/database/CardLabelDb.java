package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

public class CardLabelDb {
    public static final String DATABASE_TABLE = "cardLabels";
    
    public static final String ID      = "_id";
    public static final String LABEL   = "label";
    public static final String ID_CARD = "idCard";
    
    public static final String[] KEYS = new String[] {
        ID,
        LABEL,
        ID_CARD
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " integer primary key autoincrement, " +
        KEYS[1] + " text, " +
        KEYS[2] + " text " +
        ");";
    
    public static String cursorToVO(Cursor cursor) {
        return cursor.getString(1);
    }

    public static ContentValues voToValues(String value) {
        ContentValues values = new ContentValues();
        values.put(LABEL, value);
        return values;
    }
}
