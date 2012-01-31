package com.chrishoekstra.trello.service;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Binder;

import com.chrishoekstra.trello.listener.BoardListReceivedListener;
import com.chrishoekstra.trello.listener.CardAddedListener;
import com.chrishoekstra.trello.listener.CardsReceivedListener;
import com.chrishoekstra.trello.listener.UserDataReceivedListener;
import com.chrishoekstra.trello.vo.BoardListVO;
import com.chrishoekstra.trello.vo.BoardVO;
import com.chrishoekstra.trello.vo.CardVO;
import com.chrishoekstra.trello.vo.MemberVO;
import com.chrishoekstra.trello.vo.NotificationVO;

public class TrelloBinder extends Binder {
    
    private TrelloApi mApi;
    
    public TrelloBinder(TrelloApi trelloApi) {
        mApi = trelloApi;
    }
    
    public void setToken(String token) {
        mApi.setToken(token);
    }

    public String getRequestLink() {
        return mApi.getRequestLink();
    }

    
    
    public void fetchUserData(UserDataReceivedListener listener) {
        new UserDataFetchTask(listener).execute();
    }

    public void getListsByBoard(BoardListReceivedListener listener, String mBoardId) {
        new ListsByBoardFetchTask(listener).execute(mBoardId);
    }

    public void getCardsByList(CardsReceivedListener listener, String mBoardListId) {
        new CardsByBoardListFetchTask(listener).execute(mBoardListId);
    }
    
    public void addCard(CardAddedListener listener, String boardListId, String cardName) {
        new AddCardTask(listener, boardListId).execute(cardName);
    }

    
    // AsyncTasks
    private class UserDataFetchTask extends AsyncTask<Void, Void, Void> {
        
        private UserDataReceivedListener listener;
        private Exception exception;
                
        private ArrayList<BoardVO> boards;
        private MemberVO user;
        private ArrayList<NotificationVO> notifications;
        
        public UserDataFetchTask(UserDataReceivedListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... parameters) {
            try {
                boards        = mApi.getAllBoards();
                user          = mApi.getUser();
                notifications = mApi.getNotifications();
            } catch (Exception e) {
                exception = e;
            }
            
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            if (exception == null) {
                listener.onUserDataReceived(boards, user, notifications);
            } else {
                listener.handleError(exception);
            }
        }
    }
    
    private class ListsByBoardFetchTask extends AsyncTask<String, Void, ArrayList<BoardListVO>> {

        private BoardListReceivedListener listener;
        private Exception exception;
        
        private String boardId;
        
        public ListsByBoardFetchTask(BoardListReceivedListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<BoardListVO> doInBackground(String... parameters) {
            ArrayList<BoardListVO> result = null;

            boardId = parameters[0];
            
            try {
                result = mApi.getListsByBoard(boardId);
            } catch (Exception e) {
                exception = e;
            }
            
            return result;
        }
        
        @Override
        protected void onPostExecute(ArrayList<BoardListVO> result) {
            if (exception == null) {
                if (result != null) {
                    listener.onBoardListReceived(boardId, result);
                } else {
                    listener.handleError(new Exception());
                }                
            } else {
                listener.handleError(exception);
            }
        }
    }

    private class CardsByBoardListFetchTask extends AsyncTask<String, Void, ArrayList<CardVO>> {

        private CardsReceivedListener listener;
        private Exception exception;
        
        String boardListId;
        
        public CardsByBoardListFetchTask(CardsReceivedListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<CardVO> doInBackground(String... parameters) {
            ArrayList<CardVO> result = null;

            boardListId = parameters[0];
            
            try {
                result = mApi.getCardsByBoardList(boardListId);
            } catch (Exception e) {
                exception = e;
            }
            
            return result;
        }
        
        @Override
        protected void onPostExecute(ArrayList<CardVO> result) {
            if (exception == null) {
                if (result != null) {
                    listener.onCardsReceived(boardListId, result);
                } else {
                    listener.handleError(new Exception());
                }                
            } else {
                listener.handleError(exception);
            }
        }
    }

    private class AddCardTask extends AsyncTask<String, Void, Boolean> {
        
        private CardAddedListener listener;
        private Exception exception;
        
        private String boardId;        
        
        public AddCardTask(CardAddedListener listener, String boardId) {
            this.listener = listener;
            this.boardId = boardId;
        }
        
        @Override
        protected Boolean doInBackground(String... parameters) {
            Boolean result = false;
            
            try {                
                result = mApi.addCard(boardId, parameters[0]);
            } catch (Exception e) {
                exception = e;
            }
            
            return result;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            if (exception == null) {
                if (result != null) {
                    listener.onCardAdded();
                } else {
                    listener.handleError(new Exception());
                }                
            } else {
                listener.handleError(exception);
            }
        }
    }
}
