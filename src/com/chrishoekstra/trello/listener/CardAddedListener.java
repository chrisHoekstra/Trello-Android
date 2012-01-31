package com.chrishoekstra.trello.listener;

public interface CardAddedListener {
    void onCardAdded();
    void handleError(Exception e);
}
