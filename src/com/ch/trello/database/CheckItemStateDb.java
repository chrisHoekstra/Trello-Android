package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.CheckItemStateVO;

public class CheckItemStateDb {
    public static final String DATABASE_TABLE = "checkItemStates";
    
    public static final String ID            = "_id";
    public static final String STATE         = "state";
    public static final String ID_CHECK_ITEM = "idCheckItem";
    public static final String ID_CARD       = "idCard";
    
    public static final String[] KEYS = new String[] {
        ID,
        STATE,
        ID_CHECK_ITEM,
        ID_CARD
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " text " +
        ");";

    public static CheckItemStateVO cursorToVo(Cursor cursor) {
        CheckItemStateVO checkItemState = new CheckItemStateVO();
        checkItemState._id         = cursor.getString(0);
        checkItemState.state       = cursor.getString(1);
        checkItemState.idCheckItem = cursor.getString(2);
        return checkItemState;
    }
    
    public static ContentValues voToValues(CheckItemStateVO checkItemState) {
        ContentValues values = new ContentValues();
        values.put(ID,            checkItemState._id);
        values.put(STATE,         checkItemState.state);
        values.put(ID_CHECK_ITEM, checkItemState.idCheckItem);
        return values;
    }
}
