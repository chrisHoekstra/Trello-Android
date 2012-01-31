package com.chrishoekstra.trello.listener;

import java.util.ArrayList;

import com.chrishoekstra.trello.vo.CardVO;

public interface CardsReceivedListener {
    void onCardsReceived(String boardListId, ArrayList<CardVO> cards);
    void handleError(Exception e);
}
