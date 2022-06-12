package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Dino {

    int x, y; // the coordinates of the dino on the screen
    int width, height;
    private int counter = 1;
    int speed = 20;
    Bitmap dino1, dino2, dino3, dino4, dino5, dino6, dino7, dino8;  // the stages of the running dino
    boolean wasShot = true; // to check whether the dino got shot(and thus got out of the screen) or he got out of the screen without getting shot

    Dino(Resources res) {

        decodeImages(res);

        setWidthAndHeight();

        createScaledBitmaps();

        x = 0;
        y = -height;

    }

    public void decodeImages(Resources res) {

        dino1 = BitmapFactory.decodeResource(res, R.drawable.dino_run_1);
        dino2 = BitmapFactory.decodeResource(res, R.drawable.dino_run_2);
        dino3 = BitmapFactory.decodeResource(res, R.drawable.dino_run_3);
        dino4 = BitmapFactory.decodeResource(res, R.drawable.dino_run_4);
        dino5 = BitmapFactory.decodeResource(res, R.drawable.dino_run_5);
        dino6 = BitmapFactory.decodeResource(res, R.drawable.dino_run_6);
        dino7 = BitmapFactory.decodeResource(res, R.drawable.dino_run_7);
        dino8 = BitmapFactory.decodeResource(res, R.drawable.dino_run_8);

    }

    public void setWidthAndHeight() {

        width = dino1.getWidth();
        height = dino1.getHeight();

        width /= 3;
        height /= 3;

        width *= GroundGameView.screenRatioX;
        height *= GroundGameView.screenRatioY;

    }

    public void createScaledBitmaps() {

        dino1 = Bitmap.createScaledBitmap(dino1, width, height, false);
        dino2 = Bitmap.createScaledBitmap(dino2, width, height, false);
        dino3 = Bitmap.createScaledBitmap(dino3, width, height, false);
        dino4 = Bitmap.createScaledBitmap(dino4, width, height, false);
        dino5 = Bitmap.createScaledBitmap(dino5, width, height, false);
        dino6 = Bitmap.createScaledBitmap(dino6, width, height, false);
        dino7 = Bitmap.createScaledBitmap(dino7, width, height, false);
        dino8 = Bitmap.createScaledBitmap(dino8, width, height, false);

    }

    public Bitmap getDino() {

        if (counter == 1) {

            counter++;
            return dino1;

        } else if (counter == 2) {
            counter++;
            return dino2;
        } else if (counter == 3) {
            counter++;
            return dino3;
        } else if (counter == 4) {
            counter++;
            return dino4;
        } else if (counter == 5) {
            counter++;
            return dino5;
        } else if (counter == 6) {
            counter++;
            return dino6;
        } else if (counter == 7) {
            counter++;
            return dino7;
        }
        counter = 1;
        return dino8;
    }

    public Rect getCollisionShape() {
        return new Rect((int) (x + 400 * GroundGameView.screenRatioX), (int) (y + 60 * GroundGameView.screenRatioX), x + width, y + height);
    }

}
