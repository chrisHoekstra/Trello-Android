package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.OrganizationVO;

public class OrganizationDb {
    public static final String DATABASE_TABLE     = "organizations";
    
    public static final String ID                 = "_id";
    public static final String NAME               = "name";
    public static final String DISPLAY_NAME       = "desc";
    
    public static final String[] KEYS = new String[] {
        ID,
        NAME,
        DISPLAY_NAME
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text " +
        ");";
    
    public static OrganizationVO cursorToVo(Cursor cursor) {
        OrganizationVO organization = new OrganizationVO();
        organization._id         = cursor.getString(0);
        organization.name        = cursor.getString(1);
        organization.displayName = cursor.getString(2);
        return organization;
    }
    
    public static ContentValues voToValues(OrganizationVO vo) {
        ContentValues values = new ContentValues();
        values.put(ID,           vo._id);
        values.put(NAME,         vo.name);
        values.put(DISPLAY_NAME, vo.displayName);
        
        return values;
    }
}
