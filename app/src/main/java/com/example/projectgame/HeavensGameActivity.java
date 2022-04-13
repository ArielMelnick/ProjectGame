package com.example.projectgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;

public class HeavensGameActivity extends AppCompatActivity {

    private HeavensGameView heavensGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point point = new Point();


        getWindowManager().getDefaultDisplay().getSize(point);

        this.heavensGameView = new HeavensGameView(this, point.x, point.y); // sending the screen size to HeavensGameView


        setContentView(this.heavensGameView);

    }

    protected void onPause() {   // if another activity comes into the foreground onPause is called
        super.onPause();
        this.heavensGameView.pause();
    }

    @Override
    protected void onResume() {   // if it comes back from another activity to this activity then onResume will be called
        super.onResume();
        this.heavensGameView.resume();


    }
}