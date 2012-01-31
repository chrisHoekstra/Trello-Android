package com.chrishoekstra.trello.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.chrishoekstra.trello.vo.BoardListVO;
import com.chrishoekstra.trello.vo.BoardVO;
import com.chrishoekstra.trello.vo.CardVO;
import com.chrishoekstra.trello.vo.MemberVO;
import com.chrishoekstra.trello.vo.NotificationVO;

public class TrelloModel {
    
    public TrelloModel() {
        mCardNotifications = new HashMap<String, Integer>();
        mMemberGravatars   = new HashMap<String, String>();
        mBoardListsByBoard = new HashMap<String, ArrayList<BoardListVO>>();
        mCardsByList       = new HashMap<String, ArrayList<CardVO>>();
    }
    
    public void setAllBoards(ArrayList<BoardVO> boards) {
        mAllBoards = boards;
    }
    
    public void setBoard(String boardId, ArrayList<BoardListVO> result) {
        mBoardListsByBoard.put(boardId, result);
    }

    public void setBoardList(String boardListId, ArrayList<CardVO> result) {
        mCardsByList.put(boardListId, result);
    }
    
    public void setUser(MemberVO user) {
        mUser = user;
    }
    
    public MemberVO getUser() {
        return mUser;
    }

    public void setNotifications(ArrayList<NotificationVO> notifications) {
        mNotifications = notifications;
        
        int count;
        for (NotificationVO notification : notifications) {
            if (notification.data.card != null) {
                count = 0;
                
                if (mCardNotifications.containsKey(notification.data.card.id)) {
                    count = mCardNotifications.get(notification.data.card.id) + 1;
                }

                mCardNotifications.put(notification.data.card.id, count);
            }
            
        }
    }
    
    public ArrayList<NotificationVO> getNotifications() {
        return mNotifications;
    }
    
    public int getNotificationCount(String cardId) {
        Integer count = mCardNotifications.get(cardId);
        
        if (count == null) {
            count = 0;
        }
        
        return count;
    }
    
    public String getGravatarId(String memberId) {
        return mMemberGravatars.get(memberId);
    }
    
    
    public ArrayList<BoardVO> getAllBoards() {
        return mAllBoards;
    }

    public BoardVO getBoard(String boardId) {
        for (BoardVO board : mAllBoards) {
            if (board.id.equals(boardId)) {
                return board;
            }
        }
        
        return null;
    }
    
    
    public ArrayList<BoardListVO> getBoardLists(String boardId) {
        return mBoardListsByBoard.get(boardId);
    }
    
    public BoardListVO getBoardList(String boardId, String boardListId) {
        ArrayList<BoardListVO> lists = mBoardListsByBoard.get(boardId);
        
        for (BoardListVO list : lists) {
            if (list.id.equals(boardListId)) {
                return list;
            }
        }
        
        return null;
    }

    
    public ArrayList<CardVO> getCards(String boardListId) {
        return mCardsByList.get(boardListId);
    }
    
    public CardVO getCard(String boardListId, String cardId) {
        ArrayList<CardVO> cards = mCardsByList.get(boardListId);
        
        for (CardVO card : cards) {
            if (card.id.equals(cardId)) {
                return card;
            }
        }
        
        return null;
    }
    
    // Variables
    private MemberVO mUser;
    private ArrayList<BoardVO> mAllBoards;
    private HashMap<String, ArrayList<BoardListVO>> mBoardListsByBoard;
    private HashMap<String, ArrayList<CardVO>> mCardsByList;
    private ArrayList<NotificationVO> mNotifications;
    private HashMap<String, Integer> mCardNotifications;
    private HashMap<String, String> mMemberGravatars;
}
