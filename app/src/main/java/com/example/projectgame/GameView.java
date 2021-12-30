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
        this.screenRatioX = screenX / 1920f;
        this.screenRatioY = screenY / 1080f;

        this.background1 = new Background(screenX, screenY, getResources());
        this.background2 = new Background(screenX, screenY, getResources());

        this.background2.x = screenX;   // on the start, the second background will wait out of the screen

        this.paint = new Paint();


    }

    @Override
    public void run() {
        while (this.isPlaying) {

            update();
            draw();
            sleep();
        }

    }

    public void resume() {
        this.isPlaying = true;
        this.thread = new Thread(this);
        this.thread.start();   // calls - run()

    }

    public void pause() {
        this.isPlaying = false;

        try {
            this.thread.join();   // closing the thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        int step = (int) (10 * this.screenRatioX);
        this.background1.x -= step;   // to move the image to the left some (the basic is 10, in my phone) pixels
        this.background2.x -= step;

        if (this.background1.x + this.background1.background.getWidth() < 0)
            this.background1.x = this.screenX;
        // to bring the image to the right - outside of the screen
        if (this.background2.x + this.background2.background.getWidth() < 0)
            this.background2.x = this.screenX;
    }

    public void draw() {
        if (getHolder().getSurface().isValid()) {   // to make sure that the surface is available for use
            Canvas canvas = getHolder().lockCanvas();   // returns the current canvas that is being displayed on the screen
            canvas.drawBitmap(this.background1.background, this.background1.x, this.background1.y, this.paint);   // the x and the y are the top left coordinates of the image
            canvas.drawBitmap(this.background2.background, this.background2.x, this.background2.y, this.paint);

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
