package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WrongDialog {

    private Context mContext;
    private Dialog wrongDialog;



    public WrongDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void wrongDialog( String correctAnswer){

        wrongDialog = new Dialog(mContext);
        wrongDialog.setContentView(R.layout.wrong_dialog);

        Button bt_wrongDialog = (Button) wrongDialog.findViewById(R.id.bt_wrong_dialog);

        TextView textViewCorrectAnswer = (TextView) wrongDialog.findViewById(R.id.text_correct_ans);

        textViewCorrectAnswer.setText("Correct Ans: " + String.valueOf(correctAnswer));

        bt_wrongDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongDialog.dismiss();
            }
        });

        wrongDialog.show();
        wrongDialog.setCancelable(false);
        wrongDialog.setCanceledOnTouchOutside(false);


    }
}
