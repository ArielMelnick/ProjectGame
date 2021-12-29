package com.example.projectgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    int x = 0, y = 0;
    Bitmap background;

     public Background (int screenX, int screenY, Resources res) { // res contains the image data
         background = BitmapFactory.decodeResource(res, R.drawable.background); // bitmapFactory creates bitmaps
         background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

     }
}
