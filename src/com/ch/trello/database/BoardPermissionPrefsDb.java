package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.BoardPermissionsPrefsVO;

public class BoardPermissionPrefsDb {
    public static final String DATABASE_TABLE   = "boardPermissionPrefs";
    
    public static final String ID               = "_id";
    public static final String PERMISSION_LEVEL = "permissionLevel";
    public static final String VOTING           = "voting";
    public static final String COMMENTS         = "comments";
    public static final String INVITATIONS      = "invitations";
    public static final String ID_BOARD         = "idBoard";
    
    public static final String[] KEYS = new String[] {
        ID,
        PERMISSION_LEVEL,
        VOTING,
        COMMENTS,
        INVITATIONS,
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
        KEYS[5] + " text " +
        ");";
    
    public static BoardPermissionsPrefsVO cursorToVo(Cursor cursor) {
        BoardPermissionsPrefsVO prefs = new BoardPermissionsPrefsVO();
        prefs.permissionLevel = cursor.getString(1);
        prefs.voting          = cursor.getString(2);
        prefs.comments        = cursor.getString(3);
        prefs.invitations     = cursor.getString(4);
        return prefs;
    }
    
    public static ContentValues voToValues(BoardPermissionsPrefsVO prefs) {
        ContentValues values = new ContentValues();
        values.put(PERMISSION_LEVEL, prefs.permissionLevel);
        values.put(VOTING,           prefs.voting);
        values.put(COMMENTS,         prefs.comments);
        values.put(INVITATIONS,      prefs.invitations);
        return values;
    }
}
