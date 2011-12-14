package com.chrishoekstra.trello.vo;

import java.util.ArrayList;

public class CardVO {
    public static final String GREEN  = "green";
    public static final String YELLOW = "yellow";
    public static final String ORANGE = "orange";
    public static final String RED    = "red";
    public static final String PURPLE = "purple";
    public static final String BLUE   = "blue";
    
    public String id;
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
