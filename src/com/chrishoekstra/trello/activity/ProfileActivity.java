package com.chrishoekstra.trello.activity;

import android.app.Activity;
import android.os.Bundle;

import com.chrishoekstra.trello.R;

public class ProfileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
    }
}
