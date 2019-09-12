package com.mdurkovic.organizeyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonTask, buttonVideo, buttonVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonTask = findViewById(R.id.btnTask);
        buttonVideo = findViewById(R.id.btnVideo);
        buttonVoice = findViewById(R.id.btnVoice);

        buttonTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTask = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(intentTask);
            }
        });

        buttonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVideo = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intentVideo);
            }
        });

        buttonVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVoice = new Intent(MainActivity.this, VoiceActivity.class);
                startActivity(intentVoice;
            }
        });


    }
}
