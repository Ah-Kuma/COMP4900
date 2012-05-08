package me.bedcomfort.android.notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Example of timed notifications. Uses an AlarmManager.
 * 
 * @author William
 *
 */
public class NotificationTestActivity extends Activity {
    private SeekBar numPick;
    private Toast toastMaster3000;
    private TextView toastText;
    private AlarmManager am;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setSeekBar();
        setToast();
        
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }
    
    /**
     * Sets the AlarmManager so the notification will be created at that time.
     * 
     * @param v
     */
    public void buttonClick(View v) {
        Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        
        am.set(AlarmManager.RTC, System.currentTimeMillis() + (numPick.getProgress() * 1000), pi);
    }
    
    /**
     * assigns the seekbar view to the numPick member variable.
     * also creates a listener class to show the current selected number in a custom toast.
     */
    private void setSeekBar() {
        numPick = (SeekBar) findViewById(R.id.seekBar1);
        numPick.setMax(10);
        numPick.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               toastMaster3000.cancel();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                toastText.setText(Integer.toString(numPick.getProgress()));
                toastMaster3000.show();
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                toastText.setText(Integer.toString(numPick.getProgress()));
                toastMaster3000.show();   
            }
        });
    }
    
    /**
     * Creates the custom toast that shows the value of the seek bar.
     */
    private void setToast() {
        LayoutInflater infl = getLayoutInflater();
        View layout = infl.inflate(R.layout.toast_and_eggs, (ViewGroup)findViewById(R.id.toastlayout));
        toastText = (TextView) layout.findViewById(R.id.text);
        toastMaster3000 = new Toast(getApplicationContext());
        toastMaster3000.setGravity(Gravity.TOP, 0, 250);
        toastMaster3000.setDuration(Toast.LENGTH_LONG);
        toastMaster3000.setView(layout);
    }
}