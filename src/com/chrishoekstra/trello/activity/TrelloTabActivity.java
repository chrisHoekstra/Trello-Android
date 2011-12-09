package com.chrishoekstra.trello.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.chrishoekstra.trello.R;

public class TrelloTabActivity extends TabActivity {

    public static final String BOARD_TAB        = "board";
    public static final String NOTIFICATION_TAB = "notification";
    public static final String PROFILE_TAB      = "profile";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        TabHost tabHost = getTabHost();
        
        tabHost.addTab(tabHost.newTabSpec(BOARD_TAB)       .setIndicator("Boards",        null).setContent(new Intent().setClass(this, BoardActivityGroup.class)));
        tabHost.addTab(tabHost.newTabSpec(NOTIFICATION_TAB).setIndicator("Notifications", null).setContent(new Intent().setClass(this, NotificationsActivity.class)));
        tabHost.addTab(tabHost.newTabSpec(PROFILE_TAB)     .setIndicator("Profile",       null).setContent(new Intent().setClass(this, ProfileActivity.class)));
    }
}
