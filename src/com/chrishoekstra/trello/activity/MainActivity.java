package com.chrishoekstra.trello.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.controller.TrelloController;
import com.chrishoekstra.trello.model.TrelloModel;

public class MainActivity extends Activity {
    // Intent results static definitions

    // Dialog static definitions
    private static final int DIALOG_PROGRESS = 0;
    
    // Class static definitions
    
    // View items
    
    // Models
    private TrelloModel mModel;
    
    // Controllers
    private TrelloController mController;
    
    // Listeners
    private TrelloModel.OnLoginCompleteListener mOnLoginCompleteListener;
    
    // Activity variables
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Instantiate view items
       
        // Instantiate models
        mModel = TrelloModel.getInstance();
        
        // Instantiate controllers
        mController = TrelloController.getInstance();
        
        // Create listeners
        mOnLoginCompleteListener = new TrelloModel.OnLoginCompleteListener() {
            @Override
            public void onLoginCompleteEvent(TrelloModel model) {
                dismissDialog(DIALOG_PROGRESS);
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        };
        
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_PROGRESS);
                mController.login(((EditText) findViewById(R.id.username)).getText().toString(), 
                                  ((EditText) findViewById(R.id.password)).getText().toString());
            }
        });
        
        // Add listeners
        mModel.addListener(mOnLoginCompleteListener);
        
        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());
        
        // Instantiate activity variables
    }
    

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_PROGRESS:
                ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                dialog.setCustomTitle(null);
                dialog.setMessage(getString(R.string.logging_in));
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                return dialog;
        }
        
        return super.onCreateDialog(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        mModel.removeListener(mOnLoginCompleteListener);
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
}