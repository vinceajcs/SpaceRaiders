package com.example.hvincentstephen.finalproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by hvincentstephen on 5/5/17.
 * Author: Robert Signorile, from demo Jukebox
 *
 */

public class MusicService extends Service {

    // we declare constants for "actions" that are packed into each intent
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_STOP = "stop";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_RESUME = "resume";
    // the actual media player to play the music
    private MediaPlayer player;
    private boolean started = false;
    private int track;
    /*
     * This method is called each time a request comes in from the app
     * via an intent.  It processes the request by playing or stopping
     * the song as appropriate.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d(action, "first");

        if (action.equals(ACTION_PLAY)) {
            try {
                // stop any previous playing song
                if (player != null) {
                    player.stop();
                    player.release();
                }
            }
            catch (RuntimeException e){
                //just catch the possible error, and keep on going
                Log.v("Media Error","runtime error");
            }
            // start the new song
            Log.d("startsong", "service");
            player = MediaPlayer.create(this, R.raw.magicman);
            player.setLooping(true);
            player.start();
            started = true;

            Log.d("songstarted", "service");

        } else if (action.equals(ACTION_STOP)) {
            // stop any currently playing song
            if (player != null) {
                player.stop();
                started = false;
                //player.release();
            }
        } else if (action.equals(ACTION_PAUSE)) {
            // stop any currently playing song
            if (player.isPlaying()) {
                track = player.getCurrentPosition();
                player.pause();
            } else {
                player.start();
            }
            started = true;

        }  else if (action.equals(ACTION_RESUME) && started) {
            // stop any currently playing song
            player.seekTo(track);
            player.start();
            started = false;
        }
        return START_STICKY;   // START_STICKY means service will keep running
    }

    /*
     * This method specifies how our service will deal with binding.
     * We don't choose to support binding, which is indicated by returning null.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

