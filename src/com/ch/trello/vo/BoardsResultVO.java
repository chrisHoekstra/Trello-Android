package com.ch.trello.vo;

import java.util.ArrayList;

public class BoardsResultVO {
    public long now;
    public String idMember;
    
    public ArrayList<BoardVO> boards;
    public ArrayList<OrganizationVO> organizations;
    public ArrayList<NotificationVO> notifications;
    public ArrayList<MemberVO> members;
}
