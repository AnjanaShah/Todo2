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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {
    ListView listView;
    NotesAdapter notesAdapter;
    public ArrayList<Notes> notes=new ArrayList<Notes>();
    public final static String ID = "_id";
    Helper notesHelper=new Helper(NotesListActivity.this);
    final static int NEW_EXPENSE_CODE = 1;
    final static int CURRENT_EXPENSE_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        listView=(ListView) findViewById(R.id.notesList);
        notesAdapter= new NotesAdapter(NotesListActivity.this,notes);
        listView.setAdapter(notesAdapter);

        setupviews();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.setClass(NotesListActivity.this,NotesViewActivity.class);
                i.putExtra("expenseListPosition",position);
                Notes e=notes.get(position);
                i.putExtra(ID,e.getId());
                Log.i("ExpenseListActivity","id"+e.getId());
                startActivityForResult(i,CURRENT_EXPENSE_CODE);
            }
        });
    }

    private void setupviews() {
        //TODO fetch data from datavbase
        SQLiteDatabase db=notesHelper.getReadableDatabase();

        Cursor c=db.query(Titles.NOTES_TABLE_NAME,null,null,null,null,null,null);
        int i=0;
        notesAdapter.clear();
        notes.clear();
        while(c.moveToNext())
        {

            Long id=c.getLong(c.getColumnIndex(Titles.NOTES_TABLE_ID));
            Log.i("ExpenseListActivity","id="+id);
            String title=c.getString(c.getColumnIndex(Titles.NOTES_TABLE_TITLE));
            //TODO FETCH A DATE IN EPOCH AND CONVERT IT TO DATE
            long d= c.getLong(c.getColumnIndex(Titles.NOTES_TABLE_DATE));
            // Date date = new Date(epochTime);
            String subject=c.getString(c.getColumnIndex(Titles.NOTES_TABLE_SUBJECT));
            int alarm=c.getInt(c.getColumnIndex(Titles.NOTES_TABLE_ALARM));
            String description=c.getString(c.getColumnIndex(Titles.NOTES_TABLE_DESCRIPTION));
            Notes note = new Notes(id,d,alarm,title,subject,description);
            notes.add(note);

        }
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.addItem) {
            Intent i=new Intent();
            i.putExtra("expenseAdd",true);
            i.setClass(this,NotesAddActivity.class);
            startActivityForResult(i,NEW_EXPENSE_CODE);

        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NEW_EXPENSE_CODE || requestCode == CURRENT_EXPENSE_CODE){
            if(resultCode == Activity.RESULT_OK){
                setupviews();
            }else if(resultCode == Activity.RESULT_CANCELED){
                Log.i("MainActivityTag", "Result Cancelled ");
            }
        }

    }
}
