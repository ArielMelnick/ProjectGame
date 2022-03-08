package com.example.projectgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;

public class GameGroundActivity extends AppCompatActivity {

    private GameGroundView gameGroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameGroundView = new GameGroundView(this, point.x, point.y);

        setContentView(gameGroundView);


    }

    protected void onPause() {   // if another activity comes into the foreground onPause is called
        super.onPause();
        gameGroundView.pause();
    }

    @Override
    protected void onResume() {   // if it comes back from another activity to this activity then onResume will be called
        super.onResume();
        gameGroundView.resume();
    }
}
