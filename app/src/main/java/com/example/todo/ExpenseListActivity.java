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
import static com.example.todo.Titles.NEW_EXPENSE_CODE;

public class ExpenseListActivity extends AppCompatActivity {
    ListView listView;
    Helper expenseHelper=new Helper(ExpenseListActivity.this);
    public ArrayList<Expense> expenses=new ArrayList<Expense>();;
    ExpenseAdapter adapter;
    long  date;
    String sDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        listView=(ListView)findViewById(R.id.listView);

        adapter=new ExpenseAdapter(ExpenseListActivity.this,expenses);
        listView.setAdapter(adapter);
        //setting up the list
        setUpViews();

        //on an item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view, int position, long id){
                Intent i = new Intent();
                i.setClass(ExpenseListActivity.this,ExpenseViewActivity.class);
                i.putExtra("expenseListPosition",position);
                Expense e=expenses.get(position);
                i.putExtra(ID,e.getId());
                Log.i("ExpenseListActivity","id"+e.getId());
                startActivityForResult(i,CURRENT_EXPENSE_CODE);
            }
        });
    }

    private void setUpViews() {
        //mode of database read or write

        SQLiteDatabase db=expenseHelper.getReadableDatabase();
        Cursor c=db.query(Titles.EXPENSE_TABLE_NAME,null,null,null,null,null,null);
        int i=0;
        adapter.clear();
        expenses.clear();
        while(c.moveToNext())
        {

            Long id=c.getLong(c.getColumnIndex(Titles.EXPENSE_TABLE_ID));
            Log.i("ExpenseListActivity","id="+id);
            String title=c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_TITLE));
            String category=c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_CATEGORY));
            //TODO FETCH A DATE IN EPOCH AND CONVERT IT TO DATE
            long d= c.getLong(c.getColumnIndex(Titles.EXPENSE_TABLE_DATE));
            // Date date = new Date(epochTime);

            long alarm=c.getLong(c.getColumnIndex(Titles.EXPENSE_TABLE_ALARM));
            double price=c.getDouble(c.getColumnIndex(Titles.EXPENSE_TABLE_PRICE));
            int quantity=c.getInt(c.getColumnIndex(Titles.EXPENSE_TABLE_QUANTITY));
            String description=c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_DESCRIPTION));
            Expense expense = new Expense(id,title,category,d,price,quantity,description,alarm);
            expenses.add(expense);

        }
        Log.i("ExpenseListTag","value after function i="+expenses.size());
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expense_list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.addItem) {
            Intent i=new Intent();
            i.putExtra("expenseAdd",true);
            i.setClass(this,ExpenseAddActivity.class);
            startActivityForResult(i,NEW_EXPENSE_CODE);

        }
        return true;
    }

    // handle when coming from add screen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NEW_EXPENSE_CODE || requestCode == CURRENT_EXPENSE_CODE){
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
        i.setClass(ExpenseListActivity.this,MainActivity.class);
        startActivity(i);
    }
}
