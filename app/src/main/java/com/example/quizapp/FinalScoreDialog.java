package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreDialog {

    private Context mContext;
    private Dialog finalScoreDialog; // create variable that would store dialog box
    private TextView textViewFinalScore;

    public FinalScoreDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void finalScoreDialog (int correctAns, int wrongAns, int totalSizeOfQuiz){

        finalScoreDialog = new Dialog(mContext); // create the dialog
        finalScoreDialog.setContentView(R.layout.final_score_dialog); // set the layout view for the dialog

        final Button btFinalScore = (Button) finalScoreDialog.findViewById(R.id.bt_final_score); // get the button
        
        finalScoreCal(correctAns, wrongAns, totalSizeOfQuiz);

        btFinalScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalScoreDialog.dismiss(); // to dismiss the dialog

                Intent intent = new Intent(mContext, QuizActivity.class);
                mContext.startActivity(intent);

            }
        });

        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false); // user cannot delete the dialog by simply clicking on it
        finalScoreDialog.setCanceledOnTouchOutside(false); // user cannot delete the dialog by clicking outside of it

    }

    private void finalScoreCal(int correctAns, int wrongAns, int totalSizeOfQuiz) {

        int tempScore;
        textViewFinalScore = (TextView) finalScoreDialog.findViewById(R.id.text_final_score);

        if (correctAns == totalSizeOfQuiz) {

            tempScore = (correctAns * 10) - (wrongAns *5);
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else if (wrongAns == totalSizeOfQuiz) {
            tempScore = 0;
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else if (correctAns > wrongAns) {
            tempScore = (correctAns * 10) - (wrongAns *5);
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else if (wrongAns > correctAns) {
            tempScore = (correctAns * 10) - (wrongAns *5);
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }

        else if (wrongAns == correctAns) {
            tempScore = (correctAns * 10) - (wrongAns *5);
            textViewFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }

    }
}
