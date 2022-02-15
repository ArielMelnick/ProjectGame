package com.example.projectgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        TextView tvScore = findViewById(R.id.highScoreTxt);
        SharedPreferences sp = getSharedPreferences("game", MODE_PRIVATE);
        tvScore.setText("High score: " +sp.getInt("highScore", 0));




        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class)); // to start gameActivity and through that to show GameView (like MainActivity and activity_main)
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
        return true;

    }

}