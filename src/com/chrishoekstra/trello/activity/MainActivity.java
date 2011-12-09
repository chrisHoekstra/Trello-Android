package com.chrishoekstra.trello.activity;

import android.app.Activity;
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

    // Class static definitions
    
    // View items
    private EditText mUsernameText;
    private EditText mPasswordText;
    private Button mLoginButton;
    
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
        mUsernameText = (EditText) findViewById(R.id.username);
        mPasswordText = (EditText) findViewById(R.id.password);
        mLoginButton  = (Button)   findViewById(R.id.login);
       
        // Instantiate models
        mModel = TrelloModel.getInstance();
        
        // Instantiate controllers
        mController = TrelloController.getInstance();
        
        // Create listeners
        mOnLoginCompleteListener = new TrelloModel.OnLoginCompleteListener() {
            @Override
            public void onLoginCompleteEvent(TrelloModel model) {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        };
        
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.login(mUsernameText.getText().toString(), mPasswordText.getText().toString());
            }
        });
        
        // Add listeners
        mModel.addListener(mOnLoginCompleteListener);
        
        // Get bundle extras
        getBundleExtras((savedInstanceState != null) ? savedInstanceState : getIntent().getExtras());
        
        // Instantiate activity variables
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