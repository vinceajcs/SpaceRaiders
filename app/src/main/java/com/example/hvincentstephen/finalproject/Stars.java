package com.example.hvincentstephen.finalproject;

import java.util.Random;

/**
 * Created by hvincentstephen on 5/2/17.
 */

public class Stars {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    public Stars(int xSize, int ySize){
        maxX = xSize;
        maxY = ySize;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(10);

        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);

    }

    public void update(int playerSpeed){
        x -= playerSpeed;
        x -= speed;

        if (x < 0){
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(15);
        }
    }

    public float getStarSize(){
        float minX = 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        float starSize = rand.nextFloat() * (maxX - minX) + minX;

        return starSize;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
