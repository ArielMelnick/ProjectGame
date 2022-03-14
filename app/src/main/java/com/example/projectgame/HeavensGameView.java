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
import java.util.Random;

public class HeavensGameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private Background background1;
    private Background background2;
    private int screenX, screenY;
    private int score;
    private Paint paint;
    private Flight flight;
    public static float screenRatioX, screenRatioY;  // they are public and static so i will be able to use them in different classes
    private List<Bullet> bullets;
    private Bird[] birds;
    private Random random;
    private SharedPreferences sp;  // I'm using "SharedPreferences" to store the highest score
    private HeavensGameActivity activity;
    private MediaPlayer mp;

    public HeavensGameView(HeavensGameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        sp = activity.getSharedPreferences("game", Context.MODE_PRIVATE);  // connecting to the xml file named "game" in SharedPreferences that i created in "MainActivity".

        mp = MediaPlayer.create(activity, R.raw.shoot);

        this.screenX = screenX;  // the screen width
        this.screenY = screenY;  // the screen height
        screenRatioX = screenX / 2148f;
        screenRatioY = screenY / 1080f;

        this.background1 = new Background(screenX, screenY, getResources());
        this.background2 = new Background(screenX, screenY, getResources());

        this.flight = new Flight(this, getResources());

        this.background2.x = screenX - 1;  // on the start, the second background will wait out of the screen

        this.paint = new Paint();
        this.paint.setTextSize(130);  //  I'm using "paint" to show the score
        this.paint.setColor(Color.WHITE);

        bullets = new ArrayList<>();

        birds = new Bird[4];

        for (int i = 0; i < 4; i++) {
            Bird bird = new Bird(getResources());
            birds[i] = bird;
        }

        random = new Random();

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

        updateBackground();
        updateFlight();

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {   // I'm using list because there could be many bullets on the screen at once
            if (bullet.x > screenX)  // to check whether the bullet is out of the screen or not
                trash.add(bullet);   // to delete the bullets that came out of the screen from the list - afterwards - so I won't do the line bellow on a null
            bullet.x += 50 * screenRatioX;  // to make the bullet move on the screen

            for (Bird bird : birds) {

                if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())) {  // to check if a bullet hit a bird
                    score++;
                    bird.x = -500;  // so the bird will go out of the screen, to the left
                    bullet.x = screenX + 500;  // so the bullet will be out of the screen
                    bird.wasShot = true;
                }
            }
        }

        for (Bullet bullet : trash) {  // deleting the bullets that came out of the screen
            bullets.remove(bullet);
        }

        for (Bird bird : birds) {
            bird.x -= bird.speed;  //  so the bird will go towards the airplane -> to the left in a random speed

            if ((bird.x + bird.width) < 0) {  // if this statement is true -> the bird got out of the screen from the left side

                if (!(bird.wasShot)) {  // if the bird got out of the screen without getting shoot at so the player had failed and the game will be over
                    isGameOver = true;
                    return;
                }

                bird.speed = (int) (Math.random() * (20 * screenRatioX) + 10 * screenRatioX);

                bird.x = screenX;  // so the bird will be on the right side of the screen
                bird.y = random.nextInt(screenY - bird.height);  // so the maximum height will be (screenY - bird.height)

                bird.wasShot = false;
            }

            if (Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())) {  // to check if the bird hit the airplane to end the game
                isGameOver = true;
                return;

            }
        }
    }

    public void updateBackground() {

        int step = (int) (15 * screenRatioX);
        this.background1.x -= step;   // to move the image to the left some (the basic is 9, in my phone) pixels
        this.background2.x -= step;

        if (this.background1.x + this.background1.background.getWidth() < 0)  // if the size of the picture would be called "x" (this.background1.background.getWidth()) so if the left side -> x coordinate would be "-x" the picture would be exactly out of the screen, when it's below "-x" the picture would be brought to the right side of the screen -> out of the screen
            this.background1.x = this.background2.x + this.background2.background.getWidth() - 4;
        // to bring the image to the right side of the screen -> outside of the screen
        if (this.background2.x + this.background2.background.getWidth() < 0)
            this.background2.x = this.background1.x + this.background1.background.getWidth() - 4;


    }

    public void updateFlight() {

        if (this.flight.isGoingUp)
            this.flight.y -= (int) (20 * screenRatioY);  // to make the airplane go up
        else
            this.flight.y += (int) (20 * screenRatioY);  // to make the airplane go down

        if (this.flight.y < 0)
            this.flight.y = 0;  // to stop the airplane from getting out of the screen

        if (this.flight.y >= this.screenY - this.flight.height)
            this.flight.y = this.screenY - this.flight.height;  // to stop the airplane from getting out of the screen

    }

    public void draw() {
        if (getHolder().getSurface().isValid()) {   // to make sure that the surface is available for use
            Canvas canvas = getHolder().lockCanvas();   // returns the current canvas that is being displayed on the screen to work with

            canvas.drawBitmap(this.background1.background, this.background1.x, this.background1.y, this.paint);   // the x and the y are the top left coordinates of the image
            canvas.drawBitmap(this.background2.background, this.background2.x, this.background2.y, this.paint);

            canvas.drawText(score + "", screenX / 2f, 160 * screenRatioY, paint);  // draw the score of the player

            if (isGameOver) {
                isPlaying = false; // it will stop the game

                mp.stop();

                canvas.drawBitmap(flight.dead, flight.x, flight.y, paint);  // so it will draw the image of the destroyed airplane

                saveIfHighScore();  // to store the high score

                paint.setColor(Color.RED);
                canvas.drawText("Game Over", (screenX / 2f) - 260, screenY / 2f, paint);

                getHolder().unlockCanvasAndPost(canvas);

                waitBeforeExiting();

                return;
            }
            for (Bird bird : birds) {
                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
            }

            canvas.drawBitmap(this.flight.getFlight(), this.flight.x, this.flight.y, this.paint);

            for (Bullet bullet : bullets) {  // to draw many bullets at once
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);   // to show the canvas on the screen

        }
    }


    public void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void sleep() {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {  // to check what happened, in this case to check whether the user is clicking on the screen (MotionEvent.ACTION_DOWN) or he released his finger from the screen (MotionEvent.ACTION_UP) so i will be able to act accordingly

            case MotionEvent.ACTION_DOWN:  // ACTION_DOWN is since the user touched the screen until he release his finger from the screen
                if (event.getX() < screenX / 2)  // if the user touches the left side of the screen the airplane would go up
                    this.flight.isGoingUp = true;  // used in "update()"
                break;

            case MotionEvent.ACTION_UP:
                this.flight.isGoingUp = false;  // used in "update()"
                if (event.getX() > screenX / 2) {  // if the user touches the right side of the screen the airplane would shoot
                    this.flight.toShoot++;  // "toShoot" used to check whether "getFlight" from "Flight" should return an image from one block of code or from the other
                    //  if i will call "newBullet()" here then the bullet would show up together with the first image of the airplane shooting and if it is there it would show up on the screen only after the 5 images of the airplane shooting would be presented on the screen already
                }
                break;
        }

        return true;
    }

    public void newBullet() {  // gets called in "getFlight()" in "Flight" class.

        if (!sp.getBoolean("isMute", false))  // If the player chose to not mute the game then I will play the bullet sound.
            mp.start();  // To make the sound of the shooting.

        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;  // Initially, the bullet will be next to the airplane
        bullet.y = flight.y + (flight.height / 2);  // Initially, it will be next to the guns on the wings of the airplane
        bullets.add(bullet);

    }

    public void saveIfHighScore() {

        if (sp.getInt("HeavensHighScore", 0) < score) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt("HeavensHighScore", score);  // creating/changing an int variant named "highScore" inside "game.xml" that inside "shared_prefs" directory
            edit.apply();  // To create/change the high score

        }
    }


}
