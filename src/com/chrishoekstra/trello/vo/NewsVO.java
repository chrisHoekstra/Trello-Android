package com.chrishoekstra.trello.vo;

public class NewsVO {
    public static final String CHANGE_CARD  = "changeCard";
    public static final String COMMENT_CARD = "commentCard";
    public static final String ADDED_TO_CARD = "addedToCard";
    public static final String INVITED_TO_ORGANIZTION = "invitedToOrganization";
    
    public String _id;
    public String type;
    public String date;
    public String idMemberCreator;
    public DataVO data;
}
