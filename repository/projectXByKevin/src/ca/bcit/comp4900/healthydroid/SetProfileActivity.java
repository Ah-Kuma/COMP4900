package ca.bcit.comp4900.healthydroid;


import ca.bcit.comp4900.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * This activity class let users to let his or her profile when
 * he or she launches this application for the first time
 * or the profile has been reset.
 * @author Kevin
 *
 */
public class SetProfileActivity extends Activity {
    private int currentPage;
    public static final int MAX_PAGE = 4;
    
    private Button nextButton;
    private Button backButton;
    private EditText fName;
    private EditText lName;
    private Spinner genderSpin;
    private DatePicker datePick;
    private TextView pageNumber;
 
    private String firstName;
    private String lastName;
    private String gender;
    private int dayOfMonth;
    private int month;
    private int year; 
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.setprofile);
    	
    	nextButton = (Button) findViewById(R.id.setProfile_nextButton);
        backButton = (Button) findViewById(R.id.setProfile_backButton);
        fName = (EditText) findViewById(R.id.setProfile_firstName);
        lName = (EditText) findViewById(R.id.setProfile_lastName);
        genderSpin = (Spinner) findViewById(R.id.setProfile_spinner);
        datePick = (DatePicker) findViewById(R.id.setProfile_datePicker);
        pageNumber = (TextView) findViewById(R.id.setProfile_pageNumber);
        
        firstName = "";
        lastName = "";
        gender = "";
        dayOfMonth = -1;
        month = -1;
        year = -1;
        
        currentPage = 1;
    }
    
    /**
     * Handler for the next button
     * 
     * @param view
     */
    public void nextView(View view) {
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.setProfile_viewFlip);
        
        switch(vf.getCurrentView().getId()) {
        case R.id.setProfile_view1:
            backButton.setVisibility(View.VISIBLE);
            backButton.setClickable(true);
            break;
        case R.id.setProfile_view2:
            firstName = fName.getText().toString();
            lastName = lName.getText().toString();
            if(firstName.equals("") || lastName.equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill in your name", 300).show();
                return;
            }
            break;
        case R.id.setProfile_view3:
        	gender = genderSpin.getSelectedItem().toString();
        	nextButton.setText("Finish");
        	break;
        case R.id.setProfile_view4:
            dayOfMonth = datePick.getDayOfMonth();
            month = datePick.getMonth();
            year = datePick.getYear();
            saveData();
            Intent intent = new Intent(this, HealthyDroidActivity.class);
            finish();
            startActivity(intent);
            return;
        default:
            throw new UnsupportedOperationException("Next button on unknown view");
        }
        
        currentPage++;
        pageNumber.setText(currentPage + "/" + MAX_PAGE);
        
        vf.setOutAnimation(view.getContext(), R.anim.view_transition_out_left);
        vf.setInAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.view_transition_in_left));
        vf.showNext();
    }
    
    /**
     * Handler for the back button
     * 
     * @param view
     */
    public void prevView(View view) {
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.setProfile_viewFlip);
        
        switch(vf.getCurrentView().getId()) {
        case R.id.setProfile_view1:
            throw new UnsupportedOperationException("Back button on view 1");
        case R.id.setProfile_view2:
            backButton.setVisibility(View.INVISIBLE);
            backButton.setClickable(false);
            break;
        case R.id.setProfile_view3:
        	nextButton.setText("Next");
        	break;
        case R.id.setProfile_view4:
            nextButton.setText("Next");
            break;
        default:
            throw new UnsupportedOperationException("Back button on unknown view");
        }
        
        currentPage--;
        pageNumber.setText(currentPage + "/" + MAX_PAGE);

        vf.setInAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.view_transition_in_right));
        vf.setOutAnimation(view.getContext(), R.anim.view_transition_out_right);
        vf.showPrevious();
    }
    
    /**
     * Saves the user data into SharedPreferences
     */
    private void saveData() {
        if(firstName == "" || lastName == "" || gender == "" || dayOfMonth == -1 || month == -1 || year == -1) {
        	throw new IllegalArgumentException("Inputs are invalid");
        }
    	
    	SharedPreferences prefs = getSharedPreferences(HealthyDroidActivity.NAMESPACE, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("gender", gender);
        editor.putString("birthdate", (dayOfMonth + "-" + month + "-" + year));
        editor.commit();
    }
}
