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

import com.mdurkovic.organizeyourself.Model.TaskModel;

public class NewTask extends AppCompatActivity {

    public static final String TAG = "CalendarActivity";

    TaskModel task;
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private int mode;
    private EditText taskTitle;
    private EditText taskDescription;
    FloatingActionButton fabTask;


    private boolean needRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        this.fabTask = this.findViewById(R.id.task_done);
        this.taskTitle = this.findViewById(R.id.task_title);
        this.taskDescription = this.findViewById(R.id.task_description);

        Intent intent = this.getIntent();
        this.task = (TaskModel) intent.getSerializableExtra("task");
        if (task == null) {
            this.mode = MODE_CREATE;
        } else {
            this.mode = MODE_EDIT;
            this.taskTitle.setText(task.getTitle());
            this.taskDescription.setText(task.getDescription());
        }
    }


    public void buttonSaveClicked(View view){
        DatabaseHelper db = new DatabaseHelper(this);

        String title = this.taskTitle.getText().toString();
        String description = this.taskDescription.getText().toString();

        if(title.equals("") || description.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please fill title and description!", Toast.LENGTH_LONG).show();
            return;
        }

        if(mode==MODE_EDIT ) {
            this.task.setTitle(title);
            this.task.setDescription(description);
            db.updateTask(task);
        } else{
            this.task= new TaskModel(title,description);
            db.addData(task);
        }


        this.needRefresh = true;

        // Back to MainActivity.
        this.onBackPressed();

    }

    // When completed this Activity,
    // Send feedback to the Activity called it.
    @Override
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("needRefresh", needRefresh);

        // Set Result
        this.setResult(TaskActivity.RESULT_OK, data);
        super.finish();
    }

}

