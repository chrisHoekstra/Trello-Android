package com.chrishoekstra.trello.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.controller.TrelloController;
import com.chrishoekstra.trello.model.TrelloModel;

public class MainActivity extends Activity {
    // Intent results static definitions

    // Dialog static definitions
    private static final int DIALOG_PROGRESS = 0;
    private static final int DIALOG_LOGIN_ERROR = 1;
    
    // Class static definitions
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";
    
    // View items
    
    // Models
    private TrelloModel mModel;
    
    // Controllers
    private TrelloController mController;
    
    // Listeners
    private TrelloModel.OnLoginCompleteListener mOnLoginCompleteListener;
    
    // Activity variables
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mPrefsEditor;
    
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
            public void onLoginCompleteEvent(TrelloModel model, boolean successful) {
                if (successful) {
                    Intent intent = new Intent(MainActivity.this, TrelloTabActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    dismissDialog(DIALOG_PROGRESS);
                    
                    finish();
                } else {
                    dismissDialog(DIALOG_PROGRESS);
                    showDialog(DIALOG_LOGIN_ERROR);
                    
                    mPrefsEditor.putString(USERNAME, "");
                    mPrefsEditor.putString(PASSWORD, "");
                    mPrefsEditor.commit();
                }
            }
        };
        
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_PROGRESS);
                
                String username = ((EditText) findViewById(R.id.username)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                
                mController.login(username, password);
                
                mPrefsEditor.putString(USERNAME, username);
                mPrefsEditor.putString(PASSWORD, password);
                mPrefsEditor.commit();
            }
        });
        
        // Add listeners
        mModel.addListener(mOnLoginCompleteListener);
        
        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());
        
        // Instantiate activity variables
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPrefsEditor = mPrefs.edit();
        
        String username = mPrefs.getString(USERNAME, "");
        String password = mPrefs.getString(PASSWORD, "");
        
        if (!username.equals("") && !password.equals("")) {
            showDialog(DIALOG_PROGRESS);
            
            ((EditText) findViewById(R.id.username)).setText(username);
            ((EditText) findViewById(R.id.password)).setText(password);
            
            mController.login(username, password);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_PROGRESS:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setCustomTitle(null);
                dialog.setMessage(getString(R.string.logging_in));
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                return dialog;
            case DIALOG_LOGIN_ERROR:
                return new AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(getResources().getString(R.string.login_error_message))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();
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