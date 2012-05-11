package ca.bcit.comp4900.healthydroid;

import java.util.Scanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Time picker dialog popup for the preferences activities.
 * 
 * @author Kevin
 *
 */
public class TimePreference extends DialogPreference {
    private TimePicker timePick = null;
    
    private int hour = 10;
    private int minute = 10;

    public TimePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        setPositiveButtonText("OK");
        setNegativeButtonText("Cancel");
    }

    @Override
    protected View onCreateDialogView() {
        timePick = new TimePicker(getContext());
        return timePick;  
    }
    
    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        timePick.setCurrentHour(hour);
        timePick.setCurrentMinute(minute);
    }
    
    @Override
    protected void onDialogClosed(boolean positiveResult) {
      super.onDialogClosed(positiveResult);

      if (positiveResult) {
        if (callChangeListener(timePick.getCurrentHour()) || callChangeListener(timePick.getCurrentMinute())) {
          hour = timePick.getCurrentHour();
          minute = timePick.getCurrentMinute();
          persistInt(hour * 60 + minute);
        }
      }
    }
    
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
      return(a.getString(index));
    }
    
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
      //lastColor=(restoreValue ? getPersistedInt(lastColor) : (Integer)defaultValue);
    	if(!restoreValue) {
    		hour = 10;
            minute = 10;
            return;
    	}
        
    	String time = getPersistedString("10:10");
    	Scanner scan = new Scanner(time);
    	scan.useDelimiter(":");
    	hour = scan.nextInt();
    	minute = scan.nextInt();
    }
    
    @Override
    public CharSequence getSummary(){
		return hour + ":" + minute;
    	
    }
}
