package com.chrishoekstra.trello;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.chrishoekstra.trello.controller.TrelloController;

public class TrelloApplication extends Application {

    private TrelloController mController;
    
    @Override
    public void onCreate() {
        mController = new TrelloController(this);
    }
    
    public TrelloController getController() {
        return mController;
    }
    
    
    
    
    /*
     * Application messaging
     */
    
    private Handler mHandler;
    
    public void registerActivityHandler(Handler handler) {
        mHandler = handler;
    }
    
    public void sendMessage(int what) {
        Message message = mHandler.obtainMessage(what);
        mHandler.sendMessage(message);
    }

    public void sendMessage(int what, Boolean bool) {
        Message message = mHandler.obtainMessage(what, bool);
        mHandler.sendMessage(message);
    }
}
