package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.CheckItemVO;

public class CheckItemDb {
    public static final String DATABASE_TABLE = "checkItems";
    
    public static final String ID            = "_id";
    public static final String NAME          = "name";
    public static final String TYPE          = "type";
    public static final String POS           = "pos";
    public static final String ID_CHECK_LIST = "idCheckList";
    
    public static final String[] KEYS = new String[] {
        ID,
        NAME,
        TYPE,
        POS,
        ID_CHECK_LIST
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " integer, " +
        KEYS[4] + " text " +
        ");";

    public static CheckItemVO cursorToVo(Cursor cursor) {
        CheckItemVO checkItem = new CheckItemVO();
        checkItem._id  = cursor.getString(0);
        checkItem.name = cursor.getString(1);
        checkItem.type = cursor.getString(2);
        checkItem.pos  = cursor.getInt(3);
        return checkItem;
    }
    
    public static ContentValues voToValues(CheckItemVO checkItem) {
        ContentValues values = new ContentValues();
        values.put(ID,   checkItem._id);
        values.put(NAME, checkItem.name);
        values.put(TYPE, checkItem.type);
        values.put(POS,  checkItem.pos);
        return values;
    }
}
