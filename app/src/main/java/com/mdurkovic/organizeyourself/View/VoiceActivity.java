package com.mdurkovic.organizeyourself.View;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mdurkovic.organizeyourself.DB.DatabaseHelper;
import com.mdurkovic.organizeyourself.R;
import com.mdurkovic.organizeyourself.Model.Recording;
import com.mdurkovic.organizeyourself.Adapter.RecordingAdapter;

import java.io.File;
import java.util.ArrayList;

public class VoiceActivity extends AppCompatActivity {

    DatabaseHelper db;

    private Toolbar toolbar;
    private RecyclerView recyclerViewRecordings;
    private ArrayList<Recording> recordingArraylist;
    private RecordingAdapter recordingAdapter;
    FloatingActionButton addVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        TextView voiceView = findViewById(R.id.textViewRecordingname);

        db = new DatabaseHelper(this);

        final ArrayList<String> theList = new ArrayList<>();
        final Cursor fileName = db.getVoiceContents();


            while(fileName.moveToNext()){
                theList.add(fileName.getString(1));
                 TextView taskadapter = new TextView(this);
                voiceView.setText((CharSequence) taskadapter);

            }



        addVoice = findViewById(R.id.addVoice);
        addVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(VoiceActivity.this, NewVoice.class);
                startActivity(newIntent);
            }
        });


        initViews();

        fetchRecordings();
    }

    private void initViews() {
        recordingArraylist = new ArrayList<Recording>();
        /** setting up the toolbar  **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Recording List");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        setSupportActionBar(toolbar);

        /** enabling back button ***/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** setting up recyclerView **/
        recyclerViewRecordings = (RecyclerView) findViewById(R.id.recyclerViewRecordings);
        recyclerViewRecordings.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewRecordings.setHasFixedSize(true);


    }

    private void fetchRecordings() {

        File root = android.os.Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        if( files!=null ){

            for (int i = 0; i < files.length; i++) {

                Log.d("Files", "FileName:" + files[i].getName());
                String fileName = files[i].getName();
                String recordingUri = root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/" + fileName;

                Recording recording = new Recording(recordingUri,fileName,false);
                recordingArraylist.add(recording);
            }

            setAdaptertoRecyclerView();

        }

    }

    private void setAdaptertoRecyclerView() {
        recordingAdapter = new RecordingAdapter(this,recordingArraylist);
        recyclerViewRecordings.setAdapter(recordingAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

}