package com.chrishoekstra.trello.service;

import com.chrishoekstra.trello.TrelloApplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TrelloService extends Service {

    private TrelloBinder binder;
     
    @Override
    public void onCreate() {
        super.onCreate();
        
        binder = new TrelloBinder(((TrelloApplication)getApplication()).getApi());
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
