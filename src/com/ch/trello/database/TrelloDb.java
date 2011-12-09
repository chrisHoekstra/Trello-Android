package com.ch.trello.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ch.trello.vo.BoardListVO;
import com.ch.trello.vo.BoardVO;
import com.ch.trello.vo.CardVO;
import com.ch.trello.vo.MemberVO;
import com.ch.trello.vo.OrganizationPrefsVO;
import com.ch.trello.vo.OrganizationVO;

public class TrelloDb {

    private static TrelloDb mAdapter;
    private static SQLiteDatabase mDb;
    
    private Context mContext;
    
    public TrelloDb(Context context) {
        this.mContext = context;
    }

    public TrelloDb open() throws SQLException {
        TrelloDbHelper helper = new TrelloDbHelper(mContext);
        mDb = helper.getWritableDatabase();
        mDb.setLockingEnabled(true);
        return this;
    }
    
    public static synchronized TrelloDb getInstance(Context context) {
        if (mAdapter == null) {
            mAdapter = new TrelloDb(context);
            mAdapter.open();
        }
        
        return mAdapter;
    }
    
    
    
    /**
     * 
     * CREATE
     * 
     */
    
    public boolean createOrganization(OrganizationVO organization) {
        boolean result = true;
        
        result &= createOrganizationPrefs(organization.prefs);
        result &= mDb.insertOrThrow(OrganizationDb.DATABASE_TABLE, null, OrganizationDb.voToValues(organization)) >= 0;
        
        return result;
    }
    
    public boolean createOrganizations(List<OrganizationVO> organizations) {
        boolean result = true;
        
        mDb.beginTransaction();
        for (OrganizationVO organization : organizations) {
            result &= createOrganization(organization);
        }
        mDb.endTransaction();
        
        return result;
    }
    
    public boolean createBoard(BoardVO board) {
        boolean result = true;
        
        if (board.lists != null) {
            for (BoardListVO boardList : board.lists) {
                result &= createBoardList(boardList, board._id);
            }
        }
        
        result &= mDb.insertOrThrow(BoardDb.DATABASE_TABLE, null, BoardDb.voToValues(board)) >= 0; 
        
        return result;
    }
    
    public boolean createBoards(List<BoardVO> boards) {
        boolean result = true;
        
        mDb.beginTransaction();
        for (BoardVO board : boards) {
            result &= createBoard(board);
        }
        mDb.endTransaction();
        
        return result;
    }
    
    public boolean createBoardList(BoardListVO boardList, String boardId) {
        ContentValues values = BoardListDb.voToValues(boardList);
        values.put(BoardListDb.ID_BOARD, boardId);
        
        return mDb.insertOrThrow(BoardListDb.DATABASE_TABLE, null, values) >= 0;
    }
    
    public boolean createCard(CardVO card) {
        return mDb.insertOrThrow(CardDb.DATABASE_TABLE, null, CardDb.voToValues(card)) >= 0;
    }

    public boolean createCards(List<CardVO> cards) {
        boolean result = true;
        
        mDb.beginTransaction();
        for (CardVO card : cards) {
            result &= createCard(card);
        }
        mDb.endTransaction();
        
        return result;
    }
    
    public boolean createOrganizationPrefs(OrganizationPrefsVO prefs) {
        return mDb.insertOrThrow(OrganizationPrefsDb.DATABASE_TABLE, null, OrganizationPrefsDb.voToValues(prefs)) >= 0;
    }
    
