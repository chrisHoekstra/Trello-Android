package com.chrishoekstra.trello.activity;

import android.content.Intent;
import android.os.Bundle;

public class BoardActivityGroup extends TabActivityGroup {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("DashboardActivity", new Intent(this, DashboardActivity.class));
    }
}
