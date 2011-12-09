package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.BoardListVO;

public class BoardListDb {
    public static final String DATABASE_TABLE = "boardLists";
    
    public static final String ID        = "_id";
    public static final String NAME      = "name";
    public static final String CLOSED    = "closed";
    public static final String POS       = "pos";
    public static final String ID_BOARD  = "idBoard";
    
    public static final String[] KEYS = new String[] {
        ID,
        NAME,
        CLOSED,
        POS,
        ID_BOARD
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
    
    public static BoardListVO cursorToVo(Cursor cursor) {
        BoardListVO boardList = new BoardListVO();
        boardList._id    = cursor.getString(0);
        boardList.name   = cursor.getString(1);
        boardList.closed = Boolean.getBoolean(cursor.getString(2));
        boardList.pos    = cursor.getInt(3);
        return boardList;
    }
    
    public static ContentValues voToValues(BoardListVO boardList) {
        ContentValues values = new ContentValues();
        values.put(ID,       boardList._id);
        values.put(NAME,     boardList.name);
        values.put(CLOSED,   boardList.closed);
        values.put(POS,      boardList.pos);
        return values;
    }
}
