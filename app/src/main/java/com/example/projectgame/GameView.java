package com.example.projectgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1, background2;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;


    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX; // the screen width
        this.screenY = screenY; // the screen height
        screenRatioX = screenX / 1920f;
        screenRatioY = screenY / 1080f;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        background2.x = screenX;   // on the start, the second background will wait out of the screen

        paint = new Paint();


    }

    @Override
    public void run() {
        while (isPlaying) {

            update();
            draw();
            sleep();
        }

    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();   // calls - run()

    }

    public void pause() {
        isPlaying = false;

        try {
            thread.join();   // closing the thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        int step = (int) (10 * screenRatioX);
        background1.x -= step;   // to move the image to the left some (the basic is 10, in my phone) pixels
        background2.x -= step;

        if (background1.x + background1.background.getWidth() < 0)
            background1.x = screenX;
        // to bring the image to the right - outside of the screen
        if (background2.x + background2.background.getWidth() < 0)
            background2.x = screenX;
    }

    public void draw() {
        if (getHolder().getSurface().isValid()) {   // to make sure that the surface is available for use
            Canvas canvas = getHolder().lockCanvas();   // returns the current canvas that is being displayed on the screen
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);   // the x and the y are the top left coordinates of the image
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            getHolder().unlockCanvasAndPost(canvas);   // to show the canvas on the screen

        }

    }

    public void sleep() {   // the image will change about 60 time per second (60 fps - 60 frames per second)
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
