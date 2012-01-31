package com.chrishoekstra.trello;

import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.service.TrelloApi;

import android.app.Application;

public class TrelloApplication extends Application {

    private TrelloModel mModel;
    private TrelloApi mApi;
    
    @Override
    public void onCreate() {
        mModel = new TrelloModel();
        mApi = new TrelloApi();
    }
    
    public TrelloModel getModel() {
        return mModel;
    }
    
    public TrelloApi getApi() {
        return mApi;
    }
}
