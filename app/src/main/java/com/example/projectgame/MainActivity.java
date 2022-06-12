package com.example.projectgame;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private boolean isMute;
    private ImageView volume;
    private TextView tvHeavensHighScore;
    private TextView tvGroundHighScore;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createNotificationChannel();

        init();

        setVolume();

        setPlayButtons();

        setNotificationAlarm();

    }

    public void setVolume() {  // setting the audio in the game(the gun shots)
        isMute = sp.getBoolean("isMute", false);

        volume = findViewById(R.id.volume);

        if (isMute)  // I'm putting those "if" statements also in here so if the player would return to the game after he closed it, it will be just like it was when he exited the game.
            volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
        else
            volume.setImageResource(R.drawable.ic_baseline_volume_up_24);


        volume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                isMute = !isMute;  // if is mute is true it will turn to be false and if false it will turn to be true

                if (isMute)
                    volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
                else
                    volume.setImageResource(R.drawable.ic_baseline_volume_up_24);

                editor = sp.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();

            }
        });
    }

    public void init() {  // initiating some variable and a SharedPreferences object

        tvHeavensHighScore = findViewById(R.id.tvHeavensHighScore);
        tvGroundHighScore = findViewById(R.id.tvGroundHighScore);

        sp = getSharedPreferences("game", MODE_PRIVATE);
    }

    public void setPlayButtons() {  // setting the two play buttons and listening to them (to clicks on them)


        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HeavensGameActivity.class)); // to start gameActivity and through that to show HeavensGameView (like MainActivity and activity_main)
            }
        });

        findViewById(R.id.playOnGround).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GroundGameActivity.class));
            }
        });

    }

    public void setNotificationAlarm() {  // settings an AlarmManager,Intent and PendingIntent to activate a broadcast to activate a notification

        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1000 * 60 * 60 * 24), pendingIntent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {  // creating the menu - inflating to it the layout I made

        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.gameMode).setChecked(sp.getBoolean("oneHandGameMode", false));
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {  // reacting to selected item from the menu
        int id = item.getItemId();

        if (id == R.id.Contact) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0546306568"));
            startActivity(intent);
        }

        if (id == R.id.Instructions)
            createInstructionsDialog();

        if (id == R.id.Statistics)
            createStatisticsDialog();

        if (id == R.id.gameMode) {

            boolean isChecked = !item.isChecked();
            item.setChecked(isChecked);
            editor = sp.edit();
            editor.putBoolean("oneHandGameMode", item.isChecked());
            editor.apply();

        }

        return true;

    }

    public void createInstructionsDialog() {  // creating the instructions dialog

        Dialog instructionsDialog = new Dialog(this);

        instructionsDialog.setContentView(R.layout.instructions_dialog_layout);
        instructionsDialog.setCancelable(true);

        instructionsDialog.show();

    }

    public void createStatisticsDialog() {  // creating the statistics dialog
        TextView timesPlayedGround, dinosKilled, deathsByDino, deathsBySpikes, timesPlayedHeavens, birdsKilled, birdPassFails, birdCrashes;
        Dialog statisticsDialog = new Dialog(this);
        statisticsDialog.setContentView(R.layout.statistics_dialog_layout);
        WindowManager.LayoutParams wmlp = new WindowManager.LayoutParams();
        wmlp.width = 1600;
        wmlp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        statisticsDialog.getWindow().setAttributes(wmlp);
        statisticsDialog.setCancelable(true);

        timesPlayedGround = statisticsDialog.findViewById(R.id.timesPlayedGround);
        dinosKilled = statisticsDialog.findViewById(R.id.dinosKilled);
        deathsByDino = statisticsDialog.findViewById(R.id.deathsByDino);
        deathsBySpikes = statisticsDialog.findViewById(R.id.deathsBySpikes);
        timesPlayedHeavens = statisticsDialog.findViewById(R.id.timesPlayedHeavens);
        birdsKilled = statisticsDialog.findViewById(R.id.birdsKilled);
        birdPassFails = statisticsDialog.findViewById(R.id.birdPassFails);
        birdCrashes = statisticsDialog.findViewById(R.id.birdCrashes);


        timesPlayedGround.setText("Times played ground :  " + sp.getInt("timesPlayedGround", 0));

        if (sp.getInt("dinosKilled", 0) == 999999999)
            dinosKilled.setText("Dinos killed : " + 999999999 + "+" + "\n you are playing to much!");
        else
            dinosKilled.setText("Dinos killed : " + sp.getInt("dinosKilled", 0));

        deathsByDino.setText("Deaths by dinos : " + sp.getInt("deathsByDino", 0));
        deathsBySpikes.setText("Deaths by spikes : " + sp.getInt("deathsBySpikes", 0));
        timesPlayedHeavens.setText("Times played heavens : " + sp.getInt("timesPlayedHeavens", 0));

        if (sp.getInt("birdsKilled", 0) == 999999999)
            dinosKilled.setText("Birds killed : " + 999999999 + "+" + "\n you are playing to much!");
        else
            birdsKilled.setText("Birds Killed : " + sp.getInt("birdsKilled", 0));

        birdPassFails.setText("Bird passed fails : " + sp.getInt("birdPassFails", 0));
        birdCrashes.setText("Bird Crashes fails : " + sp.getInt("birdCrashes", 0));

        statisticsDialog.show();

    }

    public void createNotificationChannel() {  // Creating NotificationChannel to use on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "myChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("myChannel", name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        tvHeavensHighScore.setText("Heavens High Score: " + sp.getInt("HeavensHighScore", 0));
        tvGroundHighScore.setText("Ground High Score: " + sp.getInt("GroundHighScore", 0));

    }

}