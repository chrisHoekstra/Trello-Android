package com.ch.trello.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.ch.trello.BundleKeys;
import com.ch.trello.R;
import com.ch.trello.adapter.BoardAdapter;
import com.ch.trello.controller.TrelloController;
import com.ch.trello.model.TrelloModel;
import com.ch.trello.vo.AllBoardsResultVO;
import com.ch.trello.vo.BoardVO;
import com.ch.trello.vo.OrganizationVO;

import java.util.ArrayList;

public class OrganizationActivity extends BaseActivity {

    // Intent results static definitions

    // Dialog static definitions

    // Class static definitions

    // View items
    private TextView mOrgaListText;
    private ListView mBoardList;
    // Models
    private TrelloModel mModel;

    // Controllers
    private TrelloController mController;

    // Listeners
    private TrelloModel.OnCardAddedListener mOnCardAddedListener;

    // Activity variables
    private String mOrgaId;

    private BoardAdapter mBoardAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orga);

        // Instantiate view items
        mOrgaListText = (TextView) findViewById(R.id.orga_list);
        mBoardList = (ListView) findViewById(R.id.board_list);

        // Instantiate models
        mModel = TrelloModel.getInstance();

        // Instantiate controllers
        mController = TrelloController.getInstance();

        mBoardList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                BoardVO board = mBoardAdapter.getItem(position);
                Intent intent = new Intent(OrganizationActivity.this, BoardActivity.class);
                intent.putExtra(BundleKeys.BOARD_ID, board._id);
                startActivity(intent);
            }
        });


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

        // Release remaining resources
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(BundleKeys.BOARD_ID, mOrgaId);
    }

    private void getBundleExtras(final Bundle bundle) {
        if (bundle != null) {
            mOrgaId = bundle.getString(BundleKeys.ORGA_ID);
        }
    }

    private void populateView() {
        AllBoardsResultVO allBoard = mModel.getAllBoardsResult();
        ArrayList<BoardVO> filteredBoards = new ArrayList<BoardVO>();

        if (allBoard != null) {
            Log.d("BOARD", "allboard size " + allBoard.boards.size());
            for (BoardVO board : allBoard.boards) {
                if (mOrgaId.equals(board.idOrganization)) {
                    filteredBoards.add(board);
                }
            }

            for (OrganizationVO orga : allBoard.organizations) {
                if (orga._id.equals(mOrgaId)) {
                    Log.d("BOARD", orga.displayName);
                    mOrgaListText.setText(orga.displayName);
                }
            }
        }

        filteredBoards.trimToSize();

        mBoardAdapter = new BoardAdapter(this, R.id.name, filteredBoards);
        mBoardList.setAdapter(mBoardAdapter);


    }


}
