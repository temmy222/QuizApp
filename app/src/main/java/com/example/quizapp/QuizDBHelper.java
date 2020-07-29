package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.quizapp.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper  {


    private static final String DATABASE_NAME = "MomoQuiz";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);  // creating the question table

        fillQuestionTable(); // insert the data into the table


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }

    private void addQuestion(Questions question){
        ContentValues cv = new ContentValues(); // used to add values to the database
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());

        db.insert(QuestionTable.TABLE_NAME, null, cv);

    }

    private void fillQuestionTable() {
        Questions q1 = new Questions(" What is the Capital of Ghana ", " Abuja ", " Accra ", " Lome ", " Ouagadougou ", 2);
        addQuestion(q1);

        Questions q2 = new Questions(" Who is the first democratically elected President of Nigeria ", " Olusegun Obasanjo ", " Ernest Shonekan ", " Yakubu Gowon ", " Sani Abacha ", 1);
        addQuestion(q2);

        Questions q3 = new Questions(" What is the Men National Football Team of Cameroon popularly called ", " Black Stars ", " Red Devils ", " Indomitable Lions ", " Bafana Bafana ", 3);
        addQuestion(q3);

        Questions q4 = new Questions(" What is the former Name of Zimbabwe ", " Gold Coast ", " Rhodesia ", " Dahomey ", " Somalia ", 2);
        addQuestion(q4);

        Questions q5 = new Questions(" What is the currency of Tunisia called ", " Naira ", " Cedi ", " Francs ", " Dinar ", 4);
        addQuestion(q5);
    }

    public ArrayList<Questions> getAllQuestions() {
        ArrayList<Questions> questionsList = new ArrayList<>();

        db = getReadableDatabase();
        String Projection[] = {
                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR
        }; // input to the query

        Cursor c = db.query(QuestionTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );  // used to query the database

        if (c.moveToFirst()) {
            do {
                Questions questions = new Questions();
                questions.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                questions.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                questions.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                questions.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                questions.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                questions.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));

                questionsList.add(questions);
            } while (c.moveToNext());  // this basically uses the cursor and moves line by line through the db.
            // It gets the column index of the question table, gets the string in that table for that line and
            // sets it to the question class
        }
        c.close(); // closing the cursor
        return questionsList;
    }
}
