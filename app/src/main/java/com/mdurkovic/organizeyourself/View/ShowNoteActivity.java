package com.mdurkovic.organizeyourself.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.mdurkovic.organizeyourself.R;

public class ShowNoteActivity extends AppCompatActivity {

    TextView text_titleShow;
    TextView text_descriptinShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        text_titleShow = findViewById(R.id.text_titleShow);
        text_descriptinShow = findViewById(R.id.text_descriptionShow);

        String titleShow = getIntent().getStringExtra("taskTitle");
        String titleDescription = getIntent().getStringExtra("taskDescription");

        text_titleShow.setText(titleShow);
        text_descriptinShow.setText(titleDescription);
    }
}
