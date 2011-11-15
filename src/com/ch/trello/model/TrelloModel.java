package com.ch.trello.model;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TrelloModel {
    
    // Singleton stuff
    private static TrelloModel model;
    public  static TrelloModel getInstance() {
        if (model == null) {
            model = new TrelloModel();
        }
        return model;
    }
    
    
    // Listener Decleration
    public interface OnLoginCompleteListener {
        void onLoginCompleteEvent(TrelloModel model);
    }
    
    
    // Listener Arrays
    private final ConcurrentLinkedQueue<OnLoginCompleteListener> onLoginCompleteListeners = new ConcurrentLinkedQueue<OnLoginCompleteListener>();
    
    
    // Alert Listeners
    private final void alertOnLoginCompleteListeners() {
        for (final OnLoginCompleteListener listener : onLoginCompleteListeners)
            listener.onLoginCompleteEvent(this);
    }
    

    // Add Listener
    public final void addListener(OnLoginCompleteListener listener) {
        synchronized (onLoginCompleteListeners) {
            onLoginCompleteListeners.add(listener);
        }
    }

    
    // Remove Listener
    public final void removeListener(OnLoginCompleteListener listener) {
        synchronized (onLoginCompleteListeners) {
            onLoginCompleteListeners.remove(listener);
        }
    }
    
    
    // Model functions
    public void loginComplete() {
        alertOnLoginCompleteListeners();
    }
}
