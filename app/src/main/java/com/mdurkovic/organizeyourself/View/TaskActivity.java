package com.mdurkovic.organizeyourself.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mdurkovic.organizeyourself.DB.DatabaseHelper;
import com.mdurkovic.organizeyourself.Model.TaskModel;
import com.mdurkovic.organizeyourself.R;

import java.util.ArrayList;
import java.util.List;


public class TaskActivity extends AppCompatActivity {


    private ListView listView;

    FloatingActionButton fabAddTask;


    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_DELETE = 444;
    private static final int MY_REQUEST_CODE = 1000;


    private final List<TaskModel> taskList = new ArrayList<>();
    private ArrayAdapter<TaskModel> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listView = findViewById(R.id.listViewTask);
        DatabaseHelper db = new DatabaseHelper(this);

        db.createDefaultTasks();

        // onda se popunjava array iz baze
        List<TaskModel> list = db.getAllTasks();
        this.taskList.addAll(list);


        // Define a new Adapter
        // 1 - Context
        // 2 - Layout for the row
        // 3 - ID of the TextView to which the data is written
        // 4 - the List of data
        this.listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.taskList);
        // Assign adapter to ListView
        this.listView.setAdapter(this.listViewAdapter);
        // Register the ListView for Context menu
        registerForContextMenu(this.listView);


        fabAddTask = findViewById(R.id.task_add);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(TaskActivity.this, NewTask.class);
                startActivityForResult(newIntent, MY_REQUEST_CODE);

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo)    {

        super.onCreateContextMenu(menu, view, menuInfo);

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_VIEW , 0, "View Note");
        menu.add(0, MENU_ITEM_EDIT , 2, "Edit Note");
        menu.add(0, MENU_ITEM_DELETE, 4, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final TaskModel selectedTask = (TaskModel) this.listView.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            Intent intent = new Intent(this, ShowNoteActivity.class);
            intent.putExtra("taskTitle", selectedTask.getTitle());
            intent.putExtra("taskDescription", selectedTask.getDescription());
            this.startActivity(intent);
        } else if(item.getItemId() == MENU_ITEM_EDIT ){
            Intent intent = new Intent(this, NewTask.class);
            intent.putExtra("task", selectedTask);
            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent,MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    .setMessage(selectedTask.getTitle()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteTask(selectedTask);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    private void deleteTask(TaskModel task)  {
        DatabaseHelper db = new DatabaseHelper(this);
        db.DeleteTask(task);
        this.taskList.remove(task);
        // Refresh ListView.
        this.listViewAdapter.notifyDataSetChanged();
    }

    // When AddEditNoteActivity completed, it sends feedback.
    // (If you start it using startActivityForResult ())
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            if (needRefresh) {
                this.taskList.clear();
                DatabaseHelper db = new DatabaseHelper(this);
                List<TaskModel> list = db.getAllTasks();
                this.taskList.addAll(list);


                // Notify the data change (To refresh the ListView).
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }



}