    public boolean createMember(MemberVO member) {
        boolean result = true;
        
        result &= mDb.insertOrThrow(MemberPrefsDb.DATABASE_TABLE, null, MemberPrefsDb.voToValues(member.prefs)) >= 0;
        
        if (member.idBoards != null) {
            for (String id : member.idBoards) {
                result &= mDb.insertOrThrow(MemberIdBoardsDb.DATABASE_TABLE, null, MemberIdBoardsDb.voToValues(id)) >= 0;                
            }
        }
        
        if (member.idBoardsPinned != null) {
            for (String id : member.idBoardsPinned) {
                result &= mDb.insertOrThrow(MemberIdBoardsPinnedDb.DATABASE_TABLE, null, MemberIdBoardsPinnedDb.voToValues(id)) >= 0;                
            }
        }
        
        if (member.idBoardsInvited != null) {
            for (String id : member.idBoardsInvited) {
                result &= mDb.insertOrThrow(MemberIdBoardsInvitedDb.DATABASE_TABLE, null, MemberIdBoardsInvitedDb.voToValues(id)) >= 0;                
            }
        }
        
        if (member.idOrganizations != null) {
            for (String id : member.idOrganizations) {
                result &= mDb.insertOrThrow(MemberIdOrganizationsDb.DATABASE_TABLE, null, MemberIdOrganizationsDb.voToValues(id)) >= 0;                
            }
        }
        
        if (member.idOrganizationsInvited != null) {
            for (String id : member.idOrganizationsInvited) {
                result &= mDb.insertOrThrow(MemberIdOrganizationsInvitedDb.DATABASE_TABLE, null, MemberIdOrganizationsInvitedDb.voToValues(id)) >= 0;                
            }
        }
        
        result &= mDb.insertOrThrow(MemberDb.DATABASE_TABLE, null, MemberDb.voToValues(member)) >= 0;
        
        return result;
    }
    
    public boolean createMembers(List<MemberVO> members) {
        boolean result = true;
        
        mDb.beginTransaction();
        for (MemberVO member : members) {
            result &= createMember(member);
        }
        mDb.endTransaction();
        
        return result;
    }
    
    
    
    
    /**
     * 
     * READ
     * 
     */
    
    public List<BoardVO> readAllBoards() {
        ArrayList<BoardVO> boards = new ArrayList<BoardVO>();
        
        Cursor cursor = mDb.query(
                BoardDb.DATABASE_TABLE, 
                BoardDb.KEYS,
                null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            BoardVO board;
            do {
                board = BoardDb.cursorToVo(cursor);
                board.lists = readBoardListByBoard(board._id);
                boards.add(board);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        
        return boards;
    }

    public BoardVO readBoard(String id) {
        BoardVO board;
        Cursor cursor = mDb.query(
                true,
                BoardDb.DATABASE_TABLE, 
                BoardDb.KEYS,
                BoardDb.ID + "=?", 
                new String[] {id}, null, null, null, null);
        
        if (cursor != null) {
            cursor.moveToFirst();
        }
        
        board = BoardDb.cursorToVo(cursor);
        board.lists = readBoardListByBoard(board._id);
        cursor.close();
        
        return board;
    }
    
    public ArrayList<BoardListVO> readBoardListByBoard(String id) {
        ArrayList<BoardListVO> boardLists = new ArrayList<BoardListVO>();
        
        Cursor cursor = mDb.query(
                true,
                BoardListDb.DATABASE_TABLE, 
                BoardListDb.KEYS,
                BoardListDb.ID + "=?", 
                new String[] {id}, null, null, null, null);
        
        if (!cursor.isAfterLast()) {
            do {
                boardLists.add(BoardListDb.cursorToVo(cursor));
            } while (cursor.moveToNext());
        }
        
        boardLists.trimToSize();
        
        return boardLists;
    }
    
    public CardVO readCard(String id) {
        CardVO card;
        Cursor cursor = mDb.query(
                true,
                CardDb.DATABASE_TABLE, 
                CardDb.KEYS,
                CardDb.ID + "=?", 
                new String[] {id}, null, null, null, null);
        
        if (cursor != null) {
            cursor.moveToFirst();
        }
        
        card = CardDb.cursorToVo(cursor);
        cursor.close();
        
        return card;
    }
    
    
    
    /**
     * 
     * UPDATE
     * 
     */
    
    public boolean updateBoard(String id, BoardVO board) {
        if (board.lists != null) {
            for (BoardListVO boardList : board.lists) {
                createBoardList(boardList, board._id);
            }
        }
        
        ContentValues values = BoardDb.voToValues(board);

        return mDb.update(BoardDb.DATABASE_TABLE, values, BoardDb.ID + "=?", new String[]{id}) > 0;
    }
    
    
    
    
    
    /**
     * 
     * DELETE
     * 
     */
    
    public boolean deleteAllBoards() {
        return mDb.delete(BoardDb.DATABASE_TABLE, "1", null) > 0;
    }
    
    public boolean deleteBoard(String id) {
        return mDb.delete(BoardDb.DATABASE_TABLE, BoardDb.ID + "=?", new String[] {id}) > 0;
    }
}
