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

import static com.example.todo.Titles.ID;

public class ExpenseAddActivity extends AppCompatActivity {

    long id;
    long date;
    long alarm;
    boolean editItem;
    Helper expenseHelper=new Helper(ExpenseAddActivity.this);
    EditText itemTitle,itemCategory,itemDobydate,itemPrice,itemQuantity,itemDescription,itemAlarm;
    int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_add);
        itemTitle = (EditText) findViewById(R.id.itemTitle);
        itemCategory = (EditText) findViewById(R.id.itemCategory);
        itemDobydate = (EditText) findViewById(R.id.itemDobydate);
        itemPrice = (EditText) findViewById(R.id.itemPrice);
        itemQuantity = (EditText) findViewById(R.id.itemQuantity);
        itemDescription = (EditText) findViewById(R.id.itemDescription);
        itemAlarm=(EditText)findViewById(R.id.itemAlarm);

        itemDobydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d = new Date();
                d.getTime();
                Calendar newCalender = Calendar.getInstance();
                int month = newCalender.get(Calendar.MONTH);
                int year = newCalender.get(Calendar.YEAR);
                int day= newCalender.get(Calendar.DATE);
                showDatePicker(ExpenseAddActivity.this, year, month, day);

            }
        });

        itemAlarm.setOnClickListener(new View.OnClickListener() {
            Calendar time;
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ExpenseAddActivity.this,
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
                                itemAlarm.setText(format.format(time.getTime()));
                                Log.i("ExpenseAdd","2:"+time.getTime());
                                hour = selectedHour;
                                minute = selectedMinute;
                            }
                        }, hour, minute, false);// Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                AlarmManager am = (AlarmManager) ExpenseAddActivity.this.getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(ExpenseAddActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ExpenseAddActivity.this,1,i, 0);
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
        Intent i = getIntent();
        editItem = i.getBooleanExtra("ExpenseEdit", false);
        if (editItem) {
            id = i.getLongExtra(ID, -1);Log.i("ExpenseAddTag","main id:"+id);
            if (id != -1) {
                Log.i("ExpenseAddTag","id:"+id);
                Expense e;
                SQLiteDatabase db = expenseHelper.getReadableDatabase();
                Cursor c = db.query(Titles.EXPENSE_TABLE_NAME, null, Titles.EXPENSE_TABLE_ID + "=?", new String[]{"" + id}, null, null, null);
                Log.i("ExpenseAddTag", "cursor" + c);
                //db.query(Titles.EXPENSE_TABLE_NAME,null,Titles.EXPENSE_TABLE_ID+"="+id,null, null, null);
                if (c != null && c.moveToFirst()) {
                    long id = c.getLong(c.getColumnIndex(Titles.EXPENSE_TABLE_ID));
                    String title = c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_TITLE));
                    String category = c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_CATEGORY));
                    long d = c.getLong(c.getColumnIndex(Titles.EXPENSE_TABLE_DATE));
                    // Date date = new Date(epochTime);
                    double price = c.getDouble(c.getColumnIndex(Titles.EXPENSE_TABLE_PRICE));
                    int quantity = c.getInt(c.getColumnIndex(Titles.EXPENSE_TABLE_QUANTITY));
                    long a = c.getLong(c.getColumnIndex(Titles.EXPENSE_TABLE_ALARM));
                    String description = c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_DESCRIPTION));
                    itemTitle.setText(title);
                    Log.i("ExpenseAddTag","title:"+title );
                    itemCategory.setText(category);
                    String dateString = new Date(d).toString();

                    itemDobydate.setText(dateString);
                    itemPrice.setText("" + price);
                    itemQuantity.setText("" + quantity);
                    itemDescription.setText(description);
                    itemAlarm.setText(""+a);
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expense_add_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_menu=item.getItemId();
        if(id_menu==R.id.doneItem) {

            String title,description,category,sprice,squantity;
            Double price;
            Integer quantity;

            title=itemTitle.getText().toString();
            category=""+itemCategory.getText().toString();
            description=""+itemDescription.getText().toString();
            sprice=(itemPrice.getText().toString());
            squantity=(itemQuantity.getText().toString());
            //errror if following fields are empty
            if(title.trim().isEmpty()){
                itemTitle.setError("Title can't be empty ");
                return false;
            }

            if(itemPrice.getText().toString().trim().isEmpty()){
                itemPrice.setError("Price can't be empty ");
                return false;
            }
            if(itemQuantity.getText().toString().trim().isEmpty()){
                itemQuantity.setError("Price can't be empty ");
                return false;
            }

            if (date == 0.0) {
                date = new Date(System.currentTimeMillis()).getTime();
            }

            price=Double.parseDouble(itemPrice.getText().toString());
            quantity=Integer.parseInt(itemQuantity.getText().toString());

            //TODO add date,alarm ;

            //adding the values to the database
            Helper expenseHelper=new Helper(ExpenseAddActivity.this);
            SQLiteDatabase db= expenseHelper.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(Titles.EXPENSE_TABLE_TITLE, title);
            cv.put(Titles.EXPENSE_TABLE_CATEGORY, category);
            cv.put(Titles.EXPENSE_TABLE_PRICE, (price));
            cv.put(Titles.EXPENSE_TABLE_QUANTITY, (quantity));
            cv.put(Titles.EXPENSE_TABLE_DATE,date);
            cv.put(Titles.EXPENSE_TABLE_DESCRIPTION,description);
            cv.put(Titles.EXPENSE_TABLE_ALARM,alarm);
            if(!editItem) {
                long rowId = db.insert(Titles.EXPENSE_TABLE_NAME, null, cv);
                Log.i("ExpenseAddTag", "Result Inserted row Id " + rowId);
                Intent i=new Intent();
                Log.i("ExpenseAddTag", "Result: "+ Activity.RESULT_OK);
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;
            }
            else
            {
                db.update(Titles.EXPENSE_TABLE_NAME,cv,Titles.EXPENSE_TABLE_ID+"=?",new String[] {""+id});
                Intent i=new Intent();
                Log.i("ExpenseAddTag", "Result: "+ Activity.RESULT_OK);
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;
            }

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
                        itemDobydate.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent();
        i.setClass(ExpenseAddActivity.this,ExpenseListActivity.class);
        startActivity(i);
    }
}
