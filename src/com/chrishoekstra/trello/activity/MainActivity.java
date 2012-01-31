package com.chrishoekstra.trello.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.TrelloApplication;
import com.chrishoekstra.trello.listener.UserDataReceivedListener;
import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.service.TrelloBinder;
import com.chrishoekstra.trello.service.TrelloService;
import com.chrishoekstra.trello.vo.BoardVO;
import com.chrishoekstra.trello.vo.MemberVO;
import com.chrishoekstra.trello.vo.NotificationVO;

public class MainActivity extends Activity {

    // Dialog static definitions
    private static final int DIALOG_PROGRESS = 0;
    private static final int DIALOG_LOGIN_ERROR = 1;

    // Shared preferences definitions
    private static final String TOKEN = "token";

    // View Items
    private EditText mTokenEdit;
    private TextView mLinkText;

    // Activity variables
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mPrefsEditor;

    private State mState;
    private boolean mIsConfigurationChanging;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Instantiate state
        mState = (State) getLastNonConfigurationInstance();
        if (mState == null) {
            mState = new State();
            getApplicationContext().bindService(new Intent(this, TrelloService.class), mState, BIND_AUTO_CREATE);
        }
        mState.attach(this);

        // Instantiate view items
        mTokenEdit = (EditText) findViewById(R.id.token);
        mLinkText  = (TextView) findViewById(R.id.request_token_link);

        // Create listeners
        findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_PROGRESS);

                String token = mTokenEdit.getText().toString();
                mState.binder.setToken(token);
                mState.binder.fetchUserData(mState);

                mPrefsEditor = mPrefs.edit();
                mPrefsEditor.putString(TOKEN, token);
                mPrefsEditor.commit();
            }
        });

        // Instantiate activity variables
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

        if (!mIsConfigurationChanging) {
            getApplicationContext().unbindService(mState);
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        mIsConfigurationChanging = true;

        return mState;
    }

    private void onUserDataReceived() {
        Intent intent = new Intent(this, TrelloTabActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        dismissDialog(DIALOG_PROGRESS);

        finish();
    }
    
    private void handleError(Exception e) {
        dismissDialog(DIALOG_PROGRESS);
        showDialog(DIALOG_LOGIN_ERROR);
    }

    private void onBinderAvailable() {
        String token = mPrefs.getString(TOKEN, null);

        if (token != null) {
            showDialog(DIALOG_PROGRESS);
            mState.binder.setToken(token);
            mState.binder.fetchUserData(mState);
        } else {
            mLinkText.setText(mState.binder.getRequestLink());
        }
    }
    
    private static class State implements ServiceConnection, UserDataReceivedListener {
        private MainActivity activity;
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

        public void attach(MainActivity activity) {
            this.activity = activity;
            
            model = ((TrelloApplication)activity.getApplication()).getModel();
        }

        
        // Listeners

        public void onUserDataReceived(ArrayList<BoardVO> boards, MemberVO user, ArrayList<NotificationVO> notifications) {
            model.setAllBoards(boards);
            model.setUser(user);
            model.setNotifications(notifications);

            activity.onUserDataReceived();
        }

        public void handleError(Exception e) {
            activity.handleError(e);
        }
    }
}