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

import com.chrishoekstra.trello.BundleKeys;
import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.adapter.BoardListAdapter;
import com.chrishoekstra.trello.controller.TrelloController;
import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.vo.BoardListVO;
import com.chrishoekstra.trello.vo.BoardResultVO;
import com.chrishoekstra.trello.vo.BoardVO;

public class BoardActivity extends BaseActivity {
    
    // Intent results static definitions

    // Dialog static definitions

    // Class static definitions
    
    // View items
    private ListView mBoardListsList;
    private TextView mBoardText;
    
    // Models
    private TrelloModel mModel;
    
    // Controllers
    private TrelloController mController;
    
    // Listeners
    private TrelloModel.OnBoardReceivedListener mOnBoardReceivedListener;
    
    // Activity variables
    private BoardListAdapter mBoardListAdapter;
    private String mBoardId;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);
        
        // Instantiate view items
        mBoardListsList = (ListView) findViewById(R.id.board_lists_list);
        mBoardText      = (TextView) findViewById(R.id.board);
        
        // Instantiate models
        mModel = TrelloModel.getInstance();
        
        // Instantiate controllers
        mController = TrelloController.getInstance();
        
        // Create listeners
        mOnBoardReceivedListener = new TrelloModel.OnBoardReceivedListener() {
            @Override
            public void onBoardReceveidEvent(TrelloModel model, BoardResultVO result) {
                mModel.setCurrentBoard(result);
                
                BoardVO currentBoard = null;
                
                for (BoardVO board : result.boards) {
                    if (board._id.equals(mBoardId)) {
                        currentBoard = board;
                        break;
                    }
                }
                
                if (currentBoard != null) {
                    mBoardListAdapter = new BoardListAdapter(BoardActivity.this, R.id.name, currentBoard.lists);
                    mBoardListsList.setAdapter(mBoardListAdapter);
                
                    mBoardText.setText(currentBoard.name);
                }
            }
        };
        
        mBoardListsList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Intent intent = new Intent(getParent(), BoardListActivity.class);
                intent.putExtra(BundleKeys.BOARD_ID, mBoardId);
                intent.putExtra(BundleKeys.BOARD_LIST_ID, mBoardListAdapter.getItem(position)._id);
                ((TabActivityGroup) getParent()).startChildActivity("BoardListActivity", intent);
            }
        });
        
        // Add listeners
        mModel.addListener(mOnBoardReceivedListener);
        
        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());
        
        // Instantiate activity variables
        
        mController.fetchBoard(mBoardId);
        
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

        mModel.removeListener(mOnBoardReceivedListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putString(BundleKeys.BOARD_ID, mBoardId);
    }
    
    private void getBundleExtras(final Bundle bundle) {
        if (bundle != null) {
            mBoardId = bundle.getString(BundleKeys.BOARD_ID);
        }
    }
    
    private void populateView() {

    }
}
