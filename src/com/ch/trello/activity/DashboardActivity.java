package com.ch.trello.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.ch.trello.BundleKeys;
import com.ch.trello.R;
import com.ch.trello.adapter.BoardAdapter;
import com.ch.trello.adapter.OrganizationAdapter;
import com.ch.trello.controller.TrelloController;
import com.ch.trello.model.TrelloModel;
import com.ch.trello.service.Gravatar;
import com.ch.trello.vo.AllBoardsResultVO;
import com.ch.trello.vo.BoardVO;
import com.ch.trello.vo.MemberVO;
import com.ch.trello.vo.OrganizationVO;

import java.io.IOException;
import java.util.ArrayList;

public class DashboardActivity extends Activity {
    
    private static String TAG  = DashboardActivity.class.getSimpleName();

    // Intent results static definitions

    // Dialog static definitions

    // Class static definitions

    // View items
    private ListView mBoardsList;
    private ListView mOrgasList;
    private TextView mFullNameText;
    private TextView mUsernameText;
    private ImageView mGravatar;

    // Models
    private TrelloModel mModel;

    // Controllers
    private TrelloController mController;

    // Listeners

    // Activity variables
    private BoardAdapter mBoardAdapter;
    private OrganizationAdapter mOrgaAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        // Instantiate view items
        mBoardsList = (ListView) findViewById(R.id.board_list);
        mOrgasList = (ListView) findViewById(R.id.orga_list);
        mFullNameText = (TextView) findViewById(R.id.full_name);
        mUsernameText = (TextView) findViewById(R.id.username);
        mGravatar  = (ImageView) findViewById(R.id.gravatar);

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

        mOrgasList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                OrganizationVO orga = mOrgaAdapter.getItem(position);
                Intent intent = new Intent(DashboardActivity.this, OrganizationActivity.class);
                intent.putExtra(BundleKeys.ORGA_ID, orga._id);
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
        ArrayList<BoardVO> filteredBoards = new ArrayList<BoardVO>();
        for (BoardVO board : allBoards.boards) {
            if (board.idOrganization == null || board.idOrganization.equals("")) {
                filteredBoards.add(board);
            }
        }

        mBoardAdapter = new BoardAdapter(this, R.id.name, filteredBoards);
        mBoardsList.setAdapter(mBoardAdapter);

        mOrgaAdapter = new OrganizationAdapter(this, R.id.name, allBoards.organizations);
        mOrgasList.setAdapter(mOrgaAdapter);

        MemberVO user = null;
        for (MemberVO member : allBoards.members) {
            if (member._id.equals(allBoards.idMember)) {
                user = member;
                break;
            }
        }

        if (user != null) {
            try {
                mGravatar.setImageBitmap(Gravatar.downloadGravatar(user.gravatar));
            } catch (IOException e) {
                Log.e(TAG,"error gravatar img");
            }
            mFullNameText.setText(user.fullName);
            mUsernameText.setText('(' + user.username + ')');
        }
    }
}
