package ca.bcit.comp4900.healthydroid;


import ca.bcit.comp4900.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Settings menu item that allows user to set the notificationPeriod and time
 * @author Kevin
 *
 */
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	private static final String errorMsg = "No data";
	
	private static final String TIME_KEY = "time";
    private static final String NOTIFICATIONPERIOD_KEY = "notificationPeriod";
    
    private TimePreference timePref;
    private DatePreference datePref;
    
    
    @Override  
    public void onCreate(Bundle bundle) {  
        super.onCreate(bundle);  
        this.getPreferenceManager().setSharedPreferencesName(HealthyDroidActivity.NAMESPACE);  
        addPreferencesFromResource(R.xml.settings);  
        
        // Get references to the preference items
        timePref = (TimePreference)getPreferenceScreen().findPreference(TIME_KEY);
        datePref = (DatePreference) getPreferenceScreen().findPreference(NOTIFICATIONPERIOD_KEY);
        
        // Set the reset preference
        Preference button = (Preference)getPreferenceScreen().findPreference("resetButton");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences sp = getPreferenceManager().getSharedPreferences();
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(), "Settings reset", 600).show();
                finish();
                return true;
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = this.getPreferenceManager().getSharedPreferences();
        
        // Setup the initial values
        timePref.setSummary(sp.getString(TIME_KEY, errorMsg));
        datePref.setSummary(sp.getString(NOTIFICATIONPERIOD_KEY, errorMsg));
        
        // Set up a listener whenever a key changes 
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        // Unregister the listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // If preferences change, update the displayed preference
        if (key.equals(TIME_KEY)) {
            timePref.setSummary(sharedPreferences.getString(TIME_KEY, errorMsg));
        }
        if(key.equals(NOTIFICATIONPERIOD_KEY)) {
            datePref.setSummary(sharedPreferences.getString(NOTIFICATIONPERIOD_KEY, errorMsg));
        }
    }  
}
	

    



