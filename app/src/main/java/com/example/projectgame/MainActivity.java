package com.example.projectgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isMute;
    private ImageView volume;
    private TextView tvHeavensHighScore;
    private TextView tvGroundHighScore;
    private SharedPreferences sp;
    private Dialog instructionsDialog;
    private SharedPreferences.Editor editor;
    private MyBroadcastReceiver mbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        tvHeavensHighScore = findViewById(R.id.tvHeavensHighScore);
        tvGroundHighScore = findViewById(R.id.tvGroundHighScore);

        sp = getSharedPreferences("game", MODE_PRIVATE);


        isMute = sp.getBoolean("isMute", false);

        volume = findViewById(R.id.volume);

        if (isMute)  // I'm putting those "if" statements also in here so if the player would return to the game after he closed it, it will just like it was when he exited the game.
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

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Contact) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0546306568"));
            startActivity(intent);
        }
        if (id == R.id.Instructions) {
            createInstructionsDialog();

        }
        return true;

    }

    public void createInstructionsDialog() {

        instructionsDialog = new Dialog(this);

        instructionsDialog.setContentView(R.layout.instructions_dialog_layout);
        instructionsDialog.setTitle("Instructions");
        instructionsDialog.setCancelable(true);

        instructionsDialog.show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        tvHeavensHighScore.setText("Heavens High Score: " + sp.getInt("HeavensHighScore", 0));
        tvGroundHighScore.setText("Ground High Score: " + sp.getInt("GroundHighScore", 0));

        mbr = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mbr, filter);


    }



    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mbr);
    }
}