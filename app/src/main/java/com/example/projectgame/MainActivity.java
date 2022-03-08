package com.example.projectgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private boolean isMute;
    private ImageView volume;
    private TextView tvScore;
    private SharedPreferences sp;
    private Dialog instructionsDialog;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        tvScore = findViewById(R.id.highScoreTxt);
        sp = getSharedPreferences("game", MODE_PRIVATE);
        tvScore.setText("High score: " + sp.getInt("highScore", 0));

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
                startActivity(new Intent(MainActivity.this, GameActivity.class)); // to start gameActivity and through that to show GameView (like MainActivity and activity_main)
            }
        });

        findViewById(R.id.playOnGround).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameGroundActivity.class)); // to start gameActivity and through that to show GameView (like MainActivity and activity_main)
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
        tvScore.setText("High score: " + sp.getInt("highScore", 0));

    }
}