package com.example.hvincentstephen.finalproject;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.*;

import java.util.ArrayList;

/**
 * Created by hvincentstephen on 5/1/17.
 * Inspired by Simplified Coding.
 */

public class GameView extends SurfaceView implements Runnable{

    volatile boolean playing;
    Context context;

    //game thread
    private Thread gameThread = null;
    //public boolean collide = false;

    private boolean Flag1000 = false;

    private Player player;
    private Friend friend;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
    private int enemyCount = 2;

    public Explosion explosion;

    ArrayList<Stars> starsList = new ArrayList<Stars>();

    //a screenX holder
    int xSize;
    //to count the number of misses (of enemy ships)
    int misses;
    //indicator that the enemy has just entered the game screen
    boolean flag;
    //an indicator if the game is Over
    private boolean gameOver;

    //private Handler mHandler = new Handler();
    //private ExplosionThread explosionThread = new ExplosionThread();

    int score;

    public DatabaseHandler database;

    static MediaPlayer gameOnSound;
    final MediaPlayer killedEnemySound;
    final MediaPlayer gameOverSound;


    //constructor
    public GameView(Context context, int xSize, int ySize){
        super(context);

        this.context = context;

        player = new Player(context, xSize, ySize);
        surfaceHolder = getHolder();
        paint = new Paint();

        this.xSize = xSize;

        misses = 0;
        gameOver = false;


        for (int i = 0; i < enemyCount; i++){
            Enemy enemy = new Enemy(context, xSize, ySize);
            enemiesList.add(enemy);
        }

        int starCount = 200;
        for (int i = 0; i < starCount; i++) {
            Stars star  = new Stars(xSize, ySize);
            starsList.add(star);
        }

        explosion = new Explosion(context);
        //friend = new Friend(context, xSize, ySize);

        //setting the score to 0 initially
        score = 0;

        database = new DatabaseHandler(context);

        gameOnSound = MediaPlayer.create(context,R.raw.maintheme);
        killedEnemySound = MediaPlayer.create(context,R.raw.explosionsound);
        gameOverSound = MediaPlayer.create(context,R.raw.gameover);

        //start game music when game starts
        gameOnSound.start();

    }

    @Override
    public void run(){
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update(){
        player.update();

        score++;

        /*if (score == 1000 && !Flag1000){
            enemyCount++;
            Flag1000 = true;
        }*/

       /* for (int j = 0; j < explosionCount; j++){
            explosionList.get(j).setX(-500);
            explosionList.get(j).setY(-500);
        }*/

        //setting boom outside the screen
        /*mHandler.postDelayed(new Runnable() {
            public void run() {
                explosion.setX(-500);
                explosion.setY(-500);
            }
        }, 1000);*/

        //explosionThread.start();

        explosion.setX(-500);
        explosion.setY(-500);


        //updating the enemy coordinate with respect to player speed
        for (int i = 0; i < enemyCount; i++) {

            if(enemiesList.get(i).getX() == xSize){
                flag = true;
            }

            enemiesList.get(i).update(player.getSpeed());


            //if collision occurs between player and enemy...
            if (Rect.intersects(player.getDetectCollision(), enemiesList.get(i).getDetectCollision())) {
                //explosionList.get(i).setX(enemiesList.get(i).getX());
                //explosionList.get(i).setY(enemiesList.get(i).getY());

                //set explosion at coordinate where the collision happens
                explosion.setX(enemiesList.get(i).getX());
                explosion.setY(enemiesList.get(i).getY());

                killedEnemySound.start();

                score = score + 100;

                enemiesList.get(i).setX(-350);

            } else {
                //if the enemy has just entered
                if (flag) {
                    //if player's x coordinate is more than the enemies's x coordinate.i.e. enemy has just passed across the player
                    if (player.getDetectCollision().exactCenterX() >= enemiesList.get(i).getDetectCollision().exactCenterX() + 280) {
                        //increment countMisses
                        misses++;

                        //setting the flag false so that the else part is executed only when new enemy enters the screen
                        flag = false;
                        //if misses equal to 3, then game over
                        if (misses == 3) {
                            //setting playing false to stop the game.
                            playing = false;
                            gameOver = true;

                            database.addScore(score);
                            Log.d("collide", "addedscore");

                            //stopping the gameon music
                            gameOnSound.stop();
                            //play the game over sound
                            gameOverSound.start();


                        }
                    }
                }
            }
        }

        for (Stars star : starsList) {
            star.update(player.getSpeed());
        }

        /*friend.update(player.getSpeed());
        //checking for a collision between player and a friend
        if(Rect.intersects(player.getDetectCollision(),friend.getDetectCollision())){
            //display explosion at collision
            explosion.setX(friend.getX());
            explosion.setY(friend.getY());
            //setting playing false to stop the game
            //playing = false;
            //setting the isGameOver true as the game is over
            //gameOver = true;
        }*/
    }


    private void draw(){
        //check if surface is valid
        if (surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK); //draw background color

            //setting the paint color to white to draw the stars
            paint.setColor(Color.WHITE);

            //draw the stars
            for (Stars star : starsList){
                paint.setStrokeWidth(star.getStarSize());
                canvas.drawPoint(star.getX(), star.getY(), paint);
            }

            //drawing the score on the game screen
            paint.setTextSize(100);
            canvas.drawText("Score: " + score, 100, 100, paint);

            paint.setTextSize(50);
            canvas.drawText("Enemies missed: " + misses + " out of 3", 100, 150, paint);

            //draw the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            //draw the friend
            /*canvas.drawBitmap(
                    friend.getBitmap(),
                    friend.getX(),
                    friend.getY(),
                    paint);*/

            //draw the enemies
            for (int i = 0; i < enemyCount; i++){
                canvas.drawBitmap(
                        enemiesList.get(i).getBitmap(),
                        enemiesList.get(i).getX(),
                        enemiesList.get(i).getY(),
                        paint);
            }

            canvas.drawBitmap(
                    explosion.getBitmap(),
                    explosion.getX(),
                    explosion.getY(),
                    paint);



            if(gameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yCoord= (int)((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over", canvas.getWidth() / 2, yCoord, paint);

                /*String[] hs = database.getSortedScores();

                if (hs.length == 0){
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(100);
                    canvas.drawText("New High Score!", 300, 300 , paint);
                }

                if (hs.length != 0 && score > Integer.parseInt(hs[0])){
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(100);
                    canvas.drawText("New High Score!", 300, 300, paint);
                }*/
            }


            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control(){
        try {
            gameThread.sleep(15);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pause(){
        playing = false; //no longer playing in pause mode
        try {
            gameThread.join(); //wait for thread to finish
            gameOnSound.pause();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        playing = true; //set play to true again
        gameThread = new Thread(this);
        gameThread.start();
        gameOnSound.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoost();
                break;

            case MotionEvent.ACTION_DOWN:
                player.startBoost();
                break;
        }

        if (gameOver){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                context.startActivity(new Intent(context, MainActivity.class));
                gameOverSound.stop();
            }
        }
        return true;
    }

    /*class ExplosionThread extends Thread {

        @Override
        public void run() {
            try {
                if(!collide){
                    String tag = "hey";
                    String message = "vincent";
                    Log.d(tag, message);
                    sleep(1);
                    explosion.setX(-500);
                    explosion.setY(-500);
                    //collide = false;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*Thread explosionThread = new Thread() {
        @Override
        public void run() {
            try {
                    String tag = "hey";
                    String message = "vincent";
                    Log.d(tag, message);
                    sleep(5000);
                    explosion.setX(-500);
                    explosion.setY(-500);


                } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    };*/
    public static void stopMusic(){
        gameOnSound.stop();
    }



}
