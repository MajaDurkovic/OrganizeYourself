package com.mdurkovic.organizeyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    DatabaseHelper db;

    FloatingActionButton fabAddTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        ListView listView = findViewById(R.id.listViewTask);
        db = new DatabaseHelper(this);


        // onda se popunjava array iz baze

        final ArrayList<String> theList = new ArrayList<>();
        final Cursor data = db.getListContents();

        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);

            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intentPopup = new Intent(getApplicationContext(), PopUp.class);
                intentPopup.putExtra("description", String.valueOf(data));
                startActivity(intentPopup);

            }
        });

        fabAddTask = findViewById(R.id.task_add);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(TaskActivity.this, NewTask.class );
                startActivity(newIntent);
            }
        });
    }
}
