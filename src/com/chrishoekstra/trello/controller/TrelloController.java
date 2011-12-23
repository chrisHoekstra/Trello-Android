package com.chrishoekstra.trello.controller;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.service.TrelloService;
import com.chrishoekstra.trello.vo.AddCardVO;
import com.chrishoekstra.trello.vo.AllBoardsResultVO;
import com.chrishoekstra.trello.vo.ApiMethodVO;
import com.chrishoekstra.trello.vo.BoardResultVO;
import com.chrishoekstra.trello.vo.BoardVO;
import com.chrishoekstra.trello.vo.CardVO;
import com.chrishoekstra.trello.vo.MemberVO;
import com.chrishoekstra.trello.vo.NotificationVO;
import com.chrishoekstra.trello.vo.NotificationsResultVO;

public class TrelloController {

    // Singleton stuff
    private static TrelloController controller;
    public  static TrelloController getInstance() {
        if (controller == null) {
            controller = new TrelloController();
            controller.mModel = TrelloModel.getInstance();
            controller.mService = new TrelloService();
            controller.addListeners();
        }
        return controller;
    }

    // Variables
    private TrelloModel mModel;
    private TrelloService mService;
    
    
    // Public Methods
    public void login(String username, String password) {
        new UserLoginTask().execute(username, password);
    }

    public void fetchBoard(String mBoardId) {
        new BoardFetchTask().execute(mBoardId);
    }
    
    public void addCard(String boardId, String boardListId, String cardName, int position) {
        AddCardVO addCard = new AddCardVO();
        
        addCard.method = ApiMethodVO.CREATE;
        addCard.token  = mService.getFilteredToken();
        addCard.data.idParents.add(boardListId);
        addCard.data.idParents.add(boardId);
        addCard.data.attrs.name = cardName;
        addCard.data.attrs.pos = position;
        addCard.data.attrs.idBoard = boardId;
        addCard.data.attrs.idList = boardListId;
        
        new AddCardTask().execute(addCard);
    }

    public void getNotifications(int count) {
        new NotificationsFetchTask().execute(count);
    }
    
    // Private methods
    private void addListeners() {
        mModel.addListener(new TrelloModel.OnLoginCompleteListener() {
            @Override
            public void onLoginCompleteEvent(TrelloModel model, boolean successful) {
                // TODO Auto-generated method stub
                
            }
        });
    }
    
    
    // AsyncTasks
    private class UserLoginTask extends AsyncTask<String, Void, Boolean> {
        
        @Override
        protected Boolean doInBackground(String... parameters) {
            Boolean result = false;
            
            if (mService.login(parameters[0], parameters[1])) {
                AllBoardsResultVO results = mService.getBoardResults();
                
                if (results != null) {
                    mModel.setAllBoardsResult(results);
                    
                    if (results.members != null) {   
                        for (MemberVO member : results.members) {
                            if (member._id.equals(results.idMember)) {
                                mModel.setUser(member);
                                break;
                            }
                        }
                        
                        result = true;
                    }
                }
            }
            
            return result;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            mModel.loginComplete(result);
        }
    }
    
    private class BoardFetchTask extends AsyncTask<String, Void, BoardResultVO> {
        
        @Override
        protected BoardResultVO doInBackground(String... parameters) {
            BoardResultVO result = null;
            
            result = mService.getBoard(parameters[0]);
            
            return result;
        }
        
        @Override
        protected void onPostExecute(BoardResultVO result) {
            if (result != null) {
                mModel.boardReceived(result);
            }
        }
    }
    
    private class NotificationsFetchTask extends AsyncTask<Integer, Void, NotificationsResultVO> {
        
        @Override
        protected NotificationsResultVO doInBackground(Integer... counts) {
            NotificationsResultVO result = null;
            
            result = mService.getNotifications(mModel.getUser().username, counts[0]);
            
            return result;
        }
        
        @Override
        protected void onPostExecute(NotificationsResultVO result) {
            if (result != null) {
                mModel.notificationsReceived(result);
            }
        }
    }
    
    private class AddCardTask extends AsyncTask<AddCardVO, Void, CardVO> {
        
        @Override
        protected CardVO doInBackground(AddCardVO... parameters) {
            CardVO result = null;
            
            result = mService.addCard(parameters[0]);
            
            return result;
        }
        
        @Override
        protected void onPostExecute(CardVO result) {
            if (result != null) {
                mModel.cardAdded(result);
            }
        }
    }
}
