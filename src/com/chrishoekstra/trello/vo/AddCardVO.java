package com.chrishoekstra.trello.vo;

import java.util.ArrayList;

public class AddCardVO extends ApiMethodVO {
    public DataVO data = new DataVO();
    
    public class DataVO {
        public AttrVO attrs = new AttrVO();
        public ArrayList<String> idParents = new ArrayList<String>();
    }
    
    public class AttrVO {
        public String name;
        public String idBoard;
        public String idList;
        public int pos;
        public boolean closed;
    }
}
