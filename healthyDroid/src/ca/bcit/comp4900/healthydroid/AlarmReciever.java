package ca.bcit.comp4900.healthydroid;

import ca.bcit.comp4900.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Responds to the broadcast created by the AlarmManager.
 * This class needs to be added as a Service in the manifest.
 * 
 * Creates the notification.
 * 
 * @author William
 *
 */
public class AlarmReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        
        NotificationManager nm;
        nm = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
        
        //instantiate the notification
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "Time to take your quiz!!!!";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        
        //define notification's message and pending intent
        Context context = arg0.getApplicationContext();
        CharSequence contentTitle = "HealthyDroid Notification";
        CharSequence contentText = "Please complete the quiz.";
        Intent notificationIntent = new Intent(arg0, QuizActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(arg0, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLatestEventInfo(arg0, contentTitle, contentText, contentIntent);
        
        //show the notification
        nm.notify(1, notification);

    }

}