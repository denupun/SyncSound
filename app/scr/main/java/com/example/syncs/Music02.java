package com.example.syncs;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;

import android.media.AudioManager;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;




public class Music02 extends AppCompatActivity {
    ImageView playIcon;
    MediaPlayer mediaPlayer;

    String music_url2= "https://firebasestorage.googleapis.com/v0/b/syncs-870c6.appspot.com/o/Grut.mp3?alt=media&token=a904d847-492b-4d44-a336-f2fb7671c078";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music02);

        playIcon= findViewById(R.id.playIcon);



        playIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playIcon.setImageResource(R.drawable.ic_baseline_pause_24);
                }
                else{
                    mediaPlayer.pause();
                    playIcon.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }
            }
        });
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(music_url2);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Toast.makeText(Music02.this,"Media Buffering Complete", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }


}