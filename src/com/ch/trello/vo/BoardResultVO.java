package com.ch.trello.vo;

import java.util.ArrayList;

public class BoardResultVO {
    public long now;
    
    public ArrayList<BoardVO> boards;
    public ArrayList<OrganizationVO> organizations;
    public ArrayList<NotificationVO> notifications;
    public ArrayList<MemberVO> members;
    public ArrayList<ActionVO> actions;
    public ArrayList<CardVO> cards;
}
