package com.tanvi.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverActivity extends AppCompatActivity {

    private Button startGameAgain;
    private TextView Displayscore;
    private TextView DisplayHighScore;
    int score = 0,highscore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        DisplayHighScore = (TextView) findViewById(R.id.displayHighScore);
        startGameAgain = (Button) findViewById(R.id.play_again_btn);
        Displayscore = (TextView) findViewById(R.id.displayScore);

        score = getIntent().getIntExtra("score", 0);
        Displayscore.setText("Score:"+Integer.toString(score));

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highscore = prefs.getInt("score", 0);

        if (highscore > score) {
            DisplayHighScore.setText("High Score:"+Integer.toString(highscore));
        } else {
            highscore = score;
            DisplayHighScore.setText("High Score:"+Integer.toString(highscore));
            prefs.edit().putInt("score", highscore).apply();
        }

        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}