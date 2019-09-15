package com.mdurkovic.organizeyourself.DB;


import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mdurkovic.organizeyourself.Model.TaskModel;

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
        super(context, DATABASE_NAME, null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, email TEXT, password TEXT)");

        db.execSQL("CREATE TABLE tasks (ID INTEGER PRIMARY  KEY AUTOINCREMENT, TaskTitle TEXT, TaskDescription TEXT)");

        db.execSQL("CREATE TABLE voice (ID INTEGER PRIMARY  KEY AUTOINCREMENT, FileName TEXT)");

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


    public boolean addData(String newEntryTitle, String newEntryDecription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues taskValues = new ContentValues();
        taskValues.put(COL_T2, newEntryTitle);
        taskValues.put(COL_T3, newEntryDecription);


        long result = db.insert("tasks", null, taskValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(" SELECT * FROM " + TABLE_TASK_TITLE, null);
        return data;
    }


    public void deleteTask(TaskModel task) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK_TITLE, COL_T1 + " = ?" + "= ?",
                new String[] { String.valueOf(task.getTaskId()) });
        db.close();
    }

//public void deleteTask(int id, String title, String description){
//        SQLiteDatabase db = this.getWritableDatabase();
//    String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//            + COL_T1 + " = '" + id + "'" +
//            " AND " + COL_T2 + " = '" + title + "='" + COL_T3 + description +"'";
//    db.execSQL(query);
//}





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



