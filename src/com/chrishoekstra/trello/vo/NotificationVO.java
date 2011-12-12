package com.chrishoekstra.trello.vo;

import java.util.ArrayList;

public class NotificationVO extends NewsVO {
    public boolean isUnread;
    
    public ArrayList<String> idMembers;
    public ArrayList<String> idMembersUnread;
}
