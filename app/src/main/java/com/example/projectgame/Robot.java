package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Robot {

    Bitmap[] run = new Bitmap[10];
    Bitmap[] dead = new Bitmap[11];
    Bitmap[] jump = new Bitmap[11];
    int x, y;
    int widthRun, widthDead, widthJump, heightRun, heightDead, heightJump, averageWidth, averageHeight;

    private int runCounter = 0, deadCounter = 1, jumpCounter = 1;
    Boolean toJump = false, toDie = false;


    Robot(Resources res) {

        run[1] = BitmapFactory.decodeResource(res, R.drawable.robot_run_1);
        run[2] = BitmapFactory.decodeResource(res, R.drawable.robot_run_2);
        run[3] = BitmapFactory.decodeResource(res, R.drawable.robot_run_3);
        run[4] = BitmapFactory.decodeResource(res, R.drawable.robot_run_4);
        run[5] = BitmapFactory.decodeResource(res, R.drawable.robot_run_5);
        run[6] = BitmapFactory.decodeResource(res, R.drawable.robot_run_6);
        run[7] = BitmapFactory.decodeResource(res, R.drawable.robot_run_7);
        run[8] = BitmapFactory.decodeResource(res, R.drawable.robot_run_8);
        run[9] = BitmapFactory.decodeResource(res, R.drawable.robot_run_9);

        dead[1] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_1);
        dead[2] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_2);
        dead[3] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_3);
        dead[4] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_4);
        dead[5] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_5);
        dead[6] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_6);
        dead[7] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_7);
        dead[8] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_8);
        dead[9] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_9);
        dead[10] = BitmapFactory.decodeResource(res, R.drawable.robot_dead_10);

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

        setWidthAndHeight();

        for (int i = 1; i < 10; i++) {
            run[i] = Bitmap.createScaledBitmap(run[i], widthRun, heightRun, false);
            dead[i] = Bitmap.createScaledBitmap(dead[i], widthDead, heightDead, false);
            jump[i] = Bitmap.createScaledBitmap(jump[i], widthJump, heightJump, false);
        }

        dead[10] = Bitmap.createScaledBitmap(dead[10], widthDead, heightDead, false);
        jump[10] = Bitmap.createScaledBitmap(jump[10], widthJump, heightJump, false);


    }

    public void setWidthAndHeight() {

        widthRun = run[1].getWidth();
        widthDead = dead[1].getWidth();
        widthJump = jump[1].getWidth();

        heightRun = run[1].getHeight();
        heightDead = dead[1].getHeight();
        heightJump = jump[1].getHeight();

        widthRun /= 5;
        widthDead /= 5;
        widthJump /= 5;

        heightRun /= 5;
        heightDead /= 5;
        heightJump /= 5;

        widthRun *= GameView.screenRatioX;
        widthDead *= GameView.screenRatioX;
        widthJump *= GameView.screenRatioX;

        heightRun *= GameView.screenRatioY;
        heightDead *= GameView.screenRatioY;
        heightJump *= GameView.screenRatioY;

        averageWidth = (widthRun+widthDead+widthJump)/3;
        averageHeight = (heightRun+heightDead+heightJump)/3;

    }

    public Bitmap getRobot() {

        if (toJump) {
            jumpCounter++;

            if (jumpCounter == 10) {
                jumpCounter = 1;
                toJump = false;
                return jump[10];
            }

            return jump[jumpCounter];

        }

        if (toDie) {
            deadCounter++;

            if (deadCounter == 10) {
                deadCounter = 1;
                toDie = false;
                return dead[10];
            }
            return dead[deadCounter];
        }

        runCounter++;
        if(runCounter == 9){
            runCounter = 1;
            return run[9];
        }
        return run[runCounter];

    }

    public Rect getCollisionShape() {  // it will create a rectangle around the airplane and it will return this rectangle so I will be able to check if there is a collision between the airplane and a bird with "Rect.intersect()"
        return new Rect(x, y, x + averageWidth, y + averageHeight);
    }


}
