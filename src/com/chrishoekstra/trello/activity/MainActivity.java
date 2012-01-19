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
import android.widget.TextView;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.controller.TrelloController;
import com.chrishoekstra.trello.model.TrelloModel;

public class MainActivity extends Activity {

    // Dialog static definitions
    private static final int DIALOG_PROGRESS = 0;
    private static final int DIALOG_LOGIN_ERROR = 1;

    // Shared preferences definitions
    private static final String TOKEN = "token";
    
    // View Items
    private EditText mTokenEdit;
    private TextView mLinkText;
    
    // Models
    private TrelloModel mModel;
    
    // Controllers
    private TrelloController mController;
    
    // Listeners
    private TrelloModel.OnUserDataReceivedListener mOnUserDataReceivedListener;

    // Activity variables
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mPrefsEditor;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Instantiate view items
        mTokenEdit = (EditText) findViewById(R.id.token);
        mLinkText  = (TextView) findViewById(R.id.request_token_link);
        
        // Instantiate models
        mModel = TrelloModel.getInstance();
        
        // Instantiate controllers
        mController = TrelloController.getInstance();
        
        // Create listeners
        mOnUserDataReceivedListener = new TrelloModel.OnUserDataReceivedListener() {
            @Override
            public void onUserDataReceived(TrelloModel model, Boolean result) {
                if (result != null) {
                    Intent intent = new Intent(MainActivity.this, TrelloTabActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    dismissDialog(DIALOG_PROGRESS);
                    
                    finish();
                } else {
                    dismissDialog(DIALOG_PROGRESS);
                    showDialog(DIALOG_LOGIN_ERROR);
                }
            }
        };
        
        findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_PROGRESS);
                
                String token = mTokenEdit.getText().toString();
                mController.setToken(token);
                mController.getUserData();
                
                mPrefsEditor.putString(TOKEN, token);
                mPrefsEditor.commit();                
            }
        });
        
        // Add listeners
        mModel.addListener(mOnUserDataReceivedListener);
        
        // Instantiate activity variables
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPrefsEditor = mPrefs.edit();
        
        String token = mPrefs.getString(TOKEN, "");
        
        if (!token.equals("")) {
            showDialog(DIALOG_PROGRESS);
            mController.setToken(token);
            mController.getUserData();
        } else {
            mLinkText.setText(mController.getRequestLink());
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_PROGRESS:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setCustomTitle(null);
                dialog.setMessage(getString(R.string.loading));
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
        
        mModel.removeListener(mOnUserDataReceivedListener);
    }
}