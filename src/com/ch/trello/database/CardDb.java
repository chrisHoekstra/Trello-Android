package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.CardVO;

public class CardDb {
    public static final String DATABASE_TABLE     = "cards";
    
    public static final String ID                 = "_id";
    public static final String NAME               = "name";
    public static final String DESC               = "desc";
    public static final String DATE_LAST_ACTIVITY = "dateLastActivity";
    public static final String ID_BOARD           = "idBoard";
    public static final String ID_BOARD_LIST      = "idBoardList";
    public static final String CLOSED             = "closed";
    public static final String POS                = "pos";
    
    public static final String[] KEYS = new String[] {
        ID,
        NAME,
        DESC,
        DATE_LAST_ACTIVITY,
        ID_BOARD,
        ID_BOARD_LIST,
        POS,
        CLOSED
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " text, " +
        KEYS[4] + " text, " +
        KEYS[5] + " text, " +
        KEYS[6] + " integer, " +
        KEYS[7] + " text " +
        ");";
    
    public static CardVO cursorToVo(Cursor cursor) {
        CardVO card = new CardVO();
        card._id              = cursor.getString(0);
        card.name             = cursor.getString(1);
        card.desc             = cursor.getString(2);
        card.dateLastActivity = cursor.getString(3);
        card.idBoard          = cursor.getString(4);
        card.idList           = cursor.getString(5);
        card.pos              = cursor.getInt(6);
        card.closed           = Boolean.getBoolean(cursor.getString(7));
        
        return card;
    }
    
    public static ContentValues voToValues(CardVO card) {
        ContentValues values = new ContentValues();
        values.put(ID,                 card._id);
        values.put(NAME,               card.name);
        values.put(DESC,               card.desc);
        values.put(DATE_LAST_ACTIVITY, card.dateLastActivity);
        values.put(ID_BOARD,           card.idBoard);
        values.put(ID_BOARD_LIST,      card.idList);
        values.put(CLOSED,             card.closed);
        values.put(POS,                card.pos);
        
        return values;
    }
}
