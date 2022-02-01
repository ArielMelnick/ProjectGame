package com.example.projectgame;

import static com.example.projectgame.GameView.screenRatioX;
import static com.example.projectgame.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Flight {
    int toShoot = 0, shootCounter = 1;
    boolean isGoingUp = false;
    float x, y;  // x and y are the coordinates (position on the screen) of the airplane.
    int width, height, wingCounter = 0;
    Bitmap flight1, flight2, shoot1, shoot2, shoot3, shoot4, shoot5;
    private GameView gameView;

    Flight(GameView gameView ,int screenY, Resources res) {  // it will get the height of the screen (screenY) and the data of the image of the airplane from the resources (res).
        this.gameView = gameView;

        this.flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        this.flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);

        this.shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        this.shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        this.shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        this.shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        this.shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);


        this.width = flight1.getWidth();  // to get the width of the image of the airplane.
        this.height = flight1.getHeight();  // to get the height of the image of the airplane.

        this.width /= 4;  // the image is too big for the screen so i'm making it smaller.
        this.height /= 4;

        this.width = (int) (this.width * screenRatioX);  // to fit the image to other screens -> in different sizes (screenRatioX and screenRatioY are from GameView).
        this.height = (int) (this.height * screenRatioY);

        this.flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);   // setting the image size according to the changes i made according to the size of the screen.
        this.flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        this.shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false);   // setting the image size according to the changes i made according to the size of the screen.
        this.shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false);
        this.shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false);
        this.shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false);
        this.shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false);


        this.x = (int) (64 * screenRatioX);  // so the airplane will be about 64 pixels from the left of the screen.


    }

    public Bitmap getFlight() {  // gets called in "draw()" in GameView to draw the relevant image.

        // to switch between the images.
        // if the user touches the right side of the screen then getFlight will return an image(one of the stages of the airplane shooting) from this block of code(ends in "return shoot5")
        // and if not, it will return an image( one of the stages of the regular airplane) from the next block of code (starts in " if (this.wingCounter == 0)").
        if (toShoot != 0) {
            if (shootCounter == 1) {
                shootCounter++;
                return shoot1;
            }
            if (shootCounter == 2) {
                shootCounter++;
                return shoot2;
            }
            if (shootCounter == 3) {
                shootCounter++;
                return shoot3;
            }
            if (shootCounter == 4) {
                shootCounter++;
                return shoot4;
            }

            shootCounter = 1;
            toShoot--;
            gameView.newBullet();
            return shoot5;
        }

        if (this.wingCounter == 0) {
            wingCounter++;
            return flight1;
        }
        wingCounter--;
        return flight2;


    }
}