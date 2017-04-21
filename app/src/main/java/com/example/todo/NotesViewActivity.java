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

import static com.example.todo.NotesAddActivity.ID;
import static com.example.todo.Titles.NOTES_TABLE_ID;

public class NotesViewActivity extends AppCompatActivity {
    int position;
    long id;
    TextView notesTitle,notesDate, notesSubject, notesDescription,notesAlarm;
    Helper expenseHelper=new Helper(NotesViewActivity.this);
    public final static int CURRENT_CONTACT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_view);
        //fetching all the edit text
        notesTitle=(TextView)findViewById(R.id.notesViewTitle);
        notesDate=(TextView)findViewById(R.id.notesViewDate);
        notesSubject=(TextView)findViewById(R.id.notesViewSubject);
        notesDescription=(TextView)findViewById(R.id.notesViewDescription);
        notesAlarm=(TextView)findViewById(R.id.notesViewAlarm);

        Intent i=getIntent();
        id = i.getLongExtra(ID, -1);
        Log.i("ExpenseViewTag", "id = " + id);
        // position= (int) i.getLongExtra("expenseListPosition",-1);
        if(id !=  -1){
            populateViews();
        }
    }
    private void populateViews() {
        Notes e;
        SQLiteDatabase db=expenseHelper.getReadableDatabase();
        Cursor c=db.query(Titles.NOTES_TABLE_NAME,null, NOTES_TABLE_ID+"=?", new String[] {""+id},null,null,null);
        Log.i("ExpenseViewTag", "cursor"+c);
        //db.query(Titles.EXPENSE_TABLE_NAME,null,Titles.EXPENSE_TABLE_ID+"="+id,null, null, null);
        if(c != null && c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(NOTES_TABLE_ID));
            String title = c.getString(c.getColumnIndex(Titles.NOTES_TABLE_TITLE));
            //TODO FETCH A DATE IN EPOCH AND CONVERT IT TO DATE
            long d = c.getLong(c.getColumnIndex(Titles.NOTES_TABLE_DATE));
            // Date date = new Date(epochTime);
            String subject = c.getString(c.getColumnIndex(Titles.NOTES_TABLE_SUBJECT));
            int alarm = c.getInt(c.getColumnIndex(Titles.NOTES_TABLE_ALARM));

            String description = c.getString(c.getColumnIndex(Titles.NOTES_TABLE_DESCRIPTION));
            Log.i("NotesView","title:"+title);

            notesTitle.setText(""+title);
            String dateString = new Date(d).toString();

            notesDate.setText(dateString);
            notesSubject.setText("" + subject);
            notesAlarm.setText("" + alarm);
            notesDescription.setText(description);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_view_menu,menu);
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
            i.setClass(NotesViewActivity.this,NotesAddActivity.class);
            startActivityForResult(i,CURRENT_CONTACT_CODE);

        }

        if(idMenu==R.id.deleteItem) {
            //TODO DELETE THE  Dialouge Box
            SQLiteDatabase db=expenseHelper.getWritableDatabase();
            db.delete(Titles.NOTES_TABLE_NAME,NOTES_TABLE_ID+"=?",new String[] {""+id});
            Intent i=new Intent();
            i.putExtra("DeleteItem",true);
            i.setClass(NotesViewActivity.this,NotesListActivity.class);
            startActivity(i);



        }
        return true;

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == CURRENT_CONTACT_CODE){
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
        i.setClass(NotesViewActivity.this,NotesListActivity.class);
        startActivity(i);
    }
}
