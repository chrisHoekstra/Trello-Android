package com.chrishoekstra.trello.controller;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.service.TrelloService;
import com.chrishoekstra.trello.vo.BoardListVO;
import com.chrishoekstra.trello.vo.BoardVO;
import com.chrishoekstra.trello.vo.CardVO;

public class TrelloController {

    // Singleton stuff
    private static TrelloController controller;
    public  static TrelloController getInstance() {
        if (controller == null) {
            controller = new TrelloController();
            controller.mModel = TrelloModel.getInstance();
            controller.mService = new TrelloService();
        }
        return controller;
    }

    // Variables
    private TrelloModel mModel;
    private TrelloService mService;
    
    
    // Public Methods
    public void setToken(String token) {
        mService.setToken(token);
    }

    public String getRequestLink() {
        return mService.getRequestLink();
    }
    
    

    
    public void getUserData() {
        new UserDataFetchTask().execute();
    }

    public void getListsByBoard(String mBoardId) {
        new ListsByBoardFetchTask().execute(mBoardId);
    }

    public void getCardsByList(String mBoardListId) {
        new CardsByBoardListFetchTask().execute(mBoardListId);
    }
    
    public void addCard(String boardListId, String cardName) {
        new AddCardTask(boardListId).execute(cardName);
    }

    
    // AsyncTasks
    private class UserDataFetchTask extends AsyncTask<Void, Void, Boolean> {
        
        @Override
        protected Boolean doInBackground(Void... parameters) {
            Boolean result = false;
            
            mModel.setAllBoards(mService.getAllBoards());
            mModel.setUser(mService.getUser());
            mModel.setNotifications(mService.getNotifications());
            
            return result;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            mModel.userDataReceived(result);
        }
    }
    
    private class ListsByBoardFetchTask extends AsyncTask<String, Void, ArrayList<BoardListVO>> {
        
        String boardId;
        
        @Override
        protected ArrayList<BoardListVO> doInBackground(String... parameters) {
            ArrayList<BoardListVO> result = null;

            boardId = parameters[0];
            
            result = mService.getListsByBoard(boardId);
            
            return result;
        }
        
        @Override
        protected void onPostExecute(ArrayList<BoardListVO> result) {
            if (result != null) {
                mModel.boardListsReceived(boardId, result);
            }
        }
    }

    private class CardsByBoardListFetchTask extends AsyncTask<String, Void, ArrayList<CardVO>> {
        
        String boardListId;
        
        @Override
        protected ArrayList<CardVO> doInBackground(String... parameters) {
            ArrayList<CardVO> result = null;

            boardListId = parameters[0];
            
            result = mService.getCardsByBoardList(boardListId);
            
            return result;
        }
        
        @Override
        protected void onPostExecute(ArrayList<CardVO> result) {
            if (result != null) {
                mModel.cardsReceived(boardListId, result);
            }
        }
    }

    private class AddCardTask extends AsyncTask<String, Void, Boolean> {
        
        private String boardId;
        
        public AddCardTask(String boardId) {
            this.boardId = boardId;
        }
        
        @Override
        protected Boolean doInBackground(String... parameters) {
            Boolean result = false;
            
            result = mService.addCard(boardId, parameters[0]);
            
            return result;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mModel.cardAdded(result);
            }
        }
    }
}
