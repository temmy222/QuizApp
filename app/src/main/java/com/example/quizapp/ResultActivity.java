package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView txtHighScore;
    TextView txtTotalQuestion, txtCorrectQuestion, txtWrongQuestion;

    Button btStartQuiz;
    Button btMainMenu;


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


    }
}