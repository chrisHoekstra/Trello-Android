package com.ch.trello.database;

import com.ch.trello.vo.BadgeVO;

import android.content.ContentValues;
import android.database.Cursor;

public class BadgeDb {
    public static final String DATABASE_TABLE = "badges";
    
    public static final String ID                  = "_id";
    public static final String FOGBUGZ             = "fogbugz";
    public static final String CHECK_ITEMS         = "checkItems";
    public static final String CHECK_ITEMS_CHECKED = "checkItemsChecked";
    public static final String COMMENTS            = "comments";
    public static final String ATTACHEMENTS        = "attachements";
    public static final String DESCRIPTION         = "description";
    public static final String DUE                 = "due";
    public static final String ID_CARD             = "idCard";
    
    public static final String[] KEYS = new String[] {
        ID,
        FOGBUGZ,
        CHECK_ITEMS,
        CHECK_ITEMS_CHECKED,
        COMMENTS,
        ATTACHEMENTS,
        DESCRIPTION,
        DUE,
        ID_CARD
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " integer primary key autoincrement, " +
        KEYS[1] + " text, " +
        KEYS[2] + " integer, " +
        KEYS[3] + " integer, " +
        KEYS[4] + " integer, " +
        KEYS[5] + " integer, " +
        KEYS[6] + " text, " +
        KEYS[7] + " text, " +
        KEYS[8] + " text " +
        ");";
    
    public static BadgeVO cursorToVO(Cursor cursor) {
        BadgeVO badge = new BadgeVO();
        badge.fogbugz           = cursor.getString(1);
        badge.checkItems        = cursor.getInt(2);
        badge.checkItemsChecked = cursor.getInt(3);
        badge.comments          = cursor.getInt(4);
        badge.attachments       = cursor.getInt(5);
        badge.description       = Boolean.getBoolean(cursor.getString(6));
        badge.due               = cursor.getString(7);
        return badge;
    }

    public static ContentValues voToValues(BadgeVO badge) {
        ContentValues values = new ContentValues();
        values.put(FOGBUGZ,             badge.fogbugz);
        values.put(CHECK_ITEMS,         badge.checkItems);
        values.put(CHECK_ITEMS_CHECKED, badge.checkItemsChecked);
        values.put(COMMENTS,            badge.comments);
        values.put(ATTACHEMENTS,        badge.attachments);
        values.put(DESCRIPTION,         badge.description);
        values.put(DUE,                 badge.due);
        return values;
    }
}
