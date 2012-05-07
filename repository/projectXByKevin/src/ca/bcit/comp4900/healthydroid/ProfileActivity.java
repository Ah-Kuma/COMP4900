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

public class ProfileActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	private static final String errorMsg = "No data";
	
    private static final String FIRST_NAME_KEY = "firstName";
    private static final String LAST_NAME_KEY = "lastName";
    private static final String GENDER_KEY = "gender";
    private static final String BIRTHDATE_KEY = "birthdate";
    
    private EditTextPreference firstNamePref;
    private EditTextPreference lastNamePref;
    private ListPreference genderPref;
    private DatePreference datePref;
    
    
    @Override  
    public void onCreate(Bundle bundle) {  
        super.onCreate(bundle);  
        this.getPreferenceManager().setSharedPreferencesName(HealthyDroidActivity.NAMESPACE);  
        addPreferencesFromResource(R.xml.profile);  
        
        // Get references to the preference items
        firstNamePref = (EditTextPreference)getPreferenceScreen().findPreference(FIRST_NAME_KEY);
        lastNamePref = (EditTextPreference)getPreferenceScreen().findPreference(LAST_NAME_KEY);
        genderPref = (ListPreference) getPreferenceScreen().findPreference(GENDER_KEY);
        datePref = (DatePreference) getPreferenceScreen().findPreference(BIRTHDATE_KEY);
        
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
        firstNamePref.setSummary(sp.getString(FIRST_NAME_KEY, errorMsg));
        lastNamePref.setSummary(sp.getString(LAST_NAME_KEY, errorMsg));
        genderPref.setSummary(sp.getString(GENDER_KEY, errorMsg));
        datePref.setSummary(sp.getString(BIRTHDATE_KEY, errorMsg));
        
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
        if (key.equals(FIRST_NAME_KEY)) {
            firstNamePref.setSummary(sharedPreferences.getString(FIRST_NAME_KEY, errorMsg));
        }
        if (key.equals(LAST_NAME_KEY)) {
            lastNamePref.setSummary(sharedPreferences.getString(LAST_NAME_KEY, errorMsg));
        }
        if (key.equals(GENDER_KEY)) {
            genderPref.setSummary(sharedPreferences.getString(GENDER_KEY, errorMsg));
        }
        if(key.equals(BIRTHDATE_KEY)) {
            datePref.setSummary(sharedPreferences.getString(BIRTHDATE_KEY, errorMsg));
        }
    }  
}
	

    


