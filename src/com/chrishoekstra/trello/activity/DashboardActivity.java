package com.chrishoekstra.trello.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.BundleKeys;
import com.chrishoekstra.trello.adapter.BoardAdapter;
import com.chrishoekstra.trello.controller.TrelloController;
import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.vo.AllBoardsResultVO;
import com.chrishoekstra.trello.vo.BoardVO;
import com.chrishoekstra.trello.vo.MemberVO;

public class DashboardActivity extends Activity {
    
    // Intent results static definitions

    // Dialog static definitions

    // Class static definitions
    
    // View items
    private ListView mBoardsList;
    private TextView mFullNameText;
    private TextView mUsernameText;
    
    // Models
    private TrelloModel mModel;
    
    // Controllers
    private TrelloController mController;
    
    // Listeners
    
    // Activity variables
    private BoardAdapter mBoardAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        
        // Instantiate view items
        mBoardsList   = (ListView) findViewById(R.id.board_list);
        mFullNameText = (TextView) findViewById(R.id.full_name);
        mUsernameText = (TextView) findViewById(R.id.username);
        
        // Instantiate models
        mModel = TrelloModel.getInstance();
        
        // Instantiate controllers
        mController = TrelloController.getInstance();
        
        // Create listeners
        mBoardsList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                BoardVO board = mBoardAdapter.getItem(position);
                Intent intent = new Intent(DashboardActivity.this, BoardActivity.class);
                intent.putExtra(BundleKeys.BOARD_ID, board._id);
                startActivity(intent);
            }
        });
        
        // Add listeners
        
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
        super.onCreateOptionsMenu(menu);
        
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
        
        // Remove listeners
        // Release remaining resources
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        // Save any user entered / passed in information
        //outState.putString(BundleKeys.BUNDLE_VARIABLE, mBundleVariable);
    }
    
    private void getBundleExtras(final Bundle bundle) {
        if (bundle != null) {
            //mBundleVariable = bundle.getString(BundleKeys.BUNDLE_VARIABLE);
        }
    }
    
    private void populateView() {
        AllBoardsResultVO allBoards = mModel.getAllBoardsResult();
        
        mBoardAdapter = new BoardAdapter(this, R.id.name, allBoards.boards);
        mBoardsList.setAdapter(mBoardAdapter);
        
        MemberVO user = null;
        for (MemberVO member : allBoards.members) {
            if (member._id.equals(allBoards.idMember)) {
                user = member;
                break;
            }
        }
        
        if (user != null) {
            mFullNameText.setText(user.fullName);
            mUsernameText.setText('(' + user.username + ')');
        }   
    }
}
