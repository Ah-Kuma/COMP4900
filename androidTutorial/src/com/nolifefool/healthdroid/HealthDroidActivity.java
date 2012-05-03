package com.nolifefool.healthdroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class HealthDroidActivity extends Activity {
    public static final String NAMESPACE = "com.nolifefool.healthdroid";
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        checkForFirstRun();
    }
    
    /**
     * Checks if this is the first time the user has run the program.
     * If so, runs FirstRunActivity.
     */
    private void checkForFirstRun() {
        SharedPreferences prefs = getSharedPreferences(NAMESPACE, 0);
        if(!prefs.contains("firstName")) {
            Intent intent = new Intent(this, FirstRunActivity.class);
            finish();
            startActivity(intent);
        } 
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);
        return true;  
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        switch(item.getItemId()) {
        case R.id.menu_profile:
        	intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        case R.id.menu_settings:
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
}