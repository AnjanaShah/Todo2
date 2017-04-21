package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.todo.Titles.NOTES_TABLE_ALARM;
import static com.example.todo.Titles.NOTES_TABLE_DATE;
import static com.example.todo.Titles.NOTES_TABLE_DESCRIPTION;
import static com.example.todo.Titles.NOTES_TABLE_ID;
import static com.example.todo.Titles.NOTES_TABLE_NAME;
import static com.example.todo.Titles.NOTES_TABLE_SUBJECT;
import static com.example.todo.Titles.NOTES_TABLE_TITLE;

/**
 * Created by Chhavi on 21-Apr-17.
 */

public class NotesHelper extends SQLiteOpenHelper {
    public NotesHelper (Context context) {
        super(context, "TODO_DATABASE", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_query="CREATE TABLE "+NOTES_TABLE_NAME+"("+NOTES_TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NOTES_TABLE_TITLE+" TEXT,"+NOTES_TABLE_DATE+" INTEGER ,"
                +NOTES_TABLE_SUBJECT+" TEXT,"+NOTES_TABLE_ALARM+" ,"
                +NOTES_TABLE_DESCRIPTION+" TEXT);";
        db.execSQL(sql_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
}
