package com.ch.trello.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TrelloDbHelper extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "com.ch.trello";
    
    public TrelloDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AttachmentDb.DATABASE_CREATE_STRING);
        db.execSQL(BadgeDb.DATABASE_CREATE_STRING);
        db.execSQL(BoardDb.DATABASE_CREATE_STRING);
        db.execSQL(BoardListDb.DATABASE_CREATE_STRING);
        db.execSQL(BoardPermissionPrefsDb.DATABASE_CREATE_STRING);
        db.execSQL(BoardPrefsDb.DATABASE_CREATE_STRING);
        db.execSQL(CardDb.DATABASE_CREATE_STRING);
        db.execSQL(CardIdMemberDb.DATABASE_CREATE_STRING);
        db.execSQL(CardIdMembersVotedDb.DATABASE_CREATE_STRING);
        db.execSQL(CardLabelDb.DATABASE_CREATE_STRING);
        db.execSQL(CheckItemDb.DATABASE_CREATE_STRING);
        db.execSQL(CheckItemStateDb.DATABASE_CREATE_STRING);
        db.execSQL(CheckListDb.DATABASE_CREATE_STRING);
        db.execSQL(LabelNamesDb.DATABASE_CREATE_STRING);
        db.execSQL(MemberDb.DATABASE_CREATE_STRING);
        db.execSQL(MemberIdBoardsDb.DATABASE_CREATE_STRING);
        db.execSQL(MemberIdBoardsInvitedDb.DATABASE_CREATE_STRING);
        db.execSQL(MemberIdBoardsPinnedDb.DATABASE_CREATE_STRING);
        db.execSQL(MemberIdOrganizationsDb.DATABASE_CREATE_STRING);
        db.execSQL(MemberIdOrganizationsInvitedDb.DATABASE_CREATE_STRING);
        db.execSQL(MemberPrefsDb.DATABASE_CREATE_STRING);
        db.execSQL(MembershipDb.DATABASE_CREATE_STRING);
        db.execSQL(OrganizationDb.DATABASE_CREATE_STRING);
        db.execSQL(OrganizationPrefsDb.DATABASE_CREATE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DATABASE_NAME, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        
        db.execSQL("DROP TABLE IF EXISTS " + AttachmentDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BadgeDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BoardDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BoardListDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BoardPermissionPrefsDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BoardPrefsDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CardDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CardIdMemberDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CardIdMembersVotedDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CardLabelDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CheckItemDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CheckItemStateDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CheckListDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LabelNamesDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MemberDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MemberIdBoardsDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MemberIdBoardsInvitedDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MemberIdBoardsPinnedDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MemberIdOrganizationsDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MemberIdOrganizationsInvitedDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MemberPrefsDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MembershipDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OrganizationDb.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OrganizationPrefsDb.DATABASE_TABLE);
        
        onCreate(db);
    }
}
