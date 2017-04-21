package com.example.todo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Chhavi on 21-Apr-17.
 */

public class NotesAddActivity extends AppCompatActivity {
    int hour, minute;
    long date,id;
    long alarm;
    boolean editItem;
    public final static String ID = "_id";
    Helper notesHelper = new Helper(NotesAddActivity.this);
    EditText notesTitle, notesDate, notesPlace, notesSubject, notesDescription, notesAlarm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_add);
        notesTitle = (EditText) findViewById(R.id.notesTitle);
        notesDate = (EditText) findViewById(R.id.notesDate);
        notesSubject = (EditText) findViewById(R.id.notesSubject);
        notesDescription = (EditText) findViewById(R.id.notesDescription);
        notesAlarm = (EditText) findViewById(R.id.notesAlarm);

        notesDate.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Date d = new Date();
                                             d.getTime();
                                             Calendar newCalender = Calendar.getInstance();
                                             int month = newCalender.get(Calendar.MONTH);
                                             int year = newCalender.get(Calendar.YEAR);
                                             int day= newCalender.get(Calendar.DATE);
                                             showDatePicker(NotesAddActivity.this, year, month, day);

                                         }

                                     }
        );

        notesAlarm.setOnClickListener(new View.OnClickListener() {
            Calendar time;
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NotesAddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SimpleDateFormat")
                            @Override
                            public void onTimeSet(TimePicker timePicker,
                                                  int selectedHour, int selectedMinute) {

                                time = Calendar.getInstance();

                                time.set(Calendar.HOUR_OF_DAY, selectedHour);

                                time.set(Calendar.MINUTE, selectedMinute);
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "hh:mm a");
                                alarm=time.getTime().getTime();
                                notesAlarm.setText(format.format(time.getTime()));

                                Log.i("MEETIINGAdd", "2:" + time.getTime());
                                hour = selectedHour;
                                minute = selectedMinute;
                            }
                        }, hour, minute, false);// Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                AlarmManager am = (AlarmManager) NotesAddActivity.this.getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(NotesAddActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotesAddActivity.this, 1, i, 0);
                Log.i("MEETIINGAdd", "1:" + AlarmManager.RTC);

                Log.i("MEETIINGAdd", "3:" + pendingIntent);
                /*if(time.getTime()!=null)
                {
                    Log.i("MEETIINGAdd","2:"+time.getTime());
                    am.set(AlarmManager.RTC, time.getTime().getTime(), pendingIntent);
                    Log.i("ExpeneseAdd","set intent");
                }*/

            }

        });

        Intent i = getIntent();
        editItem = i.getBooleanExtra("ExpenseEdit", false);
        if (editItem) {
            id = i.getLongExtra(ID, -1);Log.i("ExpenseAddTag","main id:"+id);
            if (id != -1) {
                Log.i("ExpenseAddTag","id:"+id);
                Notes e;
                SQLiteDatabase db = notesHelper.getReadableDatabase();
                Cursor c = db.query(Titles.NOTES_TABLE_NAME, null, Titles.NOTES_TABLE_ID + "=?", new String[]{"" + id}, null, null, null);
                Log.i("ExpenseAddTag", "cursor" + c);
                //db.query(Titles.EXPENSE_TABLE_NAME,null,Titles.EXPENSE_TABLE_ID+"="+id,null, null, null);
                if (c != null && c.moveToFirst()) {
                    long id = c.getLong(c.getColumnIndex(Titles.NOTES_TABLE_ID));
                    String title = c.getString(c.getColumnIndex(Titles.NOTES_TABLE_TITLE));
                    long d = c.getLong(c.getColumnIndex(Titles.NOTES_TABLE_DATE));
                    // Date date = new Date(epochTime);
                    String subject = c.getString(c.getColumnIndex(Titles.NOTES_TABLE_SUBJECT));
                    int alarm = c.getInt(c.getColumnIndex(Titles.NOTES_TABLE_ALARM));

                    String description = c.getString(c.getColumnIndex(Titles.NOTES_TABLE_DESCRIPTION));
                    notesTitle.setText(title);
                    Log.i("ExpenseAddTag","alarm:"+alarm );
                    String dateString = new Date(d).toString();

                    notesDate.setText(dateString);
                    notesSubject.setText("" + subject);
                    notesAlarm.setText(""+alarm);
                    notesDescription.setText(description);
                }
            }
        }

    }
    //TODO edit item



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();
        if (menu_id == R.id.doneItem) {

            String title;

            String subject;
            String description;


            title = notesTitle.getText().toString();
            //date=notesDate.getText().toString();
            description = "" + notesDescription.getText().toString();
           // place = (notesPlace.getText().toString());
            subject = (notesSubject.getText().toString());

            //errror if following fields are empty
            if (title.trim().isEmpty()) {
                notesTitle.setError("Title can't be empty ");
                return false;
            }

            if (notesDate.getText().toString().trim().isEmpty()) {
                notesDate.setError("Date can't be empty ");
                return false;
            }
            if (notesSubject.getText().toString().trim().isEmpty()) {
                notesSubject.setError("Subject can't be empty ");
                return false;
            }


            if (date == 0.0) {
                date = new Date(System.currentTimeMillis()).getTime();
            }

            SQLiteDatabase db = notesHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Titles.NOTES_TABLE_TITLE, title);
            cv.put(Titles.NOTES_TABLE_DATE, date);
            cv.put(Titles.NOTES_TABLE_SUBJECT, (subject));
            cv.put(Titles.NOTES_TABLE_DESCRIPTION, description);
            cv.put(Titles.NOTES_TABLE_ALARM, alarm);
            if (!editItem) {
                long rowId = db.insert(Titles.NOTES_TABLE_NAME, null, cv);
                Log.i("NotesAddTag", "Result Inserted row Id " + rowId);
                Intent i=new Intent();
                Log.i("ExpenseAddTag", "Result: "+ Activity.RESULT_OK);
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;
            } else {
                Log.i("NtesAddActivity","ïd:"+id);

                long rowId=db.update(Titles.NOTES_TABLE_NAME, cv, Titles.NOTES_TABLE_ID + "=?", new String[]{"" + id});
                Log.i("NotesAddTag", "Result Inserted row Id " + rowId);
                Intent i=new Intent();
                Log.i("ExpenseAddTag", "Result: "+ Activity.RESULT_OK);
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;

            }
        }
        return  false;
    }


    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {
        Log.i("Notesdate","date"+initialYear);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        date = calendar.getTime().getTime();
                        Log.i("NotesAdd","date"+date);
                        notesDate.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        datePickerDialog.show();

    }
}
