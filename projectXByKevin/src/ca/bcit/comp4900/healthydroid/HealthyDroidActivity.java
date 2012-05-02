package ca.bcit.comp4900.healthydroid;


import ca.bcit.comp4900.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HealthyDroidActivity extends Activity {

	public static final String NAMESPACE = "ca.bcit.comp4900.healthydroid";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	checkProfile();
    	
    	Button profileButton = (Button)findViewById(R.id.main_profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				profileButtonOnClick(v);		
			}
		});
        
    	Button quizButton = (Button)findViewById(R.id.main_quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				quizButtonOnClick(v);		
			}
		});
    }
    
    /**
     * Checks if this is the first time the user has run the program.
     * If so, runs FirstRunActivity.
     */
    private void checkProfile() {
        SharedPreferences prefs = getSharedPreferences(NAMESPACE, 0);
        if(!prefs.contains("firstName")) {
            Intent intent = new Intent(this, SetProfileActivity.class);
            finish();
            startActivity(intent);
        } 
    }
    
    /**
     * Change the view after user clicks the profile button
     * @param v
     */
     public void profileButtonOnClick(View v){  	
         Intent intent = new Intent(this, ProfileActivity.class);
         startActivity(intent);
     }
     
     /**
      * Change the view after user clicks the quiz button
      * @param v
      */
      public void quizButtonOnClick(View v){  	
          Intent intent = new Intent(this, QuizActivity.class);
          startActivity(intent);
      }
}
