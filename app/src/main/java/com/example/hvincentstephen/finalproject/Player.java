package com.example.hvincentstephen.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by hvincentstephen on 5/1/17.
 */

public class Player {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;

    private boolean boost;
    private final int gravity = -10;

    private int maxY;
    private int minY;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 30;

    private Rect detectCollision; //similar to code in Enemy


    public Player(Context context, int xSize, int ySize) {
        x = 50;
        y = 50;
        speed = 1;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playership);
        boost = false; // player ship is not boosting to start, thus set to false

        maxY = ySize - bitmap.getHeight();
        minY = 0;

        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight()); //initialize rectangle object
    }

    public void update(){

        if (boost){
            speed += 10;
        } else {
            speed -= 10;
        }

        // statements below ensure player does not go pass limits
        if (speed > MAX_SPEED){
            speed = MAX_SPEED;
        }

        if (speed < MIN_SPEED){
            speed = MIN_SPEED;
        }

        y -= speed + gravity;

        if (y < minY){
            y = minY;
        }

        if (y > maxY){
            y = maxY;
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getSpeed() {
        return speed;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void startBoost(){
        boost = true;
    }

    public void stopBoost(){
        boost = false;
    }
}
