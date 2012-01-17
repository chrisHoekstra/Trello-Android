package com.chrishoekstra.trello;

public class TrelloMessage {
    /**
     * The login has completed.
     * 
     * @param obj Boolean - Login completed successfully
     * @param arg1 unused
     * @param arg2 unused
     */
    public static final int LOGIN_COMPLETE = 0;
    
    public static final int BOARD_RECEIVED = 1;
    public static final int NOTIFICATIONS_RECEIVED = 2;
    public static final int CARD_ADDED = 3;
}
