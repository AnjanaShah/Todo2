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

import static android.R.attr.category;
import static android.R.attr.id;
import static com.example.todo.MeetingListActivity.ID;
import static com.example.todo.NotesViewActivity.CURRENT_CONTACT_CODE;
import static com.example.todo.R.id.meetingTitle;
import static com.example.todo.Titles.MEETING_TABLE_ID;

public class MeetingViewActivity extends AppCompatActivity {
    int position;
    long id;
    TextView meetingTitle,meetingDate, meetingPlace, meetingClient, meetingDescription,meetingAlarm;
    Helper expenseHelper=new Helper(MeetingViewActivity.this);
    public final static int CURRENT_MEETING_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_view);
        //fetching all the edit text
        meetingTitle=(TextView)findViewById(R.id.meetingViewTitle);
        meetingDate=(TextView)findViewById(R.id.meetingViewDate);
        meetingPlace=(TextView)findViewById(R.id.meetingViewPlace);
        meetingClient=(TextView)findViewById(R.id.meetingViewCLient);
        meetingDescription=(TextView)findViewById(R.id.meetingViewDescription);
        meetingAlarm=(TextView)findViewById(R.id.meetingViewAlarm);

        Intent i=getIntent();
        id = i.getLongExtra(ID, -1);
        Log.i("ExpenseViewTag", "id = " + id);
        // position= (int) i.getLongExtra("expenseListPosition",-1);
        if(id !=  -1){
            populateViews();
        }
    }
    private void populateViews() {
        Meeting e;
        SQLiteDatabase db=expenseHelper.getReadableDatabase();
        Cursor c=db.query(Titles.MEETING_TABLE_NAME,null, MEETING_TABLE_ID+"=?", new String[] {""+id},null,null,null);
        Log.i("ExpenseViewTag", "cursor"+c);
        //db.query(Titles.EXPENSE_TABLE_NAME,null,Titles.EXPENSE_TABLE_ID+"="+id,null, null, null);
        if(c != null && c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(MEETING_TABLE_ID));
            String title = c.getString(c.getColumnIndex(Titles.MEETING_TABLE_TITLE));
            String place = c.getString(c.getColumnIndex(Titles.MEETING_TABLE_PLACE));
            //TODO FETCH A DATE IN EPOCH AND CONVERT IT TO DATE
            long d = c.getLong(c.getColumnIndex(Titles.MEETING_TABLE_DATE));
            // Date date = new Date(epochTime);
            String client = c.getString(c.getColumnIndex(Titles.MEETING_TABLE_CLIENT));
            int alarm = c.getInt(c.getColumnIndex(Titles.MEETING_TABLE_ALARM));

            String description = c.getString(c.getColumnIndex(Titles.MEETING_TABLE_DESCRIPTION));
            Log.i("MeetingView","title:"+title);

            meetingTitle.setText(""+title);
            meetingPlace.setText(""+place);
            String dateString = new Date(d).toString();

            meetingDate.setText(dateString);
            meetingClient.setText("" + client);
            meetingAlarm.setText("" + alarm);
            meetingDescription.setText(description);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meeting_view_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idMenu=item.getItemId();
        if(idMenu==R.id.editItem) {
            Intent i =new Intent();
            i.putExtra("ExpenseEdit",true);
            Log.i("ExpenseEditTag","id="+id);
            i.putExtra(ID,id);
            i.setClass(MeetingViewActivity.this,MeetingAddActivity.class);
            startActivityForResult(i,CURRENT_MEETING_CODE);

        }

        if(idMenu==R.id.deleteItem) {
            //TODO DELETE THE  Dialouge Box
            SQLiteDatabase db=expenseHelper.getWritableDatabase();
            db.delete(Titles.MEETING_TABLE_NAME,MEETING_TABLE_ID+"=?",new String[] {""+id});
            Intent i=new Intent();
            i.putExtra("DeleteItem",true);
            i.setClass(MeetingViewActivity.this,MeetingListActivity.class);
            startActivity(i);



        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == CURRENT_MEETING_CODE){
            if(resultCode == Activity.RESULT_OK){
                populateViews();
            }else if(resultCode == Activity.RESULT_CANCELED){
                Log.i("MainActivityTag", "Result Cancelled ");
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent();
        i.setClass(MeetingViewActivity.this,MeetingListActivity.class);
        startActivity(i);
    }
}

