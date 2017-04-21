package com.example.todo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Chhavi on 20-Apr-17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    static int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle("REMINDING YOU!")
                .setAutoCancel(true)
                .setContentText("Alarm !!!" );

        Intent resultIntent=new Intent(context,MeetingAddActivity.class);
        PendingIntent resultPendingIntent= PendingIntent.getActivity(context,i,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
        i++;

    }
}
