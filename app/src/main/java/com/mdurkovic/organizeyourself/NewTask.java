package com.mdurkovic.organizeyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewTask extends AppCompatActivity {

    DatabaseHelper db;
    FloatingActionButton fabTask;
    EditText taskDescription;
    CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        db = new DatabaseHelper(this);
        taskDescription = findViewById(R.id.task_description);
        calendarView = findViewById(R.id.calendarView);
        fabTask = findViewById(R.id.task_done);


        fabTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newEntry = taskDescription.getText().toString();
                if (taskDescription.length() != 0) {
                    AddData(newEntry);
                    taskDescription.setText("");
                } else {
                    Toast.makeText(NewTask.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }


                Intent newIntent = new Intent(NewTask.this, TaskActivity.class);
                startActivity(newIntent);

            }
        });
    }

    public void AddData(String newEntry) {
        boolean insertData = db.addData(newEntry);
        if (insertData == true) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }
}

