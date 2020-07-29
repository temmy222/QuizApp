package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private TextView textViewQuestions;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private TextView textViewCorrect, textViewWrong;

    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Questions currentQuestions;
    private boolean answerd;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupUI();
        fetchDB();
    }

    private void setupUI() {
        textViewCorrect = findViewById(R.id.txtCorrect);
        textViewWrong = findViewById(R.id.txtWrong);
        textViewCountDown = findViewById(R.id.txtTimer);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        textViewScore = findViewById(R.id.txtScore);

        buttonConfirmNext = findViewById(R.id.button);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
    }

    private void fetchDB(){
        QuizDBHelper dbHelper = new QuizDBHelper(this );
        questionList = dbHelper.getAllQuestions();
    }

    private void showQuestions()
    {
        rbGroup.clearCheck();  // clear the previous check mark of the quiz

        if (questionCounter < questionTotalCount){
            currentQuestions = questionList.get(questionCounter); // get the question class that corresponds to the particular questionCounter from the database
            textViewQuestions.setText(currentQuestions.getQuestion()); // from the class get the question and set it to show in the question box in the activity (UI)
            rb1.setText(currentQuestions.getOption1()); // from the class get the option and set it to show in the radio button in the activity (UI)
            rb2.setText(currentQuestions.getOption2());
            rb3.setText(currentQuestions.getOption3());
            rb4.setText(currentQuestions.getOption4());

            questionCounter++;
            answerd = false;

            buttonConfirmNext.setText("Confirm");  // set the button to click to answer the question
            textViewQuestionCount.setText("Questions " + questionCounter + " / " + questionTotalCount); // set the question counter at the top

    } else{
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);

                }
            }, 500);
        }
    }
}