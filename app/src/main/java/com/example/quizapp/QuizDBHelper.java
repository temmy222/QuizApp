package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.quizapp.QuizContract.*;

import androidx.annotation.Nullable;

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

        Questions q3 = new Questions(" What is the Men National Football Team of Cameroon popularly called ", " Black Stars ", " Red Devils ", " Lome ", " Ouagadougou ", 2);
        addQuestion(q3);

        Questions q4 = new Questions(" What is the Capital of Ghana ", " Abuja ", " Accra ", " Lome ", " Ouagadougou ", 2);
        addQuestion(q4);

        Questions q5 = new Questions(" What is the Capital of Ghana ", " Abuja ", " Accra ", " Lome ", " Ouagadougou ", 2);
        addQuestion(q5);
    }
}
