package me.bedcomfort.android.notification;

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
        int icon = R.drawable.icon;
        CharSequence tickerText = "HI IM A NOTIFICATION";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        
        //define notification's message and pending intent
        Context context = arg0.getApplicationContext();
        CharSequence contentTitle = "I'm a notification";
        CharSequence contentText = "I'm here to notify you that I'm a notification.";
        Intent notificationIntent = new Intent(arg0, NotificationTestActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(arg0, 0, notificationIntent, 0);
        notification.setLatestEventInfo(arg0, contentTitle, contentText, contentIntent);
        
        //show the notification
        nm.notify(1, notification);

    }

}
