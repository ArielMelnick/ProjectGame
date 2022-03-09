package com.example.projectgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameGroundView extends SurfaceView implements Runnable {

    private boolean isPlaying, isGameOver = false;
    private Thread thread;
    private final int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private GroundBackground background1, background2;
    private Paint paint;
    private Robot robot;
    private List<Bullet> bullets;
    private Spikes spikes;
    private GameGroundActivity activity;
    private Dino dino;
    private int score = 0;


    public GameGroundView(GameGroundActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

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

        spikes = new Spikes(getResources());

        dino = new Dino(getResources());


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


        if (robot.toJump && robot.y > 280)
            robot.y -= (int) (25 * screenRatioY);
        else {
            robot.y += (int) (35 * this.screenRatioY);
            robot.toJump = false;
        }
        if (robot.y > robot.defaultY)
            robot.y = robot.defaultY;


        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.x > screenX)
                trash.add(bullet);
            bullet.x += 50 * screenRatioX;

            if (Rect.intersects(dino.getCollisionShape(), bullet.getCollisionShape())) {
                score++;
                dino.x = -600;
                bullet.x = screenX + 500;
                dino.wasShot = true;
            }
        }

        for (Bullet bullet : trash) {
            bullets.remove(bullet);
        }

        dino.x -= dino.speed;
        if (dino.x + dino.width < 0) {

            if (!dino.wasShot) {
                isGameOver = true;
                return;
            }

            dino.speed = (int) (Math.random() * (40 * screenRatioX) + 30 * screenRatioX);
            dino.x = (int) ((Math.random() * (400) + screenX + 100) * screenRatioX);
            dino.y = (int) ((665 * screenRatioY) - (90 * screenRatioX));
            dino.wasShot = false;
        }


        spikes.x -= spikes.speed;
        if (spikes.x + spikes.width < 0) {

            spikes.x = (int) ((Math.random() * (400) + screenX + 100) * screenRatioX);
            spikes.y = (int) ((665 * screenRatioY) + 170 * screenRatioX);
            spikes.speed = (int) (22 * screenRatioX);

        }


        if (Rect.intersects(dino.getCollisionShape(), robot.getCollisionShape())) {
            isGameOver = true;
            robot.y = robot.defaultY + 15;
            return;
        }


        if (Rect.intersects(spikes.getCollisionShape(), robot.getCollisionShape())) {
            isGameOver = true;
            robot.y = robot.defaultY + 15;
            return;
        }


    }

    public void updateBackground() {

        int step = (int) (22 * screenRatioX);
        background1.x -= step;
        background2.x -= step;

        if (background1.x + background1.background.getWidth() < 0)
            background1.x = background2.x + background2.background.getWidth() - 4;

        if (background2.x + background2.background.getWidth() < 0)
            background2.x = background1.x + background1.background.getWidth() - 4;

    }


    public void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(spikes.getSpikes(), spikes.x, spikes.y, paint);

            if (isGameOver) {

                robot.toDie = true;
                canvas.drawBitmap(robot.getRobot(), robot.x, robot.y, paint);
                isPlaying = false;
                paint.setColor(Color.RED);
                paint.setTextSize(130);
                canvas.drawText("Game Over", (screenX / 2f) - 260, screenY / 2f, paint);

                getHolder().unlockCanvasAndPost(canvas);

                waitBeforeExiting();

                return;
            }

            canvas.drawBitmap(dino.getDino(), dino.x, dino.y, paint);

            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }


            canvas.drawBitmap(robot.getRobot(), robot.x, robot.y, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }


    }


    public void sleep() {

        try {
            Thread.sleep(17);
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
                if (event.getX() < screenX / 2) {
                    if (robot.y == robot.defaultY)
                        robot.toJump = true;
                } else
                    newBullet();
                break;
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = robot.x + robot.averageWidth;
        bullet.y = (int) (robot.y + robot.averageHeight / 2);
        bullets.add(bullet);

    }

    public void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
