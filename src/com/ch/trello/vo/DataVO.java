package com.ch.trello.vo;

public class DataVO {
    public OrganizationVO organization;
    public BoardVO board;
    public BoardListVO list;
    public CardVO card;
    public String idMember;
    public String value;
    public String text;
    
    public BoardListVO listBefore;
    public BoardListVO listAfter;
    public CardVO old;
    
    class OrganizationVO {
        public String id;
        public String name;
    }
    
    class BoardVO {
        public String id;
        public String name;
    }
    
    class BoardListVO {
        public String id;
        public String name;
    }
    
    class CardVO {
        public String id;
        public String name;
        public String desc;
        public int pos;
        public boolean closed;
    }
    
    class CheckItemVO {
        public String id;
        public String name;
        public String state;
        public int pos;
    }
}
