package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;

import static com.example.todo.Titles.CONTACT_TABLE_ID;
import static com.example.todo.Titles.ID;

public class ContactViewActivity extends AppCompatActivity {
    int position;
    long id;
    TextView contactTitle, contactDate, contactDetail, contactSubject, contactDescription, contactAlarm;
    Helper contactHelper = new Helper(ContactViewActivity.this);
    public final static int CURRENT_CONTACT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);
        //fetching all the edit text
        contactTitle = (TextView) findViewById(R.id.contactViewTitle);
        contactDate = (TextView) findViewById(R.id.contactViewDate);
        contactDetail = (TextView) findViewById(R.id.contactViewDetail);
        contactSubject = (TextView) findViewById(R.id.contactViewSubject);
        contactDescription = (TextView) findViewById(R.id.contactViewDescription);
        contactAlarm = (TextView) findViewById(R.id.contactViewAlarm);

        Intent i = getIntent();
        id = i.getLongExtra(ID, -1);
        Log.i("ExpenseViewTag", "id = " + id);
        // position= (int) i.getLongExtra("expenseListPosition",-1);
        if (id != -1) {
            populateViews();
        }
    }

    private void populateViews() {
        Contact e;
        SQLiteDatabase db = contactHelper.getReadableDatabase();
        Cursor c = db.query(Titles.CONTACT_TABLE_NAME, null, CONTACT_TABLE_ID + "=?", new String[]{"" + id}, null, null, null);
        Log.i("ExpenseViewTag", "cursor" + c);
        //db.query(Titles.EXPENSE_TABLE_NAME,null,Titles.EXPENSE_TABLE_ID+"="+id,null, null, null);
        if (c != null && c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(CONTACT_TABLE_ID));
            String title = c.getString(c.getColumnIndex(Titles.CONTACT_TABLE_TITLE));
            String detail = c.getString(c.getColumnIndex(Titles.CONTACT_TABLE_DETAIL));
            //TODO FETCH A DATE IN EPOCH AND CONVERT IT TO DATE
            long d = c.getLong(c.getColumnIndex(Titles.CONTACT_TABLE_DATE));
            // Date date = new Date(epochTime);
            String subject = c.getString(c.getColumnIndex(Titles.CONTACT_TABLE_SUBJECT));
            int alarm = c.getInt(c.getColumnIndex(Titles.CONTACT_TABLE_ALARM));

            String description = c.getString(c.getColumnIndex(Titles.CONTACT_TABLE_DESCRIPTION));
            Log.i("ContactView", "title:" + title);

            contactTitle.setText("" + title);
            contactDetail.setText("" + detail);
            String dateString = new Date(d).toString();

            contactDate.setText(dateString);
            contactSubject.setText("" + subject);
            contactAlarm.setText("" + alarm);
            contactDescription.setText(description);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expense_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idMenu = item.getItemId();
        if (idMenu == R.id.editItem) {
            Intent i = new Intent();
            i.putExtra("ExpenseEdit", true);
            Log.i("ExpenseEditTag", "id=" + id);
            i.putExtra(ID, id);
            i.setClass(ContactViewActivity.this, ContactAddActivity.class);
            startActivityForResult(i, CURRENT_CONTACT_CODE);

        }

        if (idMenu == R.id.deleteItem) {
            //TODO DELETE THE  Dialouge Box
            SQLiteDatabase db = contactHelper.getWritableDatabase();
            db.delete(Titles.CONTACT_TABLE_NAME, CONTACT_TABLE_ID + "=?", new String[]{"" + id});
            Intent i = new Intent();
            i.putExtra("DeleteItem", true);
            i.setClass(ContactViewActivity.this, ContactListActivity.class);
            startActivity(i);


        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CURRENT_CONTACT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                populateViews();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("MainActivityTag", "Result Cancelled ");
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.setClass(ContactViewActivity.this, ContactListActivity.class);
        startActivity(i);

    }
}

