package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.MemberVO;

public class MemberDb {
    public static final String DATABASE_TABLE = "member";
    
    public static final String ID                 = "_id";
    public static final String EMAIL              = "email";
    public static final String USERNAME           = "username";
    public static final String FULL_NAME          = "fullName";
    public static final String INITIALS           = "initials";
    public static final String STATUS             = "status";
    public static final String GRAVATAR           = "gravatar";
    public static final String IS_OPEN_ID_ACCOUNT = "isOpenIdAccount";
    
    public static final String[] KEYS = new String[] {
        ID,
        EMAIL,
        USERNAME,
        FULL_NAME,
        INITIALS,
        STATUS,
        GRAVATAR,
        IS_OPEN_ID_ACCOUNT
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
        KEYS[6] + " text, " +
        KEYS[7] + " text " +
        ");";
    
    public static MemberVO cursorToVo(Cursor cursor) {
        MemberVO vo = new MemberVO();
        vo._id             = cursor.getString(0);
        vo.email           = cursor.getString(1);
        vo.username        = cursor.getString(2);
        vo.fullName        = cursor.getString(3);
        vo.initials        = cursor.getString(4);
        vo.status          = cursor.getString(5);
        vo.gravatar        = cursor.getString(6);
        vo.isOpenIdAccount = Boolean.getBoolean(cursor.getString(7));
        return vo;
    }
    
    public static ContentValues voToValues(MemberVO vo) {
        ContentValues values = new ContentValues();
        values.put(ID,                 vo._id);
        values.put(EMAIL,              vo.email);
        values.put(USERNAME,           vo.username);
        values.put(FULL_NAME,         vo.fullName);
        values.put(INITIALS,           vo.initials);
        values.put(STATUS,             vo.status);
        values.put(GRAVATAR,           vo.gravatar);
        values.put(IS_OPEN_ID_ACCOUNT, vo.isOpenIdAccount);
        return values;
    }
}
