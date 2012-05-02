package ca.bcit.comp4900.healthydroid;


import ca.bcit.comp4900.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        
    	Button reset = (Button)findViewById(R.id.profile_resetButton);
        reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetButtonOnClick(v);		
			}
        });
    }
    
    /**
     * Change the view after user clicks the next button
     * @param v
     */
     public void resetButtonOnClick(View v){  	
     	SharedPreferences prefs = getSharedPreferences(HealthyDroidActivity.NAMESPACE, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    	finish();
     }
    
}
