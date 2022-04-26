package com.example.projectgame;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

       // if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
            //Toast.makeText(context,"you shouldn't connect your phone while you are playing, it is dangerous! " , Toast.LENGTH_LONG).show();


        //Intent intent2 = new Intent(context, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "myChannel")
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Ground and Heavens")
                .setContentText("Enter to the game!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);


                //.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        if(!MainActivity.isOn) {
            notificationManagerCompat.notify(0, builder.build());
        }



    }
}
