package com.ch.trello.model;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ch.trello.vo.BoardResultVO;
import com.ch.trello.vo.BoardVO;

public class TrelloModel {
    
    // Singleton stuff
    private static TrelloModel model;
    public  static TrelloModel getInstance() {
        if (model == null) {
            model = new TrelloModel();
        }
        return model;
    }
    
    
    // Listener Declaration
    public interface OnLoginCompleteListener {
        void onLoginCompleteEvent(TrelloModel model);
    }

    public interface OnBoardReceivedListener {
        void onBoardReceveidEvent(TrelloModel model, BoardResultVO result);
    }
    
    
    // Listener Arrays
    private final ConcurrentLinkedQueue<OnLoginCompleteListener> onLoginCompleteListeners = new ConcurrentLinkedQueue<OnLoginCompleteListener>();
    private final ConcurrentLinkedQueue<OnBoardReceivedListener> onBoardReceivedListeners = new ConcurrentLinkedQueue<OnBoardReceivedListener>();
    
    
    // Alert Listeners
    private final void alertOnLoginCompleteListeners() {
        for (final OnLoginCompleteListener listener : onLoginCompleteListeners)
            listener.onLoginCompleteEvent(this);
    }
    
    private final void alertOnBoardReceivedListeners(BoardResultVO result) {
        for (final OnBoardReceivedListener listener : onBoardReceivedListeners)
            listener.onBoardReceveidEvent(this, result);
    }
    

    // Add Listener
    public final void addListener(OnLoginCompleteListener listener) {
        synchronized (onLoginCompleteListeners) {
            onLoginCompleteListeners.add(listener);
        }
    }

    public final void addListener(OnBoardReceivedListener listener) {
        synchronized (onBoardReceivedListeners) {
            onBoardReceivedListeners.add(listener);
        }
    }

    
    // Remove Listener
    public final void removeListener(OnLoginCompleteListener listener) {
        synchronized (onLoginCompleteListeners) {
            onLoginCompleteListeners.remove(listener);
        }
    }

    public final void removeListener(OnBoardReceivedListener listener) {
        synchronized (onBoardReceivedListeners) {
            onBoardReceivedListeners.remove(listener);
        }
    }
    
    
    // Methods
    public ArrayList<BoardVO> getBoards() {
        return mBoards;
    }
    
    public void setBoards(ArrayList<BoardVO> value) {
        mBoards = value;
    }
    
    public BoardResultVO getCurrentBoard() {
        return mCurrentBoard;
    }
    
    public void setCurrentBoard(BoardResultVO result) {
        mCurrentBoard = result;
    }
    
    public void boardReceived(BoardResultVO result) {
        alertOnBoardReceivedListeners(result);
    }
    
    
    // Variables
    private ArrayList<BoardVO> mBoards;
    private BoardResultVO mCurrentBoard;
    
    // Model functions
    public void loginComplete() {
        alertOnLoginCompleteListeners();
    }
}
