package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

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
    private boolean answered;
    private static final String TAG = QuizActivity.class.getSimpleName();

    private Handler handler = new Handler(); // handler is used to delay  a function use

    private ColorStateList defaultTextColor;  // will be used to change the color of the text view labels

    private int correctAns = 0, wrongAns=0;
    private int quizScore = 0;
    int score = 0;

    private FinalScoreDialog finalScoreDialog;
    private CorrectDialog correctDialog;
    private WrongDialog wrongDialog;

    private PlayAudioForAnswers playAudioForAnswers;
    int FLAG = 0;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private int totalSizeofQuiz=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        System.out.println("TAG = " + TAG);

        setupUI();
        fetchDB();

        defaultTextColor = rb1.getTextColors();

        finalScoreDialog = new FinalScoreDialog (this);
        correctDialog = new CorrectDialog (this);
        wrongDialog = new WrongDialog(this);
        playAudioForAnswers = new PlayAudioForAnswers(this);
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

        //logic for what happens when a button is selected
        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()  {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.radio_button1:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        break;

                    case R.id.radio_button2:
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        break;

                    case R.id.radio_button3:
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        break;

                    case R.id.radio_button4:
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        break;



                }

            }
        });

        // listen for a click event from the button
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered){

                    // check that one of the buttons has been clicked before moving to the quizOperation method to check the calculations
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        quizOperations();
                    }
                    else{

                        // if none is selected and user attempts to click next; warn user not to proceed yet using Toast class
                        String textToast = "Please select option";
                        Toast.makeText(QuizActivity.this, textToast, Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        });


    }

    private void quizOperations() {
        answered = true; // before entering this method, a radio button must have been clicked

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1 ; // gets the index value of the radio button

        checkSolution(answerNr, rbSelected);
        
    }

    private void checkSolution(int answerNr, RadioButton rbSelected) {

        switch(currentQuestions.getAnswerNr()){

            case 1:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    rb1.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct : " + String.valueOf(correctAns));


                    quizScore = quizScore + 10;
                    textViewScore.setText("Score: " + String.valueOf(quizScore));

                    correctDialog.correctDialog(quizScore, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioForAnswer(FLAG);

                }
                else {
                    changeToIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong : " + String.valueOf(wrongAns));
                    String correctAnswer = (String) rb1.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioForAnswer(FLAG);


                }

                break;
            case 2:

                if (currentQuestions.getAnswerNr() == answerNr) {

                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct : " + String.valueOf(correctAns));

                    quizScore = quizScore + 10;
                    textViewScore.setText("Score: " + String.valueOf(quizScore));
                    correctDialog.correctDialog(quizScore, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioForAnswer(FLAG);

                    /*
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();

                        }
                    } ,500 );
                    // used to determine what happens when the user finishes clicking
                     */
                }
                else {
                    changeToIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong : " + String.valueOf(wrongAns));
                    String correctAnswer = (String) rb2.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioForAnswer(FLAG);



                }

                break;

            case 3:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct : " + String.valueOf(correctAns));

                    quizScore = quizScore + 10;
                    textViewScore.setText("Score: " + String.valueOf(quizScore));
                    correctDialog.correctDialog(quizScore, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioForAnswer(FLAG);

                }
                else {
                    changeToIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong : " + String.valueOf(wrongAns));
                    String correctAnswer = (String) rb3.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioForAnswer(FLAG);


                }
                break;

            case 4:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct : " + String.valueOf(correctAns));

                    quizScore = quizScore + 10;
                    textViewScore.setText("Score: " + String.valueOf(quizScore));
                    correctDialog.correctDialog(quizScore, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioForAnswer(FLAG);

                }
                else {
                    changeToIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong : " + String.valueOf(wrongAns));
                    String correctAnswer = (String) rb4.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioForAnswer(FLAG);


                }
                break;
        } // end of the switch case statement

        if (questionCounter == questionTotalCount) {
            buttonConfirmNext.setText("Confirm and Finish");
        }
    }

    private void changeToIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_wrong));
        rbselected.setTextColor(Color.WHITE);
    }

    // the timer code

    private void startCountDown() {

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;

                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();

            }
        }.start();
    }




    private void updateCountDownText(){

        int minutes = (int) (timeLeftInMillis/1000) / 60;
        int seconds = (int) (timeLeftInMillis/1000) % 60;

       // String timeFormatted = String.format(Locale.getDefault(),"02d:%02d", minutes, seconds);

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000){
            textViewCountDown.setTextColor(Color.RED);

            FLAG = 3;

            playAudioForAnswers.setAudioForAnswer(FLAG);

        }
        else {

            textViewCountDown.setTextColor(defaultTextColor);

        }

        if (timeLeftInMillis == 0) {
            Toast.makeText(this, "Times Up", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);

                }
            }, 2000);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void showQuestions()
    {
        rbGroup.clearCheck();  // clear the previous check mark of the quiz

        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        if (questionCounter < questionTotalCount){
            currentQuestions = questionList.get(questionCounter); // get the question class that corresponds to the particular questionCounter from the database
            textViewQuestions.setText(currentQuestions.getQuestion()); // from the class get the question and set it to show in the question box in the activity (UI)
            Log.d("Message Output" , currentQuestions.getQuestion()); // used for debugging purposes
            rb1.setText(currentQuestions.getOption1()); // from the class get the option and set it to show in the radio button in the activity (UI)
            rb2.setText(currentQuestions.getOption2());
            rb3.setText(currentQuestions.getOption3());
            rb4.setText(currentQuestions.getOption4());

            questionCounter++;
            answered = false;

            buttonConfirmNext.setText("Confirm");  // set the button to click to answer the question
            textViewQuestionCount.setText("Questions " + questionCounter + " / " + questionTotalCount); // set the question counter at the top
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();

    } else{
            totalSizeofQuiz = questionList.size();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    // Result Activity

                    finalScoreDialog.finalScoreDialog(correctAns, wrongAns, totalSizeofQuiz);

                }
            }, 2000);
        }
    }

}