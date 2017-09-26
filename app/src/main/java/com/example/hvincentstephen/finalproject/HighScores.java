package com.example.hvincentstephen.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HighScores extends AppCompatActivity {

    TextView textView,textView2,textView3,textView4, textView5;
    DatabaseHandler database = new DatabaseHandler(this);
    ListView myListView;
    String[] scores;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        scores = database.getSortedScores();
        String length = Integer.toString(scores.length);


        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);


        if (scores.length == 0){
            textView.setText("1. " );
            textView2.setText("2. " );
            textView3.setText("3. " );
            textView4.setText("4. " );
            textView5.setText("5. " );

        } else if (scores.length == 1){
            textView.setText("1. " + scores[0]);
            textView2.setText("2. " );
            textView3.setText("3. " );
            textView4.setText("4. " );
            textView5.setText("5. " );

        } else if (scores.length == 2){
            textView.setText("1. " + scores[0]);
            textView2.setText("2. " + scores[1]);
            textView3.setText("3. " );
            textView4.setText("4. " );
            textView5.setText("5. " );

        } else if (scores.length == 3){
            textView.setText("1. " + scores[0]);
            textView2.setText("2. " + scores[1]);
            textView3.setText("3. " + scores[2]);
            textView4.setText("4. " );
            textView5.setText("5. " );

        } else if (scores.length == 4){
            textView.setText("1. " + scores[0]);
            textView2.setText("2. " + scores[1]);
            textView3.setText("3. " + scores[2]);
            textView4.setText("4. " + scores[3]);
            textView5.setText("5. " );
        } else {
            textView.setText("1. " + scores[0]);
            textView2.setText("2. " + scores[1]);
            textView3.setText("3. " + scores[2]);
            textView4.setText("4. " + scores[3]);
            textView5.setText("5. " + scores[4]);
        }

        Log.d(length, "length");

    }

    @Override
    public void onStart(){
        super.onStart();
        scores = database.getSortedScores();
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_PLAY);
        Log.d("start", "start");
        startService(intent);
        Log.d("start", "afterIntent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_PAUSE);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_RESUME);
        startService(intent);
    }


}
