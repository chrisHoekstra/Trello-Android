package com.ch.trello.vo;

import java.util.ArrayList;

public class NotificationVO {
    public String _id;
    public String type;
    public String date;
    public String idMemberCreator;
    public String isUnread; // TODO - boolean?
    public DataVO data;
    
    public ArrayList<String> idMembers;
    public ArrayList<String> idMembersUnread;
}
