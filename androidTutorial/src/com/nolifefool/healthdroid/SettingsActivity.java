package com.nolifefool.healthdroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
    
    public void resetSettings(View view) {
        SharedPreferences pref = getSharedPreferences(HealthDroidActivity.NAMESPACE, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(getApplicationContext(), "Settings reset", 600).show();
    }

}
