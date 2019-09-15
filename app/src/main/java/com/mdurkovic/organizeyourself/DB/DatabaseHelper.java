package com.mdurkovic.organizeyourself.DB;


import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mdurkovic.organizeyourself.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "organizator.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "email";
    public static final String COL_3 = "password";
    public static final String TABLE_TASK_TITLE = "tasks";
    public static final String COL_T1 = "Task_Id";
    public static final String COL_T2 = "TaskTitle";
    public static final String COL_T3 = "TaskDescription";
    public static final String TABLE_VOICE = "voice";
    public static final String VOICE_ID = "id_voice";
    public static final String VOICE_NAME = "FileName";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, email TEXT, password TEXT)");

        db.execSQL("CREATE TABLE tasks (Task_Id INTEGER PRIMARY  KEY AUTOINCREMENT, TaskTitle TEXT, TaskDescription TEXT)");

        db.execSQL("CREATE TABLE voice (id_voice INTEGER PRIMARY  KEY AUTOINCREMENT, FileName TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_TASK_TITLE);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_VOICE);
        onCreate(db);
    }

    public long addUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", user);
        contentValues.put("password", password);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }


    public void addData(TaskModel tasks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues taskValues = new ContentValues();
        taskValues.put(COL_T2, tasks.getTitle());
        taskValues.put(COL_T3, tasks.getDescription());

        db.insert(TABLE_TASK_TITLE, null, taskValues);
        db.close();
    }


    public List<TaskModel> getAllTasks(){

        List<TaskModel> taskList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TASK_TITLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                TaskModel task = new TaskModel();
                task.setId(cursor.getInt(cursor.getColumnIndex(COL_T1)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(COL_T2)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(COL_T3)));
                taskList.add(task);
            }while (cursor.moveToNext());
        }
        return taskList;
    }

    public int getTasksCount(){
        String queryCount = "SELECT * FROM " + TABLE_TASK_TITLE;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryCount,null);

        int count = cursor.getCount();

        cursor.close();

        return  count;
    }

    public void createDefaultTasks() {
        int count = this.getTasksCount();
        if (count == 0) {
            TaskModel task1 = new TaskModel("naslov", "opis");
            TaskModel task2 = new TaskModel("naslov2", "opis2");
            this.addData(task1);
            this.addData(task2);
        }
    }

    public void DeleteTask(TaskModel task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK_TITLE, COL_T1 + " = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public int updateTask(TaskModel task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues taskValues = new ContentValues();
        taskValues.put(COL_T2, task.getTitle());
        taskValues.put(COL_T3, task.getDescription());

        //updating row
        return db.update(TABLE_TASK_TITLE, taskValues, COL_T1 + " = ?", new String[]{ String.valueOf(task.getId())});
    }


//Attempt 1. dodat ime voice-a

    public boolean addFileName(String voiceTitle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues voiceValues = new ContentValues();
        voiceValues.put(VOICE_NAME, voiceTitle);


        long result = db.insert("voice", null, voiceValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getVoiceContents() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor fileName = db.rawQuery(" SELECT * FROM " + TABLE_VOICE, null);
        return fileName;

    }
}




