package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CorrectDialog {

    private Context mContext; // variable for the current context
    private Dialog correctDialog; // variable for the dialog box
    private QuizActivity mQuizActivity;

    public CorrectDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void correctDialog (int score, QuizActivity quizActivity) {
        mQuizActivity = quizActivity;
        correctDialog = new Dialog(mContext);
        correctDialog.setContentView(R.layout.correct_dialog);

        Button btCorrectDialog = (Button) correctDialog.findViewById(R.id.bt_correct_dialog);
        
        score(score);

        btCorrectDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctDialog.dismiss();
                mQuizActivity.showQuestions();

            }
        });
        correctDialog.show();
        correctDialog.setCancelable(false);
        correctDialog.setCanceledOnTouchOutside(false);


    }

    private void score(int score) {

        TextView textViewScore = (TextView) correctDialog.findViewById(R.id.text_score);
        textViewScore.setText("Score :" + String.valueOf(score));
    }

}
