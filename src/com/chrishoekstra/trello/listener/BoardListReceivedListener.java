package com.chrishoekstra.trello.listener;

import java.util.ArrayList;

import com.chrishoekstra.trello.vo.BoardListVO;

public interface BoardListReceivedListener {
    void onBoardListReceived(String boardId, ArrayList<BoardListVO> boardLists);
    void handleError(Exception e);
}

