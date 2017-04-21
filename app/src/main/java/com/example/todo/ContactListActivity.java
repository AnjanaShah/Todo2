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

import static com.example.todo.NotesAddActivity.ID;
import static com.example.todo.Titles.CURRENT_EXPENSE_CODE;

public class ContactListActivity extends AppCompatActivity {
    ListView ContactListView;
    Helper contactHelper=new Helper(ContactListActivity.this);
    public ArrayList<Contact> contacts=new ArrayList<Contact>();
    final static int NEW_CONTACT_CODE = 1;
    final static int CURRENT_CONTACT_CODE = 2;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ContactListView=(ListView)findViewById(R.id.contacts_list);
        contactAdapter=new ContactAdapter(ContactListActivity.this,contacts);
        ContactListView.setAdapter(contactAdapter);
        setUpViews();

        ContactListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view, int position, long id){
                Intent i = new Intent();
                i.setClass(ContactListActivity.this,ContactViewActivity.class);
                i.putExtra("expenseListPosition",position);
                Contact c=contacts.get(position);
                i.putExtra(ID,c.getId());
                Log.i("ExpenseListActivity","id"+c.getId());
                startActivityForResult(i,CURRENT_CONTACT_CODE);
            }
        });
    }

    private void setUpViews() {

        SQLiteDatabase db=contactHelper.getReadableDatabase();
        Cursor c=db.query(Titles.CONTACT_TABLE_NAME,null,null,null,null,null,null);
        int i=0;
        contactAdapter.clear();
        contacts.clear();
        while(c.moveToNext())
        {

            Long id=c.getLong(c.getColumnIndex(Titles.CONTACT_TABLE_ID));
            Log.i("ExpenseListActivity","id="+id);
            String title=c.getString(c.getColumnIndex(Titles.CONTACT_TABLE_TITLE));
            String details=c.getString(c.getColumnIndex(Titles.CONTACT_TABLE_DETAIL));
            String subject=c.getString(c.getColumnIndex(Titles.CONTACT_TABLE_SUBJECT));
            //TODO FETCH A DATE IN EPOCH AND CONVERT IT TO DATE
            long d= c.getLong(c.getColumnIndex(Titles.CONTACT_TABLE_DATE));
            // Date date = new Date(epochTime);

            long alarm=c.getLong(c.getColumnIndex(Titles.CONTACT_TABLE_ALARM));
            String description=c.getString(c.getColumnIndex(Titles.CONTACT_TABLE_DESCRIPTION));
            Contact contact = new Contact(id,alarm,d,title,details,subject,description);
            contacts.add(contact);

        }
        Log.i("ExpenseListTag","value after function i="+contacts.size());
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expense_list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.addItem) {
            Intent i=new Intent();
            i.putExtra("expenseAdd",true);
            i.setClass(this,ContactAddActivity.class);
            startActivityForResult(i,NEW_CONTACT_CODE);

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NEW_CONTACT_CODE || requestCode == CURRENT_CONTACT_CODE){
            if(resultCode == Activity.RESULT_OK){
                setUpViews();
            }else if(resultCode == Activity.RESULT_CANCELED){
                Log.i("MainActivityTag", "Result Cancelled ");
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent();
        i.setClass(ContactListActivity.this,MainActivity.class);
        startActivity(i);
    }
}
