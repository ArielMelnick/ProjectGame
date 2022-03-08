package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Zombie {

    private Bitmap[] walk = new Bitmap[11];
    private int width, height;
    int x, y;
    private int counter = 0;
    int speed;


    Zombie(Resources res) {
        walk[1] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_1);
        walk[2] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_2);
        walk[3] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_3);
        walk[4] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_4);
        walk[5] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_5);
        walk[6] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_6);
        walk[7] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_7);
        walk[8] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_8);
        walk[9] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_9);
        walk[10] = BitmapFactory.decodeResource(res, R.drawable.zombie_walk_10);

        width = walk[1].getWidth();
        height = walk[1].getHeight();

        width /= 4;
        height /= 4;

        width *= GameView.screenRatioX;
        height *= GameView.screenRatioY;

        x = 0;
        // I need to take care of the y coordinate and also the x


        for (int i = 1; i < 11; i++) {
            walk[i] = Bitmap.createScaledBitmap(walk[i], width, height, false);
        }

    }

    public Bitmap getZombie(){

        counter++;
        if(counter == 10){
            counter = 0;
            return walk[10];
        }
        return walk[counter];

    }

    public Rect getCollisionShape() {  // it will create a rectangle around the airplane and it will return this rectangle so I will be able to check if there is a collision between the airplane and a bird with "Rect.intersect()"
        return new Rect(x, y, x + width, y +height);
    }


}
