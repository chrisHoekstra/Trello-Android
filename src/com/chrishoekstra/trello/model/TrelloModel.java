package com.chrishoekstra.trello.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.chrishoekstra.trello.vo.AllBoardsResultVO;
import com.chrishoekstra.trello.vo.BoardListVO;
import com.chrishoekstra.trello.vo.BoardResultVO;
import com.chrishoekstra.trello.vo.BoardVO;
import com.chrishoekstra.trello.vo.CardVO;
import com.chrishoekstra.trello.vo.MemberVO;
import com.chrishoekstra.trello.vo.NotificationVO;
import com.chrishoekstra.trello.vo.NotificationsResultVO;

public class TrelloModel {
    
    // Singleton stuff
    private static TrelloModel model;
    public  static TrelloModel getInstance() {
        if (model == null) {
            model = new TrelloModel();
            model.mCardNotifications = new HashMap<String, Integer>();
            model.mMemberGravatars = new HashMap<String, String>();
            model.mBoardListsByBoard = new HashMap<String, ArrayList<BoardListVO>>();
            model.mCardsByList = new HashMap<String, ArrayList<CardVO>>();
        }
        
        return model;
    }
    
    
    // Listener Declaration

    public interface OnUserDataReceivedListener {
        void onUserDataReceived(TrelloModel model, Boolean result);
    }

    public interface OnAllBoardsReceivedListener {
        void onAllBoardsReceviedEvent(TrelloModel model, ArrayList<BoardVO> result);
    }
    
    public interface OnBoardListsReceivedListener {
        void onBoardListReceviedEvent(TrelloModel model, String boardId, ArrayList<BoardListVO> result);
    }

    public interface OnCardsReceivedListener {
        void onCardsRecivedEvent(TrelloModel model, String boardListId, ArrayList<CardVO> result);
    }

    public interface OnCardAddedListener {
        void onCardAddedEvent(TrelloModel model, Boolean result);
    }
    
    
    
    // Listener Arrays
    private final ConcurrentLinkedQueue<OnUserDataReceivedListener> onUserDataReceivedListeners           = new ConcurrentLinkedQueue<OnUserDataReceivedListener>();
    private final ConcurrentLinkedQueue<OnBoardListsReceivedListener> onBoardListsReceivedListeners       = new ConcurrentLinkedQueue<OnBoardListsReceivedListener>();
    private final ConcurrentLinkedQueue<OnCardsReceivedListener> onCardsReceivedListeners                 = new ConcurrentLinkedQueue<OnCardsReceivedListener>();
    private final ConcurrentLinkedQueue<OnAllBoardsReceivedListener> onAllBoardsReceivedListeners         = new ConcurrentLinkedQueue<OnAllBoardsReceivedListener>();
    private final ConcurrentLinkedQueue<OnCardAddedListener> onCardAddedListeners                         = new ConcurrentLinkedQueue<OnCardAddedListener>();
    
    
    // Alert Listeners

    private final void alertOnUserDataReceivedListeners(Boolean result) {
        for (final OnUserDataReceivedListener listener : onUserDataReceivedListeners)
            listener.onUserDataReceived(this, result);
    }
    
    private final void alertOnCardsReceivedListeners(String boardListId, ArrayList<CardVO> result) {
        for (final OnCardsReceivedListener listener : onCardsReceivedListeners)
            listener.onCardsRecivedEvent(this, boardListId, result);
    }
    
    private final void alertOnBoardListsReceivedListeners(String boardId, ArrayList<BoardListVO> result) {
        for (final OnBoardListsReceivedListener listener : onBoardListsReceivedListeners)
            listener.onBoardListReceviedEvent(this, boardId, result);
    }
    
    private final void alertOnAllBoardsReceivedListeners(ArrayList<BoardVO> result) {
        for (final OnAllBoardsReceivedListener listener : onAllBoardsReceivedListeners)
            listener.onAllBoardsReceviedEvent(this, result);
    }
    
    private final void alertOnCardAddedListeners(Boolean result) {
        for (final OnCardAddedListener listener : onCardAddedListeners)
            listener.onCardAddedEvent(this, result);
    }
    
    
    
    // Add Listener

    public final void addListener(OnUserDataReceivedListener listener) {
        synchronized (onUserDataReceivedListeners) {
            onUserDataReceivedListeners.add(listener);
        }
    }

    public final void addListener(OnCardsReceivedListener listener) {
        synchronized (onCardsReceivedListeners) {
            onCardsReceivedListeners.add(listener);
        }
    }

    public final void addListener(OnAllBoardsReceivedListener listener) {
        synchronized (onAllBoardsReceivedListeners) {
            onAllBoardsReceivedListeners.add(listener);
        }
    }

    public final void addListener(OnBoardListsReceivedListener listener) {
        synchronized (onBoardListsReceivedListeners) {
            onBoardListsReceivedListeners.add(listener);
        }
    }

    public final void addListener(OnCardAddedListener listener) {
        synchronized (onCardAddedListeners) {
            onCardAddedListeners.add(listener);
        }
    }
    

    
    // Remove Listener

    public final void removeListener(OnUserDataReceivedListener listener) {
        synchronized (onUserDataReceivedListeners) {
            onUserDataReceivedListeners.remove(listener);
        }
    }

    public final void removeListener(OnCardsReceivedListener listener) {
        synchronized (onCardsReceivedListeners) {
            onCardsReceivedListeners.remove(listener);
        }
    }

    public final void removeListener(OnBoardListsReceivedListener listener) {
        synchronized (onBoardListsReceivedListeners) {
            onBoardListsReceivedListeners.remove(listener);
        }
    }

    public final void removeListener(OnAllBoardsReceivedListener listener) {
        synchronized (onAllBoardsReceivedListeners) {
            onAllBoardsReceivedListeners.remove(listener);
        }
    }

    public final void removeListener(OnCardAddedListener listener) {
        synchronized (onCardAddedListeners) {
            onCardAddedListeners.remove(listener);
        }
    }
    
    
    
    // Listener Methods
    public void userDataReceived(Boolean result) {
        alertOnUserDataReceivedListeners(result);
    }
    
    public void allBoardsReceived(ArrayList<BoardVO> result) {
        mAllBoards = result;
        
        alertOnAllBoardsReceivedListeners(result);
    }
    
    public void boardListsReceived(String boardId, ArrayList<BoardListVO> result) {
        mBoardListsByBoard.put(boardId, result);
        
        alertOnBoardListsReceivedListeners(boardId, result);
    }

    public void cardsReceived(String boardListId, ArrayList<CardVO> result) {
        mCardsByList.put(boardListId, result);
        
        alertOnCardsReceivedListeners(boardListId, result);
    }
    
    public void cardAdded(Boolean result) {
        alertOnCardAddedListeners(result);
    }
    
    
    // Access Methods


    public void setAllBoards(ArrayList<BoardVO> boards) {
        mAllBoards = boards;
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
                    count = mCardNotifications.get(notification.data.card.id);
                }

                mCardNotifications.put(notification.data.card.id, count);
            }
            
        }
    }
    
    public ArrayList<NotificationVO> getNotifications() {
        return mNotifications;
    }
    
    public int getNotificationCount(String cardId) {
        return mCardNotifications.get(cardId);
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
