package com.example.projectgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;


public class GroundGameView extends SurfaceView implements Runnable {

    private boolean isPlaying, isGameOver = false;
    private Thread thread;
    private final int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private GroundBackground background1;
    private GroundBackground background2;
    private Paint paint;
    private Robot robot;
    private List<Bullet> bullets;
    private Spikes spikes;
    private GroundGameActivity activity;
    private Dino dino;
    private int score = 0;
    private SharedPreferences sp;
    private MediaPlayer mp;
    private double increaseSpeed;
    private SharedPreferences.Editor edit;

    /**
     * To create a game
     */
    public GroundGameView(GroundGameActivity activity, int screenX, int screenY) {  // initiating and setting objects and variables to use later in the other methods.
        super(activity);

        this.activity = activity;

        sp = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        edit = sp.edit();

        edit.putInt("timesPlayedGround", sp.getInt("timesPlayedGround", 0) + 1);
        edit.apply();

        mp = MediaPlayer.create(activity, R.raw.shoot);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = screenX / 2148f;
        screenRatioY = screenY / 1080f;

        background1 = new GroundBackground(screenX, screenY, getResources());
        background2 = new GroundBackground(screenX, screenY, getResources());

        this.background2.x = screenX - 1;

        robot = new Robot(this, getResources());

        this.paint = new Paint();
        this.paint.setTextSize(130);  //  I'm using "paint" to show the score
        this.paint.setColor(Color.WHITE);

        bullets = new ArrayList<>();

        spikes = new Spikes(getResources());

        dino = new Dino(getResources());

    }

    @Override
    public void run() {  // Basically from here it all operates - updating the game, drawing according to those updates and waiting a little bit - all in a while loop that stays active as long as the player didn't lose or quite the game .

        while (isPlaying) {
            update();
            draw();
            sleep();
        }

    }

    public void update() {  // updating the display according to the player's actions.

        updateBackground();

        updateRobot();

        updateDinoAndBullets();

        updateSpikes();

        collisionCheck();


        if (increaseSpeed < 1000)
            increaseSpeed += 0.01;
        else
            increaseSpeed = 1000;

    }

    public void updateBackground() {  // updating the background of the game - moving it towards the left side of the screen.

        int step = (int) (22 * screenRatioX) + (int) increaseSpeed;
        background1.x -= step;
        background2.x -= step;

        if (background1.x + background1.background.getWidth() < 0)
            background1.x = background2.x + background2.background.getWidth() - 4;

        if (background2.x + background2.background.getWidth() < 0)
            background2.x = background1.x + background1.background.getWidth() - 4;

    }

    public void updateRobot() {  // updating the robot image data - the jumping process.

        if (robot.toJump && robot.y > 280)
            robot.y -= (int) (25 * screenRatioY);
        else {
            robot.y += (int) (35 * screenRatioY);
            robot.toJump = false;
        }
        if (robot.y > robot.defaultY)
            robot.y = robot.defaultY;
    }

