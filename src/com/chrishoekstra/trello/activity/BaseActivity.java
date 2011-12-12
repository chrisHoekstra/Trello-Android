package com.chrishoekstra.trello.activity;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import com.chrishoekstra.trello.R;

/**
 * User: Guernion Sylvain
 * Date: 26/11/11
 * Time: 17:01
 */
public class BaseActivity extends Activity {

    public static final String ACTION_LOGOUT = "com.ch.trello.activity.LOGOUT";

    protected SharedPreferences mPrefs;

    protected SharedPreferences.Editor mPrefsEditor;

    protected BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPrefsEditor = mPrefs.edit();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_LOGOUT);
        registerReceiver(receiver, intentFilter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.connexion:
                mPrefsEditor.putString("username", "");
                mPrefsEditor.putString("password", "");
                mPrefsEditor.commit();
                Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.quitter:
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(ACTION_LOGOUT);
                sendBroadcast(broadcastIntent);
                return true;
            default:
                break;
        }
        return false;
    }

}


