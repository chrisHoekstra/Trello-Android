package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.BoardVO;

public class BoardDb {    
    public static final String DATABASE_TABLE     = "boards";
    
    public static final String ID                 = "_id";
    public static final String NAME               = "name";
    public static final String CLOSED             = "closed";
    public static final String ACTIONS_SINCE_LAST = "actionsSinceLast";
    
    public static final String[] KEYS = new String[] {
        ID,
        NAME,
        CLOSED,
        ACTIONS_SINCE_LAST
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " integer " +
        ");";
    
    public static BoardVO cursorToVo(Cursor cursor) {
        BoardVO board = new BoardVO();
        board._id    = cursor.getString(0);
        board.name   = cursor.getString(1);
        board.closed = Boolean.getBoolean(cursor.getString(2));
        board.nActionsSinceLastView = cursor.getInt(3);
        return board;
    }
    
    public static ContentValues voToValues(BoardVO board) {
        ContentValues values = new ContentValues();
        values.put(ID,                 board._id);
        values.put(NAME,               board.name);
        values.put(CLOSED,             board.closed);
        values.put(ACTIONS_SINCE_LAST, board.nActionsSinceLastView);
        return values;
    }
}
