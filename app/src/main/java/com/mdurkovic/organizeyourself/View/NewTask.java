package com.mdurkovic.organizeyourself.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mdurkovic.organizeyourself.DB.DatabaseHelper;
import com.mdurkovic.organizeyourself.R;


public class NewTask extends AppCompatActivity {

    public static final String TAG = "CalendarActivity";


    DatabaseHelper db;
    FloatingActionButton fabTask;
    EditText taskTitle;
    EditText taskDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        db = new DatabaseHelper(this);

        fabTask = findViewById(R.id.task_done);
        taskTitle = findViewById(R.id.task_title);
        taskDescription = findViewById(R.id.task_description);


        fabTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newEntryTitle = taskTitle.getText().toString();
                String newEntryDecription = taskDescription.getText().toString();

                if (taskTitle.length() != 0 && taskDescription.length() != 0) {
                    AddData(newEntryTitle, newEntryDecription);
                    taskTitle.setText("");
                    taskDescription.setText("");
                    Intent newIntent = new Intent(NewTask.this, TaskActivity.class);
                    startActivity(newIntent);
                } else {
                    Toast.makeText(NewTask.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void AddData(String newEntryTitle, String newEntryDecription) {
        boolean insertData = db.addData(newEntryTitle, newEntryDecription);
        if (insertData == true) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }

}

