package com.nolifefool.healthdroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * Activity for the screen that shows when you first install.
 * Asks for user's info.
 * 
 * @author William
 *
 */
public class FirstRunActivity extends Activity {
    
    private int currentPage;
    public static final int MAX_PAGE = 3;
    
    private Button nextButton;
    private Button backButton;
    private EditText fName;
    private EditText lName;
    private DatePicker datePick;
    private TextView pageNumber;
 
    private String firstName;
    private String lastName;
    private int dayOfMonth;
    private int month;
    private int year;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstrun);
        
        nextButton = (Button) findViewById(R.id.first_nextButton);
        backButton = (Button) findViewById(R.id.first_backButton);
        fName = (EditText) findViewById(R.id.first_firstName);
        lName = (EditText) findViewById(R.id.first_lastName);
        datePick = (DatePicker) findViewById(R.id.first_datePicker);
        pageNumber = (TextView) findViewById(R.id.first_pageNumber);
        
        firstName = "";
        lastName = "";
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
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlip);
        
        switch(vf.getCurrentView().getId()) {
        case R.id.firstrun_v1:
            backButton.setVisibility(View.VISIBLE);
            backButton.setClickable(true);
            break;
        case R.id.firstrun_v2:
            firstName = fName.getText().toString();
            lastName = lName.getText().toString();
            if(firstName.equals("") || lastName.equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill in your name", 600).show();
                return;
            }
            nextButton.setText("Finish");
            break;
        case R.id.firstrun_v3:
            dayOfMonth = datePick.getDayOfMonth();
            month = datePick.getMonth();
            year = datePick.getYear();
            saveData();
            Intent intent = new Intent(this, HealthDroidActivity.class);
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
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlip);
        
        switch(vf.getCurrentView().getId()) {
        case R.id.firstrun_v1:
            throw new UnsupportedOperationException("Back button on view 1");
        case R.id.firstrun_v2:
            backButton.setVisibility(View.INVISIBLE);
            backButton.setClickable(false);
            break;
        case R.id.firstrun_v3:
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
        SharedPreferences prefs = getSharedPreferences(HealthDroidActivity.NAMESPACE, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putInt("dayOfMonth", dayOfMonth);
        editor.putInt("month", month);
        editor.putInt("year", year);
        editor.commit();
    }
}