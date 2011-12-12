package com.chrishoekstra.trello.vo;

import java.util.ArrayList;

public class MemberVO {
    public String _id;
    public String email;
    public String username;
    public String fullName;
    public String initials;
    public String status;
    public String gravatar;
    public PrefsVO prefs;
    public boolean isOpenIdAccount;
    
    public ArrayList<String> idBoards;
    public ArrayList<String> idBoardsPinned;
    public ArrayList<String> idBoardsInvited;
    public ArrayList<String> idOrganizations;
    public ArrayList<String> idOrganizationsInvited;
}
