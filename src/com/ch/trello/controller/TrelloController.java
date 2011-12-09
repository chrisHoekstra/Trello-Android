package com.ch.trello.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.ch.trello.database.TrelloDb;
import com.ch.trello.model.TrelloModel;
import com.ch.trello.service.TrelloService;
import com.ch.trello.vo.AddCardVO;
import com.ch.trello.vo.AllBoardsResultVO;
import com.ch.trello.vo.ApiMethodVO;
import com.ch.trello.vo.BoardResultVO;
import com.ch.trello.vo.BoardVO;
import com.ch.trello.vo.CardVO;

public class TrelloController {

    // Singleton stuff
    private static TrelloController controller;
    public  static TrelloController getInstance(Context context) {
        if (controller == null) {
            controller = new TrelloController();
            controller.mDb = TrelloDb.getInstance(context);
            controller.mModel = TrelloModel.getInstance();
            controller.mService = new TrelloService();
            controller.addListeners();
        }
        return controller;
    }

    // Variables
    private TrelloDb mDb;
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
    
    
    // Private methods
    private void addListeners() {
        mModel.addListener(new TrelloModel.OnLoginCompleteListener() {
            @Override
            public void onLoginCompleteEvent(TrelloModel model) {
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

                    mDb.createOrganizations(results.organizations);
                    mDb.createBoards(results.boards);                    
                    mDb.createMembers(results.members);
                    
                    // TODO
                    //mDb.createNotifications(results.notifications);
                    
                    result = true;
                }
            }
            
            return result;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mModel.loginComplete();
            }
        }
    }
    
    private class BoardFetchTask extends AsyncTask<String, Void, BoardResultVO> {
        
        @Override
        protected BoardResultVO doInBackground(String... parameters) {
            BoardResultVO result = null;
            
            result = mService.getBoard(parameters[0]);
            
            for (BoardVO board : result.boards) {
                if (board._id.equals(parameters[0])) {
                    mDb.updateBoard(board._id, board);
                    break;
                }
            }
            
            mDb.createCards(result.cards);
            
            return result;
        }
        
        @Override
        protected void onPostExecute(BoardResultVO result) {
            if (result != null) {
                mModel.boardReceived(result);
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
