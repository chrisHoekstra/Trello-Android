package com.chrishoekstra.trello.listener;

import java.util.ArrayList;

import com.chrishoekstra.trello.vo.BoardVO;
import com.chrishoekstra.trello.vo.MemberVO;
import com.chrishoekstra.trello.vo.NotificationVO;

public interface UserDataReceivedListener {
    void onUserDataReceived(ArrayList<BoardVO> boards, MemberVO user, ArrayList<NotificationVO> notifications);
    void handleError(Exception e);
}
