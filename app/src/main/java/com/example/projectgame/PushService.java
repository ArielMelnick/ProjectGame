package com.example.projectgame;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.media.audiofx.AudioEffect;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.SoundEffectConstants;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.time.LocalTime;
import java.util.Calendar;

public class PushService extends Service {
    public PushService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {


        startForeground(1, getNotification());


        return super.onStartCommand(intent, flags, startId);
    }


    public Notification getNotification() {
        String title;
        if (!MainActivity.isOn)
            title = "Enter to the game!";
        else
            title = "Welcome!";

        String ticker = "ticker";
        //phase 2
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        //phase 3
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "M_CH_ID");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        Notification notification = builder.setContentIntent(pendingIntent).setTicker(ticker).setAutoCancel(true).setContentTitle(title).setSmallIcon(android.R.drawable.star_on).setContentText("").setOnlyAlertOnce(true).build();


        return notification;

    }

}
