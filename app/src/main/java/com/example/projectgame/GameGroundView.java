package com.example.projectgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameGroundView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private Thread thread;
    private final int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private GroundBackground background1, background2;
    private Paint paint;
    private Robot robot;
    private List<Bullet> bullets;
    private Zombie[] zombies;


    public GameGroundView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        this.screenRatioX = screenX / 2148f;
        this.screenRatioY = screenY / 1080f;

        background1 = new GroundBackground(screenX, screenY, getResources());
        background2 = new GroundBackground(screenX, screenY, getResources());

        this.background2.x = screenX - 1;

        robot = new Robot(getResources());

        this.paint = new Paint();

        bullets = new ArrayList<>();

        zombies[0] = new Zombie(getResources());
        zombies[1] = new Zombie(getResources());
        zombies[2] = new Zombie(getResources());




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

        updateBackground();




    }

    public void updateBackground() {

        int step = (int) (10 * screenRatioX);
        background1.x -= step;   // to move the image to the left some (the basic is 9, in my phone) pixels
        background2.x -= step;

        if (background1.x + background1.background.getWidth() < 0)  // if the size of the picture would be called "x" (this.background1.background.getWidth()) so if the left side -> x coordinate would be "-x" the picture would be exactly out of the screen, when it's below "-x" the picture would be brought to the right side of the screen -> out of the screen
            background1.x = background2.x + background2.background.getWidth() - 4;
        // to bring the image to the right side of the screen -> outside of the screen
        if (background2.x + background2.background.getWidth() < 0)
            background2.x = background1.x + background1.background.getWidth() - 4;

    }


    public void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);


            canvas.drawBitmap(robot.getRobot(), robot.x, robot.y, paint);


            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    public void sleep() {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:
                if (event.getX() < screenX / 2)
                    robot.toJump = true;
                else
                    newBullet();
                break;
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());

    }
}
