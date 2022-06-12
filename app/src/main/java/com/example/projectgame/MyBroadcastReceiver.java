package com.example.projectgame;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "myChannel")
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Ground and Heavens")
                .setContentText("Enter to the game! I miss you.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);


        notificationManagerCompat.notify(0, builder.build());

    }
}
