package com.ch.trello.vo;

import java.util.ArrayList;

public class CardVO {
    public String _id;
    public String name;
    public String desc;
    public String dateLastActivity;
    public int pos;
    public boolean closed;
    public String idBoard;
    public String idList;
    public BadgeVO badges;
    
    public ArrayList<String> idChecklists;
    public ArrayList<CheckItemStateVO> checkItemStates;
    public ArrayList<String> attachments;
    public ArrayList<String> idMembers;
    public ArrayList<String> idMembersVoted;
    public ArrayList<String> labels;
}
