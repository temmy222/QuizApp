package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView txtHighScore;
    TextView txtTotalQuestion, txtCorrectQuestion, txtWrongQuestion;

    Button btStartQuiz;
    Button btMainMenu;

    private int highScore;
    public static final String SHARED_PREFERENCE = "shared_preference";
    public static final String SHARED_PREFERENCE_HIGH_SCORE = "shared_preference_high_score";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btMainMenu = findViewById(R.id.result_bt_mainmenu);
        btStartQuiz = findViewById(R.id.result_bt_playAgain);
        txtHighScore = findViewById(R.id.result_text_High_Score);
        txtTotalQuestion = findViewById(R.id.result_total_Ques);
        txtCorrectQuestion = findViewById(R.id.result_Correct_Ques);
        txtWrongQuestion = findViewById(R.id.result_Wrong_Ques);

        btMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PlayActivity.class);
                startActivity(intent);

            }
        });

        btStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
        
        loadHighScore();

        Intent intent = getIntent();

        int score = intent.getIntExtra("UserScore",0);
        Log.d("Score Output" , String.valueOf(score));
        int totalQuestion = intent.getIntExtra("Total Question",0);
        int correctQuestion = intent.getIntExtra("Correct Question",0);
        int wrongQuestion = intent.getIntExtra("Wrong Question",0);

        txtTotalQuestion.setText("Total Ques: " + String.valueOf(totalQuestion));
        txtCorrectQuestion.setText("Total Correct : " + String.valueOf(correctQuestion));
        txtWrongQuestion.setText("Total Wrong: " + String.valueOf(wrongQuestion));
        
        
        if (score > highScore) {
            updateHighScore(score);
        }



    }

    private void updateHighScore(int newHighScore) {

        highScore = newHighScore;

        txtHighScore.setText(" High Score : " + String.valueOf(highScore));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCE_HIGH_SCORE,highScore);
        editor.apply();
    }

    private void loadHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHARED_PREFERENCE_HIGH_SCORE,0);
        txtHighScore.setText(" High Score : " + String.valueOf(highScore));

    }
}