package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        textViewCorrect = findViewById(R.id.txtCorrect); // finds the button in the activity and assigns it to a variable in Java for subsequent use
        textViewWrong = findViewById(R.id.txtWrong);
        textViewCountDown = findViewById(R.id.txtTimer);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestions = findViewById(R.id.textView2);

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

        startQuiz();  // function to start the quiz after getting all questions from the database
    }

    private void startQuiz() {
        questionTotalCount = questionList.size();
        Collections.shuffle(questionList); // used to randomly shuffle the questions from the database

        showQuestions(); //show the questions

        // listen for a click event from the button
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answerd){

                    // check that one of the buttons has been clicked before moving to the quizOperation method to check the calculations
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        quizOperations();
                    }
                    else{

                        // if none is selected and user attempts to click next; warn user not to proceed yet using Toast class
                        String textToast = "Please select option";
                        Toast.makeText(QuizActivity.this, textToast, Toast.LENGTH_SHORT );
                    }
                }
            }
        });


    }

    private void quizOperations() {
        answerd = true; // before entering this method, a radio button must have been clicked

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1 ;

        checkSolution(answerNr, rbselected);
    }

    private void checkSolution(int answerNr, RadioButton rbselected) {

        switch(currentQuestions.getAnswerNr()){

            case 1:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    rb1.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                }
                else {
                    changeToIncorrectColor(rbselected);

                }
                showQuestions();
                break;
            case 2:

                if (currentQuestions.getAnswerNr() == answerNr) {

                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                }
                else {
                    changeToIncorrectColor(rbselected);

                }
                showQuestions();

                break;

            case 3:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                }
                else {
                    changeToIncorrectColor(rbselected);

                }
                showQuestions();
                break;

            case 4:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                }
                else {
                    changeToIncorrectColor(rbselected);

                }
                showQuestions();
                break;
        } // end of the switch case statement

        if (questionCounter < questionTotalCount) {
            buttonConfirmNext.setText("Confirm and Finish");
        }
    }

    private void changeToIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_wrong));
    }

    private void showQuestions()
    {
        rbGroup.clearCheck();  // clear the previous check mark of the quiz

        if (questionCounter < questionTotalCount){
            currentQuestions = questionList.get(questionCounter); // get the question class that corresponds to the particular questionCounter from the database
            textViewQuestions.setText(currentQuestions.getQuestion()); // from the class get the question and set it to show in the question box in the activity (UI)
            Log.d("Message Output" ,currentQuestions.getQuestion());
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