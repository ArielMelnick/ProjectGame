package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {
    float x, y;
    Bitmap bullet;

    // it seems to me for right now, that "newBullet()" from GameView can be here instead

    Bullet(Resources res) {
        this.bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        int width = bullet.getWidth();
        int height = bullet.getHeight();

        width = width / 4;
        height = height / 4;

        width *=  GameView.screenRatioX;
        height *=  GameView.screenRatioY;

        this.bullet = Bitmap.createScaledBitmap(bullet, width, height, false);  // reforming the image according to the changes i made.


    }


}
