package com.chrishoekstra.trello.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chrishoekstra.trello.BundleKeys;
import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.TrelloApplication;
import com.chrishoekstra.trello.adapter.CardAdapter;
import com.chrishoekstra.trello.listener.CardAddedListener;
import com.chrishoekstra.trello.listener.CardsReceivedListener;
import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.service.TrelloBinder;
import com.chrishoekstra.trello.service.TrelloService;
import com.chrishoekstra.trello.vo.BoardListVO;
import com.chrishoekstra.trello.vo.CardVO;

public class BoardListActivity extends Activity {
    
    // Intent results static definitions

    // Dialog static definitions

    // Class static definitions
    
    // View items
    private ListView mCardList;
    private TextView mBoardListText;
    private EditText mAddCardEdit;
    private Button mAddCardButton;
    private Button mAddButton;
    private Button mCancelButton;
    private RelativeLayout mAddCardLayout;
    
    // Models
    private TrelloModel mModel;
    
    // Activity variables
    private CardAdapter mCardAdapter;
    private String mBoardId;
    private String mBoardListId;
    
    private State mState;
    private boolean mIsConfigurationChanging;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_list);

        // Instantiate state
        mState = (State) getLastNonConfigurationInstance();
        if (mState == null) {
            mState = new State();
            getApplicationContext().bindService(new Intent(this, TrelloService.class), mState, BIND_AUTO_CREATE);
        }
        mState.attach(this);
        
        // Instantiate view items
        mCardList      = (ListView) findViewById(R.id.card_list);
        mBoardListText = (TextView) findViewById(R.id.board_list);
        mAddCardEdit   = (EditText) findViewById(R.id.add_card_edit);
        mAddCardButton = (Button)   findViewById(R.id.add_card);
        mAddButton     = (Button)   findViewById(R.id.add);
        mCancelButton  = (Button)   findViewById(R.id.cancel);
        mAddCardLayout = (RelativeLayout) findViewById(R.id.add_card_layout);
        
        // Create listeners
        mCardList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Intent intent = new Intent(getParent(), CardActivity.class);
                intent.putExtra(BundleKeys.CARD_ID, mCardAdapter.getItem(position).id);
                intent.putExtra(BundleKeys.BOARD_LIST_ID, mBoardListId);
                ((TabActivityGroup) getParent()).startChildActivity("CardActivity", intent);
            }
        });
        
        mAddCardButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginAddCard();
            }
        });
        
        mCancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                endAddCard();
            }
        });
        

        mAddButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState.binder.addCard(mState, mBoardListId, mAddCardEdit.getText().toString());
            }
        });
        
        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());
        
        // Instantiate activity variables
        mModel = ((TrelloApplication)getApplication()).getModel();
        
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
        inflater.inflate(R.menu.board_list_menu, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_card :
                beginAddCard();
                break;
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
        outState.putString(BundleKeys.BOARD_LIST_ID, mBoardId);
    }
    
    private void getBundleExtras(final Bundle bundle) {
        if (bundle != null) {
            mBoardId = bundle.getString(BundleKeys.BOARD_ID);
            mBoardListId = bundle.getString(BundleKeys.BOARD_LIST_ID);
        }
    }
    
    private void populateView() {
        BoardListVO list = mModel.getBoardList(mBoardId, mBoardListId);
        mBoardListText.setText(list.name);
    }
    
    private void beginAddCard() {
        mAddCardButton.setVisibility(View.GONE);
        mAddCardLayout.setVisibility(View.VISIBLE);
    }
    
    private void endAddCard() {
        mAddCardLayout.setVisibility(View.GONE);
        mAddCardButton.setVisibility(View.VISIBLE);
    }
    
    private void onBinderAvailable() {
        mState.binder.getCardsByList(mState, mBoardListId);
    }
    
    private void onCardsReceived(ArrayList<CardVO> cards) {
        mCardAdapter = new CardAdapter(BoardListActivity.this, R.id.name, cards, mModel);
        mCardList.setAdapter(mCardAdapter);
    }

    public void onCardAdded() {
        Toast.makeText(BoardListActivity.this, "Card added!", Toast.LENGTH_SHORT).show();
        endAddCard();
        
        mState.binder.getCardsByList(mState, mBoardListId);
    }
    
    public void handleError(Exception exception) {
        
    }
    
    private static class State implements ServiceConnection, CardsReceivedListener, CardAddedListener {
        private BoardListActivity activity;
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

        public void attach(BoardListActivity activity) {
            this.activity = activity;

            model = ((TrelloApplication)activity.getApplication()).getModel();
        }
        
        
        // Listeners

        public void onCardAdded() {
            activity.onCardAdded();
        }

        public void onCardsReceived(String boardListId, ArrayList<CardVO> cards) {
            model.setBoardList(boardListId, cards);
            
            if (boardListId.equals(activity.mBoardListId)) {
                activity.onCardsReceived(cards);
            }
        }

        public void handleError(Exception e) {
            activity.handleError(e);
        }
    }
}
