package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.MembershipVO;

public class MembershipDb {
    public static final String DATABASE_TABLE = "memberships";
    
    public static final String ID             = "_id";
    public static final String MEMBER_TYPE    = "memberType";
    public static final String ID_MEMBER      = "idMember";
    
    public static final String[] KEYS = new String[] {
        ID,
        MEMBER_TYPE,
        ID_MEMBER
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " +
        KEYS[1] + " text, " +
        KEYS[2] + " text " +
        ");";
    
    public static MembershipVO cursorToVo(Cursor cursor) {
        MembershipVO vo = new MembershipVO();
        vo._id        = cursor.getString(1);
        vo.memberType = cursor.getString(2);
        vo.idMember   = cursor.getString(2);
        return vo;
    }
    
    public static ContentValues voToValues(MembershipVO vo) {
        ContentValues values = new ContentValues();
        values.put(ID,          vo._id);
        values.put(MEMBER_TYPE, vo.memberType);
        values.put(ID_MEMBER,   vo.idMember);
        return values;
    }
}
