package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.ActionVO;

public class ActionDb {
    public static final String DATABASE_TABLE    = "actions";
    
    public static final String ID                = "_id";
    public static final String TYPE              = "type";
    public static final String DATE              = "date";
    public static final String ID_MEMBER_CREATOR = "idMemberCreator";
    public static final String VERSION           = "version";
    
    public static final String[] KEYS = new String[] {
        ID,
        TYPE,
        DATE,
        ID_MEMBER_CREATOR,
        VERSION
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " text, " +
        KEYS[4] + " integer " +
        ");";

    public static ActionVO cursorToVo(Cursor cursor) {
        ActionVO action = new ActionVO();
        action._id             = cursor.getString(0);
        action.type            = cursor.getString(1);
        action.date            = cursor.getString(2);
        action.idMemberCreator = cursor.getString(3);
        action.version         = cursor.getInt(4);
        return action;
    }
    
    public static ContentValues voToValues(ActionVO action) {
        ContentValues values = new ContentValues();
        values.put(ID,                action._id);
        values.put(TYPE,              action.type);
        values.put(DATE,              action.date);
        values.put(ID_MEMBER_CREATOR, action.idMemberCreator);
        values.put(VERSION,           action.version);
        return values;
    }
}
