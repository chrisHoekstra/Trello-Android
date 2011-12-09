package com.ch.trello.vo;

import java.util.ArrayList;

public class BoardVO {
    public String name;
    public String _id;
    public boolean closed;
    public int nActionsSinceLastView;
    
    public BoardPermissionsPrefsVO prefs;
    public LabelNamesVO labelNames;
    
    public ArrayList<BoardListVO> lists;
    public ArrayList<CheckListVO> checklists;
    public ArrayList<String> invitations;
    public ArrayList<MembershipVO> memberships;
    public ArrayList<String> idMembersWatching;
    public ArrayList<String> idMembersRemoved;
}
