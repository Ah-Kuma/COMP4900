package ca.bcit.comp4900.healthydroid;


import com.michaelnovakjr.numberpicker.NumberPickerPreference;

import ca.bcit.comp4900.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Settings menu item that allows user to set the notificationPeriod and time
 * @author Kevin, William
 *
 */
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	private static final String ERROR_MSG = "No data";
	
	private static final String NOTIFICATIONENABLED_KEY = "notificationEnabled";
	private static final String TIME_KEY = "notificationTime";
    private static final String NOTIFICATIONPERIOD_KEY = "notificationPeriod";
    
    private CheckBoxPreference checkPref;
    private TimePreference timePref;
    private NumberPickerPreference dayPref;
    
    @Override  
    public void onCreate(Bundle bundle) {  
        super.onCreate(bundle);  
        this.getPreferenceManager().setSharedPreferencesName(HealthyDroidActivity.NAMESPACE);  
        addPreferencesFromResource(R.xml.settings);  
        
        
        // Get references to the preference items
        checkPref = (CheckBoxPreference)getPreferenceScreen().findPreference(NOTIFICATIONENABLED_KEY);
        timePref = (TimePreference)getPreferenceScreen().findPreference(TIME_KEY);
        dayPref = (NumberPickerPreference) getPreferenceScreen().findPreference(NOTIFICATIONPERIOD_KEY);
        
        
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
        //timePref.setSummary(sp.getString(TIME_KEY, errorMsg));
        checkPref.setSummary("Displays a notification a certain number of days after you take the quiz.");
        timePref.setSummary(convertMinutesToString(sp.getInt(TIME_KEY, -1)));
        dayPref.setSummary(convertDaysToString(sp.getInt(NOTIFICATIONPERIOD_KEY, -1)));
        
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
            timePref.setSummary(convertMinutesToString(sharedPreferences.getInt(TIME_KEY, -1)));
        }
        if(key.equals(NOTIFICATIONPERIOD_KEY)) {
            dayPref.setSummary(convertDaysToString(sharedPreferences.getInt(NOTIFICATIONPERIOD_KEY, -1)));
        }
        
        //Cancels any existing notification for the quiz if the Notification Enabled setting changes to false
        if(key.equals(NOTIFICATIONENABLED_KEY)) {
            if(sharedPreferences.getBoolean(NOTIFICATIONENABLED_KEY, false) == false)
                SetNotification.cancelNotification();
        }
    }  
    
    /**
     * Convert minutes into a String.
     * @param initial minutes
     * @return a string for displaying the time
     */
    private String convertMinutesToString(int initial) {
    	if(initial == -1)
    		return ERROR_MSG;
    	
    	int hours, minutes;
    	String result = "";
    	String amPmThing = "AM";
    	hours = initial / 60;
    	minutes = initial % 60;
    	if(hours > 12) {
    		hours -= 12;
    		amPmThing = "PM";
    	}else if (hours == 12) {
    	    amPmThing = "PM";
    	}else if (hours == 0) {
    		hours = 12;
    	}
    	result += hours + ":" + ((minutes < 10) ? "0" : "") + minutes + " " + amPmThing;
		return result;
    	
    }
    
    /**
     * Converts the number of days into a string for the summary message.
     * 
     * @param initial number of days
     * @return
     */
    private String convertDaysToString(int initial) {
        if(initial == -1)
            return ERROR_MSG;
        
        return "Notifies you " + initial +  " day" 
            + ((initial == 1) ? "" : "s") + " after your last quiz.";
    }
}
	

    



