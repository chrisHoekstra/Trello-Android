package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.NotificationVO;

public class NotificationDb {
    public static final String DATABASE_TABLE    = "notifications";
    
    public static final String ID                = "_id";
    public static final String TYPE              = "type";
    public static final String DATE              = "date";
    public static final String ID_MEMBER_CREATOR = "idMemberCreator";
    public static final String IS_UNREAD         = "isUnread";
    
    public static final String[] KEYS = new String[] {
        ID,
        TYPE,
        DATE,
        ID_MEMBER_CREATOR,
        IS_UNREAD
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " text, " +
        KEYS[4] + " text " +
        ");";

    public static NotificationVO cursorToVo(Cursor cursor) {
        NotificationVO notification = new NotificationVO();
        notification._id             = cursor.getString(0);
        notification.type            = cursor.getString(1);
        notification.date            = cursor.getString(2);
        notification.idMemberCreator = cursor.getString(3);
        notification.isUnread        = Boolean.getBoolean(cursor.getString(4));
        return notification;
    }
    
    public static ContentValues voToValues(NotificationVO notification) {
        ContentValues values = new ContentValues();
        values.put(ID,                notification._id);
        values.put(TYPE,              notification.type);
        values.put(DATE,              notification.date);
        values.put(ID_MEMBER_CREATOR, notification.idMemberCreator);
        values.put(IS_UNREAD,         notification.isUnread);
        return values;
    }
}
