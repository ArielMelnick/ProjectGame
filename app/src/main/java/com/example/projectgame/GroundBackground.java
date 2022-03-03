package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GroundBackground {

    float x = 0, y = 0; // those will be the coordinates of the images on the screen
    Bitmap background;

    public GroundBackground(int screenX, int screenY, Resources res) {  // res contains the image data

        this.background = BitmapFactory.decodeResource(res, R.drawable.ground_background);  // bitmapFactory creates bitmaps
        this.background = Bitmap.createScaledBitmap(background, screenX + 2, screenY, false);  // to fit the image to the screen

    }
}




