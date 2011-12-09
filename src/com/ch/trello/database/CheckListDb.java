package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.CheckListVO;

public class CheckListDb {
    public static final String DATABASE_TABLE = "checkLists";
    
    public static final String ID      = "_id";
    public static final String NAME    = "name";
    public static final String ID_CARD = "idCard";
    
    public static final String[] KEYS = new String[] {
        ID,
        NAME,
        ID_CARD
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text " +
        ");";

    public static CheckListVO cursorToVo(Cursor cursor) {
        CheckListVO checkList = new CheckListVO();
        checkList._id  = cursor.getString(0);
        checkList.name = cursor.getString(1);
        return checkList;
    }
    
    public static ContentValues voToValues(CheckListVO checkList) {
        ContentValues values = new ContentValues();
        values.put(ID,   checkList._id);
        values.put(NAME, checkList.name);
        return values;
    }
}
