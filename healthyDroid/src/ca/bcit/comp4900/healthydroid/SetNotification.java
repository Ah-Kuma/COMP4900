package ca.bcit.comp4900.healthydroid;

import java.util.NoSuchElementException;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

/**
 * A class that has static methods for setting and cancelling the alarm for the notification.
 * 
 * @author William
 *
 */
public class SetNotification {
    static PendingIntent pendIntent;
    static AlarmManager alarmMan;
    
    /**
     * Sets the alarm for the notification
     * 
     * @param am the alarm manger
     * @param context the context
     * @param notifiMinutes the time the notification will be called at
     * @param notifDays the day between the notifications
     */
    public static void setNotifiAlarm(AlarmManager am, Context context, int notifiMinutes, int notifDays){
        int hour, minute, currentTime;
        alarmMan = am;
        
        //Get current time
        Time time = new Time();
        time.setToNow();
        hour = time.hour;
        minute = time.minute;
        //convert current hour and minute into minute
        currentTime = hour * 60 + minute; 
        
        //Get the notification minute from shared preferences
        if(notifiMinutes == -1 || notifDays == -1)
            throw new NoSuchElementException("Invalid or missing minute or day value");
        
        //Set the alarm for notification
        Intent intent = new Intent(context, AlarmReciever.class);
        pendIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.set(AlarmManager.RTC, System.currentTimeMillis() 
                - (currentTime * 60000)
                + (notifiMinutes * 60000)
                + (notifDays * 86400000) , pendIntent);

    }
    
    /**
     * Cancels any set notification alarms.
     */
    public static void cancelNotification() {
        if(alarmMan == null || pendIntent == null)
            return;
        alarmMan.cancel(pendIntent);
    }

}
