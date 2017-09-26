package com.example.hvincentstephen.finalproject;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

public class MainActivity extends AppCompatActivity{

    private ImageButton playbutton;
    private ImageButton scorebutton;
    private ImageButton controlsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playbutton = (ImageButton) findViewById(R.id.playbutton);
        scorebutton = (ImageButton) findViewById(R.id.scoresbutton);
        controlsbutton = (ImageButton) findViewById(R.id.controlsbutton);


        scorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoresActivity = new Intent(MainActivity.this, HighScores.class);
                startActivity(scoresActivity);
            }
        });

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameActivity);
            }
        });

        controlsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructionActivity = new Intent(MainActivity.this, InstructionActivity.class);
                startActivity(instructionActivity);
            }
        });
    }


}
