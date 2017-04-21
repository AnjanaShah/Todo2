package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.todo.Titles.MEETING_TABLE_ALARM;
import static com.example.todo.Titles.MEETING_TABLE_CLIENT;
import static com.example.todo.Titles.MEETING_TABLE_DATE;
import static com.example.todo.Titles.MEETING_TABLE_DESCRIPTION;
import static com.example.todo.Titles.MEETING_TABLE_ID;
import static com.example.todo.Titles.MEETING_TABLE_NAME;
import static com.example.todo.Titles.MEETING_TABLE_PLACE;
import static com.example.todo.Titles.MEETING_TABLE_TITLE;

/**
 * Created by Chhavi on 20-Apr-17.
 */

public class MeetingHelper extends SQLiteOpenHelper {


    public MeetingHelper(Context context) {
        super(context, "TODO_DATABASE", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_query="CREATE TABLE "+MEETING_TABLE_NAME+"("+MEETING_TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +MEETING_TABLE_TITLE+" TEXT,"+MEETING_TABLE_DATE+" INTEGER ,"
                +MEETING_TABLE_CLIENT+" TEXT,"+MEETING_TABLE_PLACE+" TEXT,"+MEETING_TABLE_ALARM+" ,"
                +MEETING_TABLE_DESCRIPTION+" TEXT);";
        db.execSQL(sql_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
