package com.jayani.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "Task_database";
    //Database Table name
    private static final String TABLE_NAME = "Tasks";
    //Table columns
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DISCRIPTION = "discription";
    public static final String DUEDATE = "duedate";

    private SQLiteDatabase sqLiteDatabase;


    //creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"("+ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " TEXT NOT NULL,"+DISCRIPTION+" TEXT NOT NULL,"+DUEDATE+" TEXT NOT NULL);";
    //Constructor
    public DataBaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(TaskModel taskModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.TITLE, taskModel.getTitle());
        contentValues.put(DataBaseHelper.DISCRIPTION, taskModel.getDiscription());
        contentValues.put(DataBaseHelper.DUEDATE, taskModel.getDueDate());

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DataBaseHelper.TABLE_NAME, null,contentValues);
    }

    public ArrayList<TaskModel> getTaskList(){
        String sql = "select * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<TaskModel> storeTasks = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String discription = cursor.getString(2);
                String duedate = cursor.getString(3);
                storeTasks.add(new TaskModel(id,title,discription, duedate));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeTasks;
    }

    public void updateTask(TaskModel taskModel){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("tessssst",taskModel.getDiscription());
        contentValues.put(DataBaseHelper.ID,taskModel.getId());
        contentValues.put(DataBaseHelper.TITLE,taskModel.getTitle());
        contentValues.put(DataBaseHelper.DISCRIPTION,taskModel.getDiscription());
        contentValues.put(DataBaseHelper.DUEDATE,taskModel.getDueDate());
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID + " = ?" , new String[]
                {String.valueOf(taskModel.getId())});

        sqLiteDatabase.close();
    }

    public void deleteTask(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID + " = ? ", new String[]
                {String.valueOf(id)});

        sqLiteDatabase.close();
    }
}