    public void updateDinoAndBullets() {  // updating the dino image - moving it towards the robot, and the bullets images - moving them towards the dinos. and checking collision between them.
        List<Bullet> trash = new ArrayList<>();


        for (Bullet bullet : bullets) {
            if (bullet.x > screenX)
                trash.add(bullet);
            bullet.x += (50 * screenRatioX);

            if (dino.x < screenX - 400 && dino.x > 0) {  // So the dino won't be shot outside of the screen
                if (Rect.intersects(dino.getCollisionShape(), bullet.getCollisionShape())) {
                    score++;
                    dino.x = -600;
                    bullet.x = screenX + 500;
                    dino.wasShot = true;

                    if (sp.getInt("dinosKilled", 0) < 999999999)
                        edit.putInt("dinosKilled", sp.getInt("dinosKilled", 0) + 1);

                    edit.apply();
                }
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

            dino.speed = (int) (Math.random() * (40 * screenRatioX) + (30 * screenRatioX) + (int) increaseSpeed);
            dino.x = (int) ((Math.random() * (900) + screenX + 100) * screenRatioX);
            dino.y = (int) ((665 * screenRatioY) - (90 * screenRatioY));
            dino.wasShot = false;
        }
    }

    public void updateSpikes() {  // updating the spikes image - moving it towards the robot (to the left).

        spikes.x -= spikes.speed;
        if (spikes.x + spikes.width < 0) {

            spikes.x = (int) ((Math.random() * (400) + screenX + 100) * screenRatioX);
            spikes.y = (int) ((665 * screenRatioY) + 150 * screenRatioY);
            spikes.speed = (int) (22 * screenRatioX) + (int) increaseSpeed;

        }
    }


    public void collisionCheck() {  // Checking if there was a collision between the robot and the and between the robot and the spikes.

        if (Rect.intersects(dino.getCollisionShape(), robot.getCollisionShape())) {
            isGameOver = true;
            robot.y = robot.defaultY + (int) (15 * screenRatioY);
            edit.putInt("deathsByDino", sp.getInt("deathsByDino", 0) + 1);
            edit.apply();
            return;
        }


        if (Rect.intersects(spikes.getCollisionShape(), robot.getCollisionShape())) {
            isGameOver = true;
            robot.y = robot.defaultY + (int) (15 * screenRatioY);
            edit.putInt("deathsBySpikes", sp.getInt("deathsBySpikes", 0) + 1);
            edit.apply();
            return;
        }
    }


    public void draw() {  // drawing the changes on the screen according to the update method and checking if the player lost.

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            paint.setColor(Color.parseColor("#A52A2A"));
            canvas.drawText(score + "", screenX / 2f, 160 * screenRatioY, paint);  // draw the score of the player

            canvas.drawBitmap(spikes.getSpikes(), spikes.x, spikes.y, paint);

            if (isGameOver) {

                isPlaying = false;

                mp.stop();

                robot.toDie = true;

                canvas.drawBitmap(robot.getRobot(), robot.x, robot.y, paint);

                saveIfHighScore();

                paint.setColor(Color.RED);
                paint.setTextSize(130);

                canvas.drawText("Game Over", (screenX / 2f) - 260, screenY / 2f, paint);

                getHolder().unlockCanvasAndPost(canvas);

                waitBeforeExiting();

                return;
            }

            canvas.drawBitmap(dino.getDino(), dino.x, dino.y, paint);

            canvas.drawBitmap(robot.getRobot(), robot.x, robot.y, paint);

            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);

        }

    }


    public void sleep() {  // sleeping between each iteration of the while loop in run to make the game playable and somewhat "fun".

        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {  // starting the thread (the "run()" method).
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {  // stopping the run method and the thread.
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean onTouchEvent(MotionEvent event) {  // listening to the player's touches on the screen and acting accordingly.

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() < screenX / 2f) {
                if (robot.y == robot.defaultY)
                    robot.toJump = true;
            } else if (dino.x < screenX) // or: if (!isGameOver && dino.x < screenX) with newBullet(); from here. (Concurrent modification exception)

                robot.toShoot = true;
        }
        return true;
    }

    public void newBullet() {  // creating a new bullet and putting it into the bullet list and making the shooting sound.

        if (!sp.getBoolean("isMute", false))  // If the player chose to not mute the game then I will play the bullet sound.
            mp.start();  // To make the sound of the shooting.

        Bullet bullet = new Bullet(getResources());
        bullet.x = robot.x + robot.averageWidth;
        bullet.y = (int) ((robot.y + robot.averageHeight / 2) - 30 * screenRatioY);
        bullets.add(bullet);

    }


    public void waitBeforeExiting() {  // waiting a little bit before returning to the main screen - to let the player see the robot dead and the "Game Over" text.

        try {
            activity.finish();
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveIfHighScore() {  // to save the player's score in the memory if it was higher than the last high score stored in the memory.

        if (sp.getInt("GroundHighScore", 0) < score) {

            edit.putInt("GroundHighScore", score);  // creating/changing an int variant named "highScore" inside "game.xml" that inside "shared_prefs" directory
            edit.apply();  // To create/change the high score

        }
    }

}
