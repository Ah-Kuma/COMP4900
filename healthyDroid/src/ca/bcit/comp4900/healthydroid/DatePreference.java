package ca.bcit.comp4900.healthydroid;

import java.util.Scanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

/**
 * Date picker dialog popup for the preferences activities.
 * 
 * @author William
 *
 */
public class DatePreference extends DialogPreference {
    private DatePicker datePick = null;
    
    private int dayOfMonth = 10;
    private int month = 10;
    private int year = 2000;

    public DatePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        setPositiveButtonText("OK");
        setNegativeButtonText("Cancel");
    }

    @Override
    protected View onCreateDialogView() {
        datePick = new DatePicker(getContext());
        if(Build.VERSION.SDK_INT >= 11)
        	datePick.setCalendarViewShown(false);
        return datePick;  
    }
    
    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        datePick.init(year,month,dayOfMonth, null);
    }
    
    @Override
    protected void onDialogClosed(boolean positiveResult) {
      super.onDialogClosed(positiveResult);

      if (positiveResult) {
        if (callChangeListener(datePick.getDayOfMonth()) || callChangeListener(datePick.getMonth()) || callChangeListener(datePick.getYear())) {
          dayOfMonth = datePick.getDayOfMonth();
          month = datePick.getMonth();
          year = datePick.getYear();
          persistString(dayOfMonth + "-" + month + "-" + year);
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
    		year = 2000;
            month = 11;
            dayOfMonth = 11;
            return;
    	}
        
    	String birthdate = getPersistedString("11-11-2000");
    	Scanner scan = new Scanner(birthdate);
    	scan.useDelimiter("-");
    	dayOfMonth = scan.nextInt();
    	month = scan.nextInt()+1;
    	year = scan.nextInt();
    }
    
    @Override
    public CharSequence getSummary(){
		return dayOfMonth + "-" + (month + 1) + "-" + year ;
    	
    }
}
