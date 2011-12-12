package com.chrishoekstra.trello.activity;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.adapter.NotificationsAdapter;
import com.chrishoekstra.trello.controller.TrelloController;
import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.vo.NotificationsResultVO;

public class NotificationsActivity extends ListActivity {

    // Intent results static definitions

    // Dialog static definitions

    // Class static definitions
    protected static final int NUMBER_OF_STATIC_NOTIFICATIONS = 5;

    // View items
    private Button mMoreButton;
    
    // Models
    private TrelloModel mModel;
    
    // Controllers
    private TrelloController mController;
    
    // Listeners
    private TrelloModel.OnNotificationsReceivedListener mOnNotificationsReceivedListener;
    
    // Activity variables
    private NotificationsAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        
        // Instantiate view items
        mMoreButton = (Button) getLayoutInflater().inflate(R.layout.more_button, null);
        
        // Instantiate models
        mModel = TrelloModel.getInstance();
        
        // Instantiate controllers
        mController = TrelloController.getInstance();
        
        // Create listeners
        mOnNotificationsReceivedListener = new TrelloModel.OnNotificationsReceivedListener() {
            @Override
            public void onNotificationsReceivedEvent(TrelloModel model, NotificationsResultVO result) {
                mAdapter.addNotifications(result.notifications.subList(NUMBER_OF_STATIC_NOTIFICATIONS, result.notifications.size()));
                
                if (result.isMore) {
                    mMoreButton.setText(R.string.load_more_notifications);
                } else {
                    mMoreButton.setVisibility(View.GONE);
                }
            }
        };
        
        mMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getNotifications(mAdapter.getCount());
            }
        });
        
        // Add listeners
        mModel.addListener(mOnNotificationsReceivedListener);
        
        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());
        
        // Instantiate activity variables
        
        populateView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        switch (requestCode) {
            //case ACTIVITY_RESULT_ID :
            //    break;
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_id, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            //case R.id.view_item_id : 
            //    break;
            default:
                break;
        }
        
        // Return true if you want the click event to stop here, or false
        // if you want the click even to continue propagating possibly
        // triggering an onClick event
        return false;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo info) {
        super.onCreateContextMenu(menu, view, info);

        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_id, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.view_item_id : 
            //    break;
            default:
                break;
        }
        
        // Return true if you want the click event to stop here, or false
        // if you want the click even to continue propagating possibly
        // triggering an onClick event
        return false;
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            //case DIALOG_STATIC_DEFINITION:
            //    return new Dialog();
            //    break;
        }
        
        return super.onCreateDialog(id);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        
        if (isFinishing()) {
            // Clean up of any information in model
        }
        
        //new PasscodeCheckTask(TemplateActivity.this.getApplicationContext(), TemplateActivity.this).execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        mModel.removeListener(mOnNotificationsReceivedListener);
        
        if (mMoreButton != null) {
            mMoreButton.setOnClickListener(null);
            mMoreButton = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    private void getBundleExtras(final Bundle bundle) {
        if (bundle != null) {
        }
    }
    
    private void populateView() {
        mMoreButton.setText(R.string.see_all_notifications);
        getListView().addFooterView(mMoreButton);
        
        mAdapter = new NotificationsAdapter(this, R.id.name, mModel.getAllBoardsResult().notifications);
        setListAdapter(mAdapter);
    }
}
