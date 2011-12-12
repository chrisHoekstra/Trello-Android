package com.chrishoekstra.trello.vo;

import java.util.ArrayList;

public class NotificationsResultVO {
    public long now;
    public boolean isMore;
    public String idMember;
    
    public ArrayList<BoardVO> boards;
    public ArrayList<OrganizationVO> organizations;
    public ArrayList<NotificationVO> notifications;
    public ArrayList<MemberVO> members;
    public ArrayList<ActionVO> actions;
    public ArrayList<CardVO> cards;
}
