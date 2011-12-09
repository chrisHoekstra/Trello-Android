package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.BoardPrefsVO;

public class BoardPrefsDb {
    public static final String DATABASE_TABLE        = "boardPrefs";
    
    public static final String ID                         = "_id";
    public static final String SHOW_SIDEBAR               = "showSidebar";
    public static final String SHOW_SIDEBAR_MEMBERS       = "showSidebarMembers";
    public static final String SHOW_SIDEBAR_BOARD_ACTIONS = "showSidebarBoardActions";
    public static final String SHOW_SIDEBAR_ACTIVITY      = "showSidebarActivity";
    public static final String SHOW_LIST_GUIDE            = "showListGuide";
    public static final String ID_BOARD                   = "idBoard";
    
    public static final String[] KEYS = new String[] {
        ID,
        SHOW_SIDEBAR,
        SHOW_SIDEBAR_MEMBERS,
        SHOW_SIDEBAR_BOARD_ACTIONS,
        SHOW_SIDEBAR_ACTIVITY,
        SHOW_LIST_GUIDE,
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
        KEYS[6] + " text " +
        ");";
    
    public static BoardPrefsVO cursorToVo(Cursor cursor) {
        BoardPrefsVO prefs = new BoardPrefsVO();
        prefs.showSidebar             = Boolean.getBoolean(cursor.getString(1));
        prefs.showSidebarMembers      = Boolean.getBoolean(cursor.getString(2));
        prefs.showSidebarBoardActions = Boolean.getBoolean(cursor.getString(3));
        prefs.showSidebarActivity     = Boolean.getBoolean(cursor.getString(4));
        prefs.showListGuide           = Boolean.getBoolean(cursor.getString(5));
        return prefs;
    }
    
    public static ContentValues voToValues(BoardPrefsVO prefs) {
        ContentValues values = new ContentValues();
        values.put(SHOW_SIDEBAR,               prefs.showSidebar);
        values.put(SHOW_SIDEBAR_MEMBERS,       prefs.showSidebarMembers);
        values.put(SHOW_SIDEBAR_BOARD_ACTIONS, prefs.showSidebarBoardActions);
        values.put(SHOW_SIDEBAR_ACTIVITY,      prefs.showSidebarActivity);
        values.put(SHOW_LIST_GUIDE,            prefs.showListGuide);
        return values;
    }
}
