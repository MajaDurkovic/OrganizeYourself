package com.mdurkovic.organizeyourself.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mdurkovic.organizeyourself.DB.DatabaseHelper;
import com.mdurkovic.organizeyourself.Model.TaskModel;
import com.mdurkovic.organizeyourself.PopUp;
import com.mdurkovic.organizeyourself.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    DatabaseHelper db;
    FloatingActionButton fabAddTask;
    ListView listView;

    private static final int ITEM_DELETE = 111;
    ArrayAdapter<String> listViewAdapter;
    final ArrayList<String> theList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listView = findViewById(R.id.listViewTask);
        registerForContextMenu(listView);
        db = new DatabaseHelper(this);


        // onda se popunjava array iz baze


        final Cursor data = db.getListContents();

        if (data.getCount() == 0) {
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1) + "\n" + data.getString(2));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);

            }
        }

//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent intentPopup = new Intent(getApplicationContext(), PopUp.class);
//                startActivity(intentPopup);
//
//            }
//        });

        fabAddTask = findViewById(R.id.task_add);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(TaskActivity.this, NewTask.class);
                startActivity(newIntent);
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, ITEM_DELETE, 0, "DELETE?");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
               final TaskModel selectedTask = (TaskModel) this.listView.getItemAtPosition(info.position);

        if (item.getItemId() == ITEM_DELETE) {
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    .setMessage(selectedTask.getTitle() + ". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteTask(selectedTask);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            return false;
        }
        return true;
    }

    private void deleteTask(TaskModel task) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.deleteTask(task);
        this.theList.remove(task);
        // Refresh ListView.
        this.listViewAdapter.notifyDataSetChanged();
    }


}


