package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {
    int x, y;
    Bitmap bullet;
    int width, height;


    Bullet(Resources res) {  // the constructor method of this claas, used to initiate and set the variables and objects.
        this.bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width = width / 4;
        height = height / 4;

        if (HeavensGameView.screenRatioX > 0) {

            width *= HeavensGameView.screenRatioX;
            height *= HeavensGameView.screenRatioY;
        } else {
            width *= GroundGameView.screenRatioX;
            height *= GroundGameView.screenRatioY;
        }

        this.bullet = Bitmap.createScaledBitmap(bullet, width, height, false);  // reforming the image according to the changes i made.


    }

    public Rect getCollisionShape() {  // it will create a rectangle around the airplane and it will return this rectangle so I will be able to check if there is a collision between the airplane and a bird with "Rect.intersect()"
        return new Rect(x, y, x + width, y + height);
    }


}
