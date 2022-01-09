package com.example.projectgame;

import static com.example.projectgame.GameView.screenRatioX;
import static com.example.projectgame.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Flight {
    boolean isGoingUp = false;
    int x, y, width, height, wingCounter = 0;  // x and y are the coordinates (position on the screen) of the airplane
    Bitmap flight1, flight2;

    Flight(int screenY, Resources res) {  // it will get the height of the screen (screenY) and the data of the image of the airplane from the resources (res)

        this.flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        this.flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);


        this.width = flight1.getWidth();  // to get the width of the image of the airplane
        this.height = flight1.getHeight();  // to get the height of the image of the airplane

        this.width /= 4;  // the image is too big for the screen so i'm making it smaller
        this.height /= 4;

        this.width = (int) (this.width * screenRatioX);  // to fit the image to other screens -> in different sizes (screenRatioX and screenRatioY are from GameView)
        this.height = (int) (this.height * screenRatioY);

        this.flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        this.flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        this.y = screenY / 2 -flight1.getHeight()/2;  // i want the airplane to be on the start vertically middle
        this.x = (int) (64 * screenRatioX);  // so the airplane will be about 64 pixels from the left of the screen


    }

    public Bitmap getFlight() {   // to switch between the two images

        if(this.wingCounter == 0){
            wingCounter++;
            return flight1;
        }
        wingCounter--;
        return flight2;


    }
}
