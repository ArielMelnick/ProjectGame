package com.example.projectgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;

public class GroundGameActivity extends AppCompatActivity {

    private GroundGameView groundGameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        groundGameView = new GroundGameView(this, point.x, point.y);

        setContentView(groundGameView);


    }

    protected void onPause() {   // if another activity comes into the foreground onPause is called
        super.onPause();
        groundGameView.pause();
    }

    @Override
    protected void onResume() {   // if it comes back from another activity to this activity then onResume will be called
        super.onResume();
        groundGameView.resume();


    }
}
