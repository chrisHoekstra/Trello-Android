package com.chrishoekstra.trello.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chrishoekstra.trello.BundleKeys;
import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.TrelloApplication;
import com.chrishoekstra.trello.adapter.BoardListAdapter;
import com.chrishoekstra.trello.listener.BoardListReceivedListener;
import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.service.TrelloBinder;
import com.chrishoekstra.trello.service.TrelloService;
import com.chrishoekstra.trello.vo.BoardListVO;

public class BoardActivity extends Activity {
    
    // View items
    private ListView mBoardListsList;
    private TextView mBoardText;
    
    // Models
    private TrelloModel mModel;
    
    // Activity variables
    private BoardListAdapter mBoardListAdapter;
    private String mBoardId;

    private State mState;
    private boolean mIsConfigurationChanging;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        // Instantiate state
        mState = (State) getLastNonConfigurationInstance();
        if (mState == null) {
            mState = new State();
            getApplicationContext().bindService(new Intent(this, TrelloService.class), mState, BIND_AUTO_CREATE);
        }
        mState.attach(this);
        
        // Instantiate view items
        mBoardListsList = (ListView) findViewById(R.id.board_lists_list);
        mBoardText      = (TextView) findViewById(R.id.board);
        
        // Create listeners
        mBoardListsList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Intent intent = new Intent(getParent(), BoardListActivity.class);
                intent.putExtra(BundleKeys.BOARD_ID, mBoardId);
                intent.putExtra(BundleKeys.BOARD_LIST_ID, mBoardListAdapter.getItem(position).id);
                ((TabActivityGroup) getParent()).startChildActivity("BoardListActivity", intent);
            }
        });
        
        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());
        
        // Instantiate activity variables
        mModel = ((TrelloApplication)getApplication()).getModel();
        
        populateView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!mIsConfigurationChanging) {
            getApplicationContext().unbindService(mState);
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        mIsConfigurationChanging = true;

        return mState;
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
        mBoardText.setText(mModel.getBoard(mBoardId).name);
        
        ArrayList<BoardListVO> boardLists = mModel.getBoardLists(mBoardId);
        if (boardLists != null) {
            onBoardListReceived(boardLists);
        }
    }
    
    private void onBoardListReceived(ArrayList<BoardListVO> boardLists) {
        mBoardListAdapter = new BoardListAdapter(BoardActivity.this, R.id.name, boardLists);
        mBoardListsList.setAdapter(mBoardListAdapter);
    }
    
    private void displayError(Exception e) {
        // TODO - add error handling here
    }

    private void onBinderAvailable() {
        mState.binder.getListsByBoard(mState, mBoardId);
    }
    
    private static class State implements ServiceConnection, BoardListReceivedListener {
        private BoardActivity activity;
        private TrelloBinder binder;
        private TrelloModel model;
        
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            this.binder = (TrelloBinder)binder;

            activity.onBinderAvailable();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            this.binder = null;
        }

        public void attach(BoardActivity activity) {
            this.activity = activity;

            model = ((TrelloApplication)activity.getApplication()).getModel();
        }

        
        // Listeners

        public void onBoardListReceived(String boardId, ArrayList<BoardListVO> boardLists) {
            model.setBoard(boardId, boardLists);
            
            if (boardId.equals(activity.mBoardId)) {
                activity.onBoardListReceived(boardLists);
            }
        }

        public void handleError(Exception e) {
            activity.displayError(e);
        }
    }
}
