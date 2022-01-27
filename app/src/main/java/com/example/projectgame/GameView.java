package com.example.projectgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1, background2;
    private final int screenX, screenY;
    private Paint paint;
    private Flight flight;
    public static float screenRatioX, screenRatioY;  // they are public and static so i will be able to use them in different classes
    private List<Bullet> bullets;


    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;  // the screen width
        this.screenY = screenY;  // the screen height
        this.screenRatioX = screenX / 1920f;
        this.screenRatioY = screenY / 1080f;

        this.background1 = new Background(screenX, screenY, getResources());
        this.background2 = new Background(screenX, screenY, getResources());

        this.flight = new Flight(this, screenY, getResources());

        this.background2.x = screenX - 1;  // on the start, the second background will wait out of the screen

        this.paint = new Paint();

        bullets = new ArrayList<>();


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
        this.background1.x -= step;   // to move the image to the left some (the basic is 9, in my phone) pixels
        this.background2.x -= step;

        if (this.background1.x + this.background1.background.getWidth() < 0)  // if the size of the picture would be called "x" (this.background1.background.getWidth()) so if the left side -> x coordinate would be "-x" the picture would be exactly out of the screen, when it's below "-x" the picture would be brought to the right side of the screen -> out of the screen
            this.background1.x = this.background2.x + this.background2.background.getWidth();
        // to bring the image to the right side of the screen -> outside of the screen
        if (this.background2.x + this.background2.background.getWidth() < 0)
            this.background2.x = this.background1.x + this.background1.background.getWidth();

        if (this.flight.isGoingUp)
            this.flight.y -= (int) (20 * this.screenRatioY);  // to make the airplane go up
        else
            this.flight.y += (int) (20 * this.screenRatioY);  // to make the airplane go down

        if (this.flight.y < 0)
            this.flight.y = 0;  // to stop the airplane from getting out of the screen

        if (this.flight.y >= this.screenY - this.flight.height)
            this.flight.y = this.screenY - this.flight.height;  // to stop the airplane from getting out of the screen

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.x > screenX)
                trash.add(bullet);
            bullet.x += 50 * screenRatioX;  // to make the bullet move on the screen
        }

        for(Bullet bullet : trash){
            bullets.remove(bullet);
        }


    }

    public void draw() {
        if (getHolder().getSurface().isValid()) {   // to make sure that the surface is available for use
            Canvas canvas = getHolder().lockCanvas();   // returns the current canvas that is being displayed on the screen to work with
            canvas.drawBitmap(this.background1.background, this.background1.x, this.background1.y, this.paint);   // the x and the y are the top left coordinates of the image
            canvas.drawBitmap(this.background2.background, this.background2.x, this.background2.y, this.paint);
            canvas.drawBitmap(this.flight.getFlight(), this.flight.x, this.flight.y, this.paint);

            for(Bullet bullet : bullets){
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }


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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {  // to check what happened, in this case to check whether the user is clicking on the screen (MotionEvent.ACTION_DOWN) or he released his finger from the screen (MotionEvent.ACTION_UP) so i will be able to act accordingly

            case MotionEvent.ACTION_DOWN:  // ACTION_DOWN is since the user touched the screen until he release his finger from the screen
                if (event.getX() < screenX / 2)  // if the user touches the left side of the screen the airplane would go up
                    this.flight.isGoingUp = true;

                break;

            case MotionEvent.ACTION_UP:
                this.flight.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    this.flight.toShoot++;
                break;


        }

        return true;
    }

    public void newBullet() {

        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;  // initially, the bullet will be next to the airplane
        bullet.y = flight.y + (flight.height / 2);  // initially, it will be next to the guns on the wings of the airplane
        bullets.add(bullet);

    }
}
