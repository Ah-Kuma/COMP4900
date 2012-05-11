package ca.bcit.comp4900.healthydroid;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Time picker dialog popup for the preferences activities.
 * 
 * @author Kevin, William
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
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
      //lastColor=(restoreValue ? getPersistedInt(lastColor) : (Integer)defaultValue);
    	if(!restoreValue) {
    		hour = 10;
            minute = 10;
            return;
    	}
        
    	int time;
    	time = getPersistedInt(100);
    	hour = time / 60;
    	minute = time % 60;
    }
    
     
    
    
    

}
