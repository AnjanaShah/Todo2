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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static android.R.attr.id;


public class MeetingAddActivity extends AppCompatActivity {
    int hour, minute;
    long date,id;
    long alarm;
    boolean editItem;
    public final static String ID = "_id";
    Helper meetingHelper = new Helper(MeetingAddActivity.this);
    EditText meetingTitle, meetingDate, meetingPlace, meetingClient, meetingDescription, meetingAlarm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_add);
        meetingTitle = (EditText) findViewById(R.id.meetingTitle);
        meetingDate = (EditText) findViewById(R.id.meetingDate);
        meetingPlace = (EditText) findViewById(R.id.meetingPlace);
        meetingClient = (EditText) findViewById(R.id.meetingClient);
        meetingDescription = (EditText) findViewById(R.id.meetingDescription);
        meetingAlarm = (EditText) findViewById(R.id.meetingAlarm);

        meetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d = new Date();
                d.getTime();
                Calendar newCalender = Calendar.getInstance();
                int month = newCalender.get(Calendar.MONTH);
                int year = newCalender.get(Calendar.YEAR);
                int day= newCalender.get(Calendar.DATE);
                showDatePicker(MeetingAddActivity.this, year, month, day);

            }

            }
        );

        meetingAlarm.setOnClickListener(new View.OnClickListener() {
            Calendar time;
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MeetingAddActivity.this,
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
                                meetingAlarm.setText(format.format(time.getTime()));

                                Log.i("MEETIINGAdd", "2:" + time.getTime());
                                hour = selectedHour;
                                minute = selectedMinute;
                            }
                        }, hour, minute, false);// Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                AlarmManager am = (AlarmManager) MeetingAddActivity.this.getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(MeetingAddActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MeetingAddActivity.this, 1, i, 0);
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
                Meeting e;
                SQLiteDatabase db = meetingHelper.getReadableDatabase();
                Cursor c = db.query(Titles.MEETING_TABLE_NAME, null, Titles.MEETING_TABLE_ID + "=?", new String[]{"" + id}, null, null, null);
                Log.i("ExpenseAddTag", "cursor" + c);
                //db.query(Titles.EXPENSE_TABLE_NAME,null,Titles.EXPENSE_TABLE_ID+"="+id,null, null, null);
                if (c != null && c.moveToFirst()) {
                    long id = c.getLong(c.getColumnIndex(Titles.MEETING_TABLE_ID));
                    String title = c.getString(c.getColumnIndex(Titles.MEETING_TABLE_TITLE));
                    String place = c.getString(c.getColumnIndex(Titles.MEETING_TABLE_PLACE));
                    long d = c.getLong(c.getColumnIndex(Titles.MEETING_TABLE_DATE));
                    // Date date = new Date(epochTime);
                    String client = c.getString(c.getColumnIndex(Titles.MEETING_TABLE_CLIENT));
                    int alarm = c.getInt(c.getColumnIndex(Titles.MEETING_TABLE_ALARM));

                    String description = c.getString(c.getColumnIndex(Titles.MEETING_TABLE_DESCRIPTION));
                    meetingTitle.setText(title);
                    Log.i("ExpenseAddTag","alarm:"+alarm );
                    meetingPlace.setText(place);
                    String dateString = new Date(d).toString();

                    meetingDate.setText(dateString);
                    meetingClient.setText("" + client);
                    meetingAlarm.setText(""+alarm);
                    meetingDescription.setText(description);
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
        int idMenu = item.getItemId();
        if (idMenu== R.id.doneItem) {

            String title;

            String client;
            String place;
            String description;


            title = meetingTitle.getText().toString();
            //date=meetingDate.getText().toString();
            description = "" + meetingDescription.getText().toString();
            place = (meetingPlace.getText().toString());
            client = (meetingClient.getText().toString());

            //errror if following fields are empty
            if (title.trim().isEmpty()) {
                meetingTitle.setError("Title can't be empty ");
                return false;
            }

            if (meetingDate.getText().toString().trim().isEmpty()) {
                meetingDate.setError("Date can't be empty ");
                return false;
            }
            if (meetingClient.getText().toString().trim().isEmpty()) {
                meetingClient.setError("Client can't be empty ");
                return false;
            }
            if (meetingPlace.getText().toString().trim().isEmpty()) {
                meetingPlace.setError("Place can't be empty ");
                return false;
            }

            if (date == 0.0) {
                date = new Date(System.currentTimeMillis()).getTime();
            }

            SQLiteDatabase db = meetingHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Titles.MEETING_TABLE_TITLE, title);
            cv.put(Titles.MEETING_TABLE_DATE, date);
            cv.put(Titles.MEETING_TABLE_PLACE, (place));
            cv.put(Titles.MEETING_TABLE_CLIENT, (client));
            cv.put(Titles.MEETING_TABLE_DESCRIPTION, description);
            cv.put(Titles.MEETING_TABLE_ALARM, alarm);
            if (!editItem) {
                long rowId = db.insert(Titles.MEETING_TABLE_NAME, null, cv);
                Log.i("MeetingAddTag", "Result Inserted row Id " + rowId);
                Intent i=new Intent();
                Log.i("ExpenseAddTag", "Result: "+Activity.RESULT_OK);
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;
            } else {
                Log.i("NtesAddActivity","ïd:"+id);

                db.update(Titles.MEETING_TABLE_NAME, cv, Titles.MEETING_TABLE_ID + "=?", new String[]{"" + id});
                //db.update()
                Intent i=new Intent();
                Log.i("ExpenseAddTag", "Result: "+Activity.RESULT_OK);
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;
            }
        }
        return false;

    }


        public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {
            Log.i("Meetingdate","date"+initialYear);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, day);
                            date = calendar.getTime().getTime();
                            Log.i("MeetingAdd","date"+date);
                            meetingDate.setText(day + "/" + (month + 1) + "/" + year);
                        }
                    }, initialYear, initialMonth, initialDay);

            datePickerDialog.show();

        }
}

