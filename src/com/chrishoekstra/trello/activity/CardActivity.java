package com.chrishoekstra.trello.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chrishoekstra.trello.BundleKeys;
import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.activity.BaseActivity;
import com.chrishoekstra.trello.controller.TrelloController;
import com.chrishoekstra.trello.model.TrelloModel;

import com.chrishoekstra.trello.vo.CardVO;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends BaseActivity {

    // Intent results static definitions

    // Dialog static definitions

    // Class static definitions

    // View items
    private TextView mNameText;
    private TextView mDescriptionText;

    // Models
    private TrelloModel mModel;

    // Controllers
    private TrelloController mController;

    // Listeners

    // Activity variables
    private CardVO mCard;
    private String mCardId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);

        // Instantiate view items
        mNameText = (TextView) findViewById(R.id.name);
        mDescriptionText = (TextView) findViewById(R.id.description);


        // Instantiate models
        mModel = TrelloModel.getInstance();

        // Instantiate controllers
        mController = TrelloController.getInstance();

        // Create listeners

        // Add listeners

        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());

        // Instantiate activity variables

        ArrayList<CardVO> cards = mModel.getCurrentBoard().cards;
        for (CardVO card : cards) {
            if (card._id.equals(mCardId)) {
                mCard = card;
            }
        }

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

        outState.putString(BundleKeys.CARD_ID, mCardId);
    }

    private void getBundleExtras(final Bundle bundle) {
        if (bundle != null) {
            mCardId = bundle.getString(BundleKeys.CARD_ID);
        }
    }

    private void populateView() {
        mNameText.setText(mCard.name);
        mDescriptionText.setText(mCard.desc);
        initLabels(mCard.labels);
    }


    private void initLabels(List<String> labels) {

        View labelsView = findViewById(R.id.labels);
        TextView labelGreen = (TextView) labelsView.findViewById(R.id.labelGreen);
        TextView labelYellow = (TextView) labelsView.findViewById(R.id.labelYellow);
        TextView labelOrange = (TextView) labelsView.findViewById(R.id.labelOrange);
        TextView labelRed = (TextView) labelsView.findViewById(R.id.labelRed);
        TextView labelBlue = (TextView) labelsView.findViewById(R.id.labelBlue);
        TextView labelPurple = (TextView) labelsView.findViewById(R.id.labelPurple);

        boolean hasGreen = false;
        boolean hasYellow = false;
        boolean hasOrange = false;
        boolean hasRed = false;
        boolean hasBlue = false;
        boolean hasPurple = false;

        for (String label : labels) {
            if (label.equals("green")) {
                hasGreen = true;
            }
            if (label.equals("yellow")) {
                hasYellow = true;
            }
            if (label.equals("orange")) {
                hasOrange = true;
            }
            if (label.equals("red")) {
                hasRed = true;
            }
            if (label.equals("blue")) {
                hasBlue = true;
            }
            if (label.equals("purple")) {
                hasPurple = true;
            }

        }

        if (!hasGreen) {
            labelGreen.setVisibility(View.INVISIBLE);
            labelGreen.setWidth(0);
            ((LinearLayout) labelGreen.getParent()).setPadding(0, 0, 0, 0);

        }
        if (!hasYellow) {
            labelYellow.setVisibility(View.INVISIBLE);
            labelYellow.setWidth(0);
            ((LinearLayout) labelYellow.getParent()).setPadding(0, 0, 0, 0);

        }
        if (!hasOrange) {
            labelOrange.setVisibility(View.INVISIBLE);
            labelOrange.setWidth(0);
            ((LinearLayout) labelOrange.getParent()).setPadding(0, 0, 0, 0);

        }
        if (!hasRed) {
            labelRed.setVisibility(View.INVISIBLE);
            labelRed.setWidth(0);
            ((LinearLayout) labelRed.getParent()).setPadding(0, 0, 0, 0);

        }
        if (!hasBlue) {
            labelBlue.setVisibility(View.INVISIBLE);
            labelBlue.setWidth(0);
            ((LinearLayout) labelBlue.getParent()).setPadding(0, 0, 0, 0);

        }
        if (!hasPurple) {
            labelPurple.setVisibility(View.INVISIBLE);
            labelPurple.setWidth(0);
        }
    }
}
