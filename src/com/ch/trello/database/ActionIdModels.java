package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

public class ActionIdModels {
    public static final String DATABASE_TABLE  = "actionIdModels";
    
    public static final String ID        = "_id";
    public static final String ID_MODEL  = "idModel";
    public static final String ID_ACTION = "idAction";
    
    public static final String[] KEYS = new String[] {
        ID,
        ID_MODEL,
        ID_ACTION
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
        values.put(ID_MODEL, value);
        return values;
    }
}
