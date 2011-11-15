package com.ch.trello.vo;

import java.util.ArrayList;

public class BoardVO {
    public String name;
    public String _id;
    public PrefsVO prefs;
    public String closed; // TODO - boolean?
    public int nActionsSinceLastView;
    
    public ArrayList<String> invitations;
    public ArrayList<MembershipVO> memberships;
    public ArrayList<String> idMembersWatching;
}
