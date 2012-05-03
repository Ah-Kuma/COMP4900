package com.nolifefool.healthdroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends Activity {
    
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        display();
    }
    
    public void display(){
    	String firstName;
        String lastName;
        int dayOfMonth;
        int month;
        int year;
        TextView nameText;
        TextView birthDateText;
        
    	SharedPreferences pref = getSharedPreferences(HealthDroidActivity.NAMESPACE, 0);
    	firstName = pref.getString("firstName", "firstName");
    	lastName = pref.getString("lastName", "lastName");
    	dayOfMonth = pref.getInt("dayOfMonth", -1);
    	month = pref.getInt("month", -1);
    	year = pref.getInt("year", -1);
    	
    	nameText = (TextView) findViewById(R.id.profile_name);
    	nameText.setText("Name: " + firstName + " " + lastName);
    	
    	birthDateText = (TextView) findViewById(R.id.profile_birthDate);
    	birthDateText.setText(Integer.toString(dayOfMonth) + "/" + Integer.toString(month) + "/" + Integer.toString(year));
    	
    	
    }
}
