package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.OrganizationPrefsVO;

public class OrganizationPrefsDb {
    public static final String DATABASE_TABLE   = "organizationPrefs";
    
    public static final String ID               = "_id";
    public static final String PERMISSION_LEVEL = "permissionLevel";
    public static final String ID_ORGANIZATION  = "idOrganization";
    
    public static final String[] KEYS = new String[] {
        ID,
        PERMISSION_LEVEL,
        ID_ORGANIZATION
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " integer primary key autoincrement, " +
        KEYS[1] + " text, " +
        KEYS[2] + " text " +
        ");";
    
    public static OrganizationPrefsVO cursorToVo(Cursor cursor) {
        OrganizationPrefsVO prefs = new OrganizationPrefsVO();
        prefs.permissionLevel = cursor.getString(1);
        return prefs;
    }
    
    public static ContentValues voToValues(OrganizationPrefsVO prefs) {
        ContentValues values = new ContentValues();
        values.put(PERMISSION_LEVEL, prefs.permissionLevel);
        return values;
    }
}
