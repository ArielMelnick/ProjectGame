package com.example.projectgame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;


public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
            Toast.makeText(context,"you shouldn't connect your phone while you are playing, it is dangerous! " , Toast.LENGTH_LONG).show();





    }
}
