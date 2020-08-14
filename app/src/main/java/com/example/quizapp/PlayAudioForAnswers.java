package com.example.quizapp;

import android.content.Context;
import android.media.MediaPlayer;

public class PlayAudioForAnswers {

    private Context mContext;
    private MediaPlayer mediaPlayer;

    public PlayAudioForAnswers(Context mContext) {
        this.mContext = mContext;
    }

    public void setAudioForAnswer(int flag){

        switch (flag) {
            case 1:
                int CorrectAudio = R.raw.correct;
                playMusic(CorrectAudio);
                break;

            case 2:
                int wrongAudio = R.raw.wrong;
                playMusic(wrongAudio);
                break;

        }
    }

    private void playMusic(int audioFile) {
        mediaPlayer = MediaPlayer.create(mContext,audioFile);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
    }
}
