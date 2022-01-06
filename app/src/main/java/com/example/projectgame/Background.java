package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    int x = 0, y = 0; // those will be the coordinates of the image
    Bitmap background;

     public Background (int screenX, int screenY, Resources res) {  // res contains the image data
         this.background = BitmapFactory.decodeResource(res, R.drawable.background);  // bitmapFactory creates bitmaps
         this.background = Bitmap.createScaledBitmap(background, screenX, screenY, false);  // to fit the image to the screen

     }
}
