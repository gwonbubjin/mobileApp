package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tasks.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE_NAME = "task";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_DONE = "done";
    private static final String COL_DUE_DATE = "due_date";
    private static final String COL_PRIORITY = "priority";

    public TaskDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DONE + " INTEGER, " +
                COL_DUE_DATE + " TEXT, " +
                COL_PRIORITY + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_DUE_DATE + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_PRIORITY + " TEXT");
        }
    }

    public void insertTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, task.getTitle());
        values.put(COL_DONE, task.isDone() ? 1 : 0);
        values.put(COL_DUE_DATE, task.getDueDate());
        values.put(COL_PRIORITY, task.getPriority());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DONE, task.isDone() ? 1 : 0);
        values.put(COL_DUE_DATE, task.getDueDate());
        values.put(COL_PRIORITY, task.getPriority());
        db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COL_ID + " DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE));
                boolean done = cursor.getInt(cursor.getColumnIndexOrThrow(COL_DONE)) == 1;
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_DUE_DATE));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRIORITY));
                taskList.add(new Task(id, title, done, dueDate, priority));
            }
            cursor.close();
        }
        db.close();
        return taskList;
    }
}
