package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Robot {

    Bitmap[] run = new Bitmap[10];
    Bitmap dead;
    Bitmap[] jump = new Bitmap[11];
    int x, y, defaultY;
    int widthRun, widthDead, widthJump, heightRun, heightDead, heightJump, averageWidth, averageHeight;
    boolean toShoot = false;
    private GroundGameView ggv;

    int runCounter = 0;
    int jumpCounter = 0;
    Boolean toJump = false, toDie = false;


    Robot(GroundGameView ggv, Resources res) {
        this.ggv = ggv;

        decodeImages(res);

        setWidthAndHeight();

        createScaledBitmaps();

        x = (int) (64 * GroundGameView.screenRatioX);
        y = (int) (665 * GroundGameView.screenRatioY);
        defaultY = y;

    }

    public void decodeImages(Resources res) {

        run[1] = BitmapFactory.decodeResource(res, R.drawable.robot_run_1);
        run[2] = BitmapFactory.decodeResource(res, R.drawable.robot_run_2);
        run[3] = BitmapFactory.decodeResource(res, R.drawable.robot_run_3);
        run[4] = BitmapFactory.decodeResource(res, R.drawable.robot_run_4);
        run[5] = BitmapFactory.decodeResource(res, R.drawable.robot_run_5);
        run[6] = BitmapFactory.decodeResource(res, R.drawable.robot_run_6);
        run[7] = BitmapFactory.decodeResource(res, R.drawable.robot_run_7);
        run[8] = BitmapFactory.decodeResource(res, R.drawable.robot_run_8);
        run[9] = BitmapFactory.decodeResource(res, R.drawable.robot_run_9);


        jump[1] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_1);
        jump[2] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_2);
        jump[3] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_3);
        jump[4] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_4);
        jump[5] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_5);
        jump[6] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_6);
        jump[7] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_7);
        jump[8] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_8);
        jump[9] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_9);
        jump[10] = BitmapFactory.decodeResource(res, R.drawable.robot_jump_10);

        dead = BitmapFactory.decodeResource(res, R.drawable.robot_dead_10);
    }


    public void createScaledBitmaps() {

        for (int i = 1; i < 10; i++) {
            run[i] = Bitmap.createScaledBitmap(run[i], widthRun, heightRun, false);
            jump[i] = Bitmap.createScaledBitmap(jump[i], widthJump, heightJump, false);
        }

        jump[10] = Bitmap.createScaledBitmap(jump[10], widthJump, heightJump, false);
        dead = Bitmap.createScaledBitmap(dead, widthDead, heightDead, false);
    }


    public void setWidthAndHeight() {

        widthRun = run[1].getWidth();
        widthDead = dead.getWidth();
        widthJump = jump[1].getWidth();

        heightRun = run[1].getHeight();
        heightDead = dead.getHeight();
        heightJump = jump[1].getHeight();

        widthRun /= 5;
        widthDead /= 5;
        widthJump /= 5;

        heightRun /= 5;
        heightDead /= 5;
        heightJump /= 5;

        widthRun *= GroundGameView.screenRatioX;
        widthDead *= GroundGameView.screenRatioX;
        widthJump *= GroundGameView.screenRatioX;

        heightRun *= GroundGameView.screenRatioY;
        heightDead *= GroundGameView.screenRatioY;
        heightJump *= GroundGameView.screenRatioY;

        averageWidth = (widthRun + widthDead + widthJump) / 3;
        averageHeight = (heightRun + heightDead + heightJump) / 3;


    }

    public Bitmap getRobot() {

        if (toDie) {
            toDie = false;
            return dead;
        }

        if (toJump) {

            jumpCounter++;

            if (jumpCounter == 10) {
                jumpCounter = 0;
                return jump[10];
            }

            if (toShoot) {
                toShoot = false;
                ggv.newBullet();
            }

            return jump[jumpCounter];

        }


        runCounter++;
        if (runCounter == 9) {
            runCounter = 0;
            return run[9];
        }

        if (toShoot) {
            toShoot = false;
            ggv.newBullet();
        }

        return run[runCounter];

    }

    public Rect getCollisionShape() {  // it will create a rectangle around the airplane and it will return this rectangle so I will be able to check if there is a collision between the airplane and a bird with "Rect.intersect()"
        return new Rect(x, y, x + averageWidth, y + averageHeight);
    }


}
