package com.mdurkovic.organizeyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mdurkovic.organizeyourself.DB.DatabaseHelper;

public class PopUp extends AppCompatActivity {
    DatabaseHelper db;
    FloatingActionButton btnDelete;
    TextView textPop;

    private String selectedDescription;
    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        db = new DatabaseHelper(this);
//
//selectedDescription = ;
//selectedID = ;
//selectedName= ;


        textPop = findViewById(R.id.textPop);
        textPop.setText("DELETE NOTE?");


//        btnDelete = findViewById(R.id.task_delete);
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                db.deleteTask(selectedID, selectedName, selectedDescription);
//            }
//        });


    }
}
