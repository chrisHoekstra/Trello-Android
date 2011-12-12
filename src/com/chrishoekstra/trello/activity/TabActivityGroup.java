package com.chrishoekstra.trello.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class TabActivityGroup extends ActivityGroup {

    private ArrayList<String> mIdList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (mIdList == null) mIdList = new ArrayList<String>();
    }

    /**
     * This is called when a child activity of this one calls its finish method.
     * This implementation calls {@link LocalActivityManager#destroyActivity} on the child activity
     * and starts the previous activity.
     * If the last child activity just called finish(),this activity (the parent),
     * calls finish to finish the entire group.
     */
    @Override
    public void finishFromChild(Activity child) {
        LocalActivityManager manager = getLocalActivityManager();
        
        int index = mIdList.size() - 1;
        if (index < 1) {
            finish();
            return;
        }

        manager.destroyActivity(mIdList.get(index), true);
        mIdList.remove(index);
        index--;
        
        String lastId = mIdList.get(index);
        Intent lastIntent = manager.getActivity(lastId).getIntent();
        Window newWindow  = manager.startActivity(lastId, lastIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        
        setContentView(newWindow.getDecorView());
    }

    /**
     * Starts an Activity as a child Activity to this.
     * @param Id Unique identifier of the activity to be started.
     * @param intent The Intent describing the activity to be started.
     */
    public void startChildActivity(String Id, Intent intent) {
        Window window = getLocalActivityManager().startActivity(Id, intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        
        if (window != null) {
            mIdList.add(Id);
            setContentView(window.getDecorView());
        }
    }    
    
    @Override
    public void onBackPressed() {
        int length = mIdList.size();
        if (length > 0) {
            getLocalActivityManager().getActivity(mIdList.get(length - 1)).finish();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return getLocalActivityManager().getCurrentActivity().onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return getLocalActivityManager().getCurrentActivity().onOptionsItemSelected(item);
    }
}
