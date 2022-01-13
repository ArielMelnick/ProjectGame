package com.example.projectgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point point = new Point();


        getWindowManager().getDefaultDisplay().getSize(point);

        this.gameView = new GameView(this, point.x, point.y); // sending the screen size to GameView


        setContentView(this.gameView);

    }

    protected void onPause() {   // if another activity comes into the foreground onPause is called
        super.onPause();
        this.gameView.pause();
    }

    @Override
    protected void onResume() {   // if it comes back from another activity to this activity then onResume will be called
        super.onResume();
        this.gameView.resume();
    }
}