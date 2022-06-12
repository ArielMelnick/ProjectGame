package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Spikes {

    private Bitmap spikes;
    int width, height;
    int x, y;
    int speed = 20;

    Spikes(Resources res) {

        spikes = BitmapFactory.decodeResource(res, R.drawable.spikes);


        width = spikes.getWidth();
        height = spikes.getHeight();

        width /= 6;
        height /= 6;

        width *= GroundGameView.screenRatioX;
        height *= GroundGameView.screenRatioY;

        x = 0;
        y = -height;

        spikes = Bitmap.createScaledBitmap(spikes, width, height, false);

    }

    public Bitmap getSpikes() {
        return spikes;

    }

    public Rect getCollisionShape() {  // it will create a rectangle around the airplane and it will return this rectangle so I will be able to check if there is a collision between the airplane and a bird with "Rect.intersect()"
        return new Rect((int) (x + 130 * GroundGameView.screenRatioX), (int) (y + 60 * GroundGameView.screenRatioY), (int) (x + width - 90 * GroundGameView.screenRatioX), y + height);
    }


}
