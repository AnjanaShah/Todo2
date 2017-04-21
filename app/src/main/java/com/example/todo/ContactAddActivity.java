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

public class ContactAddActivity extends AppCompatActivity {
    long id;
    long date;
    long alarm=0;
    boolean editContact;
    Helper helper= new Helper(ContactAddActivity.this);
    EditText contactTitle,contactDetail,contactDate,contactSubject,contactDescription,contactAlarm;
    int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        contactTitle = (EditText) findViewById(R.id.contactTitle);
        contactDetail = (EditText) findViewById(R.id.contactDetail);
        contactDate = (EditText) findViewById(R.id.contactDate);
        contactSubject = (EditText) findViewById(R.id.contactSubject);
        contactDescription = (EditText) findViewById(R.id.contactDescription);
        contactAlarm=(EditText)findViewById(R.id.contactAlarm);

        contactDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d = new Date();
                d.getTime();
                Calendar newCalender = Calendar.getInstance();
                int month = newCalender.get(Calendar.MONTH);
                int year = newCalender.get(Calendar.YEAR);
                int day=newCalender.get(Calendar.DATE);
                showDatePicker(ContactAddActivity.this, year, month, day);

            }
        });
        contactAlarm.setOnClickListener(new View.OnClickListener() {
            Calendar time;
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ContactAddActivity.this,
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
                                contactAlarm.setText(format.format(time.getTime()));
                                Log.i("ExpenseAdd","2:"+time.getTime());
                                hour = selectedHour;
                                minute = selectedMinute;
                            }
                        }, hour, minute, false);// Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                AlarmManager am = (AlarmManager) ContactAddActivity.this.getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(ContactAddActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ContactAddActivity.this,1,i, 0);
                Log.i("ExpenseAdd","1:"+AlarmManager.RTC);

                Log.i("ExpenseAdd","3:"+pendingIntent);
                /*if(time.getTime()!=null)
                {
                    Log.i("ExpenseAdd","2:"+time.getTime());
                    am.set(AlarmManager.RTC, time.getTime().getTime(), pendingIntent);
                    Log.i("ExpeneseAdd","set intent");
                }*/

            }
        });
        //TODO edit activity

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expense_add_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id=item.getItemId();
        if(menu_id==R.id.doneItem) {

            String title,description,details,subject;

            title=contactTitle.getText().toString();
            details=""+contactDetail.getText().toString();
            description=""+contactDescription.getText().toString();
            subject=(contactSubject.getText().toString());
            //errror if following fields are empty
            if(title.trim().isEmpty()){
                contactTitle.setError("Title can't be empty ");
                return false;
            }

            if(details.trim().isEmpty()){
                contactDetail.setError("Details can't be empty ");
                return false;
            }

            if (date == 0.0) {
                date = new Date(System.currentTimeMillis()).getTime();
            }


            //TODO add date,alarm ;

            //adding the values to the database
            SQLiteDatabase db= helper.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(Titles.CONTACT_TABLE_TITLE, title);
            cv.put(Titles.CONTACT_TABLE_DETAIL, details);
            cv.put(Titles.CONTACT_TABLE_SUBJECT, subject);
            cv.put(Titles.CONTACT_TABLE_ALARM, alarm);
            cv.put(Titles.CONTACT_TABLE_DATE,date);
            cv.put(Titles.EXPENSE_TABLE_DESCRIPTION,description);
            if(!editContact) {
                long rowId = db.insert(Titles.CONTACT_TABLE_NAME, null, cv);
                Log.i("CONTACTAddTag", "Result Inserted row Id " + rowId);
                Intent i=new Intent();
                Log.i("ExpenseAddTag", "Result: "+ Activity.RESULT_OK);
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;
            }
            else
            {
                db.update(Titles.CONTACT_TABLE_NAME,cv,Titles.CONTACT_TABLE_ID+"=?",new String[] {""+id});
                Intent i=new Intent();
                Log.i("ExpenseAddTag", "Result: "+ Activity.RESULT_OK);
                setResult(Activity.RESULT_OK,i);
                finish();
                return  true;
            }
            //view the done item on list screen


        }
        return false;
    }

    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {
        Log.i("Expensedate","date"+initialYear);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        date = calendar.getTime().getTime();
                        Log.i("ExpenseAdd","date"+date);
                        contactDate.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        datePickerDialog.show();


    }
}
