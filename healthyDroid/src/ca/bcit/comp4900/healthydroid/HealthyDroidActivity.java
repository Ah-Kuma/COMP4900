package ca.bcit.comp4900.healthydroid;

import ca.bcit.comp4900.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * The main activity of the application.
 * It contains three buttons for executing different functionalities.
 * -Take a quiz
 * -Send report
 * -Backup data
 * It also has a menu for editing profile and setting.
 * @author Kevin, William
 *
 */
public class HealthyDroidActivity extends Activity {

	public static final String NAMESPACE = "ca.bcit.comp4900.healthydroid";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	checkProfile();
        
        //Call the quizButtonOnClick method when the quiz button is clicked
    	Button quizButton = (Button)findViewById(R.id.main_quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				quizButtonOnClick(v);		
			}
		});
        
        //Call the sendReportButtonOnClick method when the send report button is clicked
    	Button sendReportButton = (Button)findViewById(R.id.main_sendReportButton);
        sendReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendReportButtonOnClick(v);		
			}
		});
        
    }
    @Override
    public void onResume(){
    	super.onResume();
    	checkProfile();
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
      * Change the view to the quiz view after user clicks the quiz button.
      * @param v
      */
      public void quizButtonOnClick(View v){  	
          Intent intent = new Intent(this, QuizActivity.class);
          startActivity(intent);
      }
      
      /**
       * Change the view to the report view after user clicks the send report button.
       * @param v
       */
       public void sendReportButtonOnClick(View v){  	
    	   //Report report = new Report(this.getBaseContext());
    	   //Intent intent = report.getLineBitMap(3);
    	   Intent intent = new Intent(this, ReportActivity.class);
    	   startActivity(intent);
       }
        
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater mi = getMenuInflater();
            mi.inflate(R.menu.menu, menu);
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
