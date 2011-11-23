package com.ch.trello.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ch.trello.R;
import com.ch.trello.controller.TrelloController;
import com.ch.trello.model.TrelloModel;

public class MainActivity extends Activity {
    // Intent results static definitions

    // Dialog static definitions

    // Class static definitions

    // View items
    private EditText mUsernameText;
    private EditText mPasswordText;
    private Button mLoginButton;

    protected SharedPreferences mPrefs;

    protected SharedPreferences.Editor mPrefsEditor;


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

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPrefsEditor = mPrefs.edit();

       String  mUsername = mPrefs.getString("username", "");
       String mPassword = mPrefs.getString("password", "");


        // Instantiate view items
        mUsernameText = (EditText) findViewById(R.id.username);
        mPasswordText = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.login);

        // Instantiate models
        mModel = TrelloModel.getInstance();

        // Instantiate controllers
        mController = TrelloController.getInstance();

        // Create listeners
        mOnLoginCompleteListener = new TrelloModel.OnLoginCompleteListener() {
            @Override
            public void onLoginCompleteEvent(TrelloModel model) {
                dologin(model);
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrefsEditor.putString("username", mUsernameText.getText().toString());
                mPrefsEditor.putString("password", mPasswordText.getText().toString());
                mPrefsEditor.commit();
                mController.login(MainActivity.this, mUsernameText.getText().toString(), mPasswordText.getText().toString());
            }
        });

        // Add listeners
        mModel.addListener(mOnLoginCompleteListener);

        if (!mUsername.equals("") && !mPassword.equals("")) {
            mController.login(MainActivity.this, mUsername, mPassword);
        }

        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());

        // Instantiate activity variables
    }

    private void dologin(TrelloModel model) {
        if (!model.isLoginFail.booleanValue()) {

            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            mPrefsEditor.putString("username", "");
            mPrefsEditor.putString("password", "");
            mPrefsEditor.commit();
            showError(getString(R.string.login_error), false);
        }
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

    protected void showError(String message, final boolean finish) {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage(message);
        build.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (finish)
                            finish();
                    }

                });
        build.create().show();
    }
}