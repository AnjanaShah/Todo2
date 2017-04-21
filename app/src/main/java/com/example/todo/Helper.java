package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.todo.Titles.EXPENSE_TABLE_ALARM;
import static com.example.todo.Titles.EXPENSE_TABLE_CATEGORY;
import static com.example.todo.Titles.EXPENSE_TABLE_DATE;
import static com.example.todo.Titles.EXPENSE_TABLE_DESCRIPTION;
import static com.example.todo.Titles.EXPENSE_TABLE_ID;
import static com.example.todo.Titles.EXPENSE_TABLE_NAME;
import static com.example.todo.Titles.EXPENSE_TABLE_PRICE;
import static com.example.todo.Titles.EXPENSE_TABLE_QUANTITY;
import static com.example.todo.Titles.EXPENSE_TABLE_TITLE;
import static com.example.todo.Titles.MEETING_TABLE_ALARM;
import static com.example.todo.Titles.MEETING_TABLE_CLIENT;
import static com.example.todo.Titles.MEETING_TABLE_DATE;
import static com.example.todo.Titles.MEETING_TABLE_DESCRIPTION;
import static com.example.todo.Titles.MEETING_TABLE_ID;
import static com.example.todo.Titles.MEETING_TABLE_NAME;
import static com.example.todo.Titles.MEETING_TABLE_PLACE;
import static com.example.todo.Titles.MEETING_TABLE_TITLE;
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

public class Helper extends SQLiteOpenHelper {
    public Helper(Context context) {
        super(context, "TODO_DATABASE", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_query="CREATE TABLE "+MEETING_TABLE_NAME+"("+MEETING_TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +MEETING_TABLE_TITLE+" TEXT,"+MEETING_TABLE_DATE+" INTEGER ,"
                +MEETING_TABLE_CLIENT+" TEXT,"+MEETING_TABLE_PLACE+" TEXT,"+MEETING_TABLE_ALARM+" INTEGER, "
                +MEETING_TABLE_DESCRIPTION+" TEXT);";
        String sql_query1="CREATE TABLE "+NOTES_TABLE_NAME+"("+NOTES_TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NOTES_TABLE_TITLE+" TEXT,"+NOTES_TABLE_DATE+" INTEGER ,"
                +NOTES_TABLE_SUBJECT+" TEXT,"+NOTES_TABLE_ALARM+" INTEGER, "
                +NOTES_TABLE_DESCRIPTION+" TEXT);";
        String sql_query3="CREATE TABLE "+EXPENSE_TABLE_NAME+"("+EXPENSE_TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +EXPENSE_TABLE_TITLE+" TEXT,"+EXPENSE_TABLE_CATEGORY+" TEXT,"+EXPENSE_TABLE_DATE+" LONG ,"
                +EXPENSE_TABLE_PRICE+" REAL,"+EXPENSE_TABLE_QUANTITY+" INTEGER,"+EXPENSE_TABLE_ALARM+" INTEGER,"+EXPENSE_TABLE_DESCRIPTION+" TEXT);";
        db.execSQL(sql_query3);
        db.execSQL(sql_query);
        db.execSQL(sql_query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
