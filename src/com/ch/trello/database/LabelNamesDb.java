package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.LabelNamesVO;

public class LabelNamesDb {
    public static final String DATABASE_TABLE = "labelNames";
    
    public static final String ID       = "_id";
    public static final String RED      = "red";
    public static final String ORANGE   = "orange";
    public static final String YELLOW   = "yellow";
    public static final String GREEN    = "green";
    public static final String BLUE     = "blue";
    public static final String PURPLE   = "purple";
    public static final String ID_BOARD = "idBoard";
    
    public static final String[] KEYS = new String[] {
        ID,
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        PURPLE,
        ID_BOARD
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " integer primary key autoincrement, " +
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " text, " +
        KEYS[4] + " text, " +
        KEYS[5] + " text, " +
        KEYS[6] + " text, " +
        KEYS[7] + " text " +
        ");";

    public static LabelNamesVO cursorToVo(Cursor cursor) {
        LabelNamesVO checkList = new LabelNamesVO();
        checkList.red    = cursor.getString(1);
        checkList.orange = cursor.getString(2);
        checkList.yellow = cursor.getString(3);
        checkList.green  = cursor.getString(4);
        checkList.blue   = cursor.getString(5);
        checkList.purple = cursor.getString(6);
        return checkList;
    }
    
    public static ContentValues voToValues(LabelNamesVO vo) {
        ContentValues values = new ContentValues();
        values.put(RED,    vo.red);
        values.put(ORANGE, vo.orange);
        values.put(YELLOW, vo.yellow);
        values.put(GREEN,  vo.green);
        values.put(BLUE,   vo.blue);
        values.put(PURPLE, vo.purple);
        return values;
    }
}
