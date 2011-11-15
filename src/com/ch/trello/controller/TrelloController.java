package com.ch.trello.controller;

import android.os.AsyncTask;

import com.ch.trello.model.TrelloModel;
import com.ch.trello.service.TrelloService;
import com.ch.trello.vo.BoardsResultVO;

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
    
    
    // Methods
    public void login(String username, String password) {
        new UserLoginTask().execute(username, password);
    }
    
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
                BoardsResultVO results = mService.getBoardResults();
                
                if (results != null) {
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
}
