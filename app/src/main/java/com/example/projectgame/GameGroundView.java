package com.example.projectgame;

import android.content.Context;
import android.view.SurfaceView;

public class GameGroundView extends SurfaceView implements Runnable {

    private boolean isPlaying ;




    public GameGroundView(Context context) {
        super(context);
    }

    @Override
    public void run() {

        while (isPlaying) {
            update();
            draw();
            sleep();
        }

    }

    public void update() {

    }

    public void draw() {

    }

    public void sleep() {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
