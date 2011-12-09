package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.MemberPrefsVO;

public class MemberPrefsDb {
    public static final String DATABASE_TABLE        = "memberPrefs";
    
    public static final String ID                    = "_id";
    public static final String SEND_SUMMARIES        = "sendSummaries";
    public static final String MIN_BETWEEN_SUMMARIES = "minutesBetweenSummaries";
    public static final String ID_MEMBER             = "idMember";
    
    public static final String[] KEYS = new String[] {
        ID,
        SEND_SUMMARIES,
        MIN_BETWEEN_SUMMARIES,
        ID_MEMBER
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " integer primary key autoincrement, " +
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " text " +
        ");";
    
    public static MemberPrefsVO cursorToVo(Cursor cursor) {
        MemberPrefsVO prefs = new MemberPrefsVO();
        prefs.sendSummaries           = cursor.getString(1);
        prefs.minutesBetweenSummaries = cursor.getString(2);
        return prefs;
    }
    
    public static ContentValues voToValues(MemberPrefsVO prefs) {
        ContentValues values = new ContentValues();
        values.put(SEND_SUMMARIES,        prefs.sendSummaries);
        values.put(MIN_BETWEEN_SUMMARIES, prefs.minutesBetweenSummaries);
        return values;
    }
}
