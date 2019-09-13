package com.mdurkovic.organizeyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        Intent intent = getIntent();
        String val = intent.getStringExtra("description");

        TextView textPop = findViewById(R.id.textPop);
        textPop.setText(val);
    }
}
