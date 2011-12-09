package com.ch.trello.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ch.trello.vo.AttachmentVO;

public class AttachmentDb {
    public static final String DATABASE_TABLE = "attachments";
    
    public static final String ID        = "_id";
    public static final String MEMBER_ID = "memberId";
    public static final String NAME      = "name";
    public static final String URL       = "url";
    public static final String BYTES     = "bytes";
    public static final String DATE      = "date";
    public static final String ID_CARD   = "idCard";
    
    public static final String[] KEYS = new String[] {
        ID,
        MEMBER_ID,
        NAME,
        URL,
        BYTES,
        DATE,
        ID_CARD
    };
    
    public static final String DATABASE_CREATE_STRING =
        "create table " + DATABASE_TABLE + 
        "(" +
        KEYS[0] + " text primary key, " + 
        KEYS[1] + " text, " +
        KEYS[2] + " text, " +
        KEYS[3] + " text, " +
        KEYS[4] + " integer, " +
        KEYS[5] + " text, " +
        KEYS[6] + " text " +
        ");";

    public static AttachmentVO cursorToVo(Cursor cursor) {
        AttachmentVO attachment = new AttachmentVO();
        attachment._id      = cursor.getString(0);
        attachment.idMember = cursor.getString(1);
        attachment.name     = cursor.getString(2);
        attachment.url      = cursor.getString(3);
        attachment.bytes    = cursor.getInt(4);
        attachment.date     = cursor.getString(5);
        return attachment;
    }
    
    public static ContentValues voToValues(AttachmentVO attachment) {
        ContentValues values = new ContentValues();
        values.put(ID,        attachment._id);
        values.put(MEMBER_ID, attachment.idMember);
        values.put(NAME,      attachment.name);
        values.put(URL,       attachment.url);
        values.put(BYTES,     attachment.bytes);
        values.put(DATE,      attachment.date);
        return values;
    }
}
