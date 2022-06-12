package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bird {
    int x, y, width, height, birdCounter = 1, speed = 20;
    Bitmap bird1, bird2, bird3, bird4;
    boolean wasShot = true;

    Bird(Resources res) {  // the constructor method of this claas, used to initiate and set variables and objects
        bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1);  // the bird stages/phases so it will seem like it moves its wings and fly
        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2);
        bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3);
        bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4);

        width = bird1.getWidth();
        height = bird1.getHeight();

        width /= 6;
        height /= 6;

        width *= HeavensGameView.screenRatioX;
        height *= HeavensGameView.screenRatioY;


        bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
        bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);
        bird3 = Bitmap.createScaledBitmap(bird3, width, height, false);
        bird4 = Bitmap.createScaledBitmap(bird4, width, height, false);


        y = -height;  // initially the bird will be out of the screen on the top and it will come up from there.
        x = 0;  // initially the bird will be on the left side of the screen.

    }

    public Bitmap getBird() {  // used to return the relevant image of the bird.

        if (birdCounter == 1) {
            birdCounter++;
            return bird1;
        }
        if (birdCounter == 2) {
            birdCounter++;
            return bird2;
        }
        if (birdCounter == 3) {
            birdCounter++;
            return bird3;
        }

        birdCounter = 1;
        return bird4;

    }

    public Rect getCollisionShape() {  // it will create a rectangle around the bird and it will return this rectangle so I will be able to check if there is a collision between the bird and something else like the airplane or a bullet with "Rect.intersect()".
        return new Rect(x, y, x + width, y + height);  // the "x" is the left side of the bird, the "y" is the top, "x + width" is the right side and "y + height" is the bottom coordinate.
    }
}
