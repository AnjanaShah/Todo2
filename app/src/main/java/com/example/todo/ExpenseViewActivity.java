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

import static com.example.todo.Titles.CURRENT_EXPENSE_CODE;
import static com.example.todo.Titles.EXPENSE_TABLE_ID;
import static com.example.todo.Titles.ID;

public class ExpenseViewActivity extends AppCompatActivity {
    int position;
    long id;
    TextView expenseTitle,expenseDate, expenseQuantity, expenseCategory,expensePrice, expenseDescription,expenseAlarm;
    Helper expenseHelper=new Helper(ExpenseViewActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_view);
        //fetching all the edit text
        expenseTitle=(TextView)findViewById(R.id.item_title);
        expenseDate=(TextView)findViewById(R.id.dobydate);
        expenseQuantity=(TextView)findViewById(R.id.quantity);
        expenseCategory=(TextView)findViewById(R.id.item_category);
        expensePrice=(TextView)findViewById(R.id.price);
        expenseDescription=(TextView)findViewById(R.id.description);
        expenseAlarm=(TextView)findViewById(R.id.alarm);

        Intent i=getIntent();
        id = i.getLongExtra(ID, -1);
        Log.i("ExpenseViewTag", "id = " + id);
        // position= (int) i.getLongExtra("expenseListPosition",-1);
        if(id !=  -1){
            populateViews();
        }
    }

    private void populateViews() {
        Expense e;
        SQLiteDatabase db=expenseHelper.getReadableDatabase();
        Cursor c=db.query(Titles.EXPENSE_TABLE_NAME,null, EXPENSE_TABLE_ID+"=?", new String[] {""+id},null,null,null);
        Log.i("ExpenseViewTag", "cursor"+c);
        //db.query(Titles.EXPENSE_TABLE_NAME,null,Titles.EXPENSE_TABLE_ID+"="+id,null, null, null);
        if(c != null && c.moveToFirst())
        {
            long id = c.getLong(c.getColumnIndex(EXPENSE_TABLE_ID));
            String title = c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_TITLE));
            String category = c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_CATEGORY));
            //TODO FETCH A DATE IN EPOCH AND CONVERT IT TO DATE
            long d = c.getLong(c.getColumnIndex(Titles.EXPENSE_TABLE_DATE));
            // Date date = new Date(epochTime);
            double price = c.getDouble(c.getColumnIndex(Titles.EXPENSE_TABLE_PRICE));
            int quantity = c.getInt(c.getColumnIndex(Titles.EXPENSE_TABLE_QUANTITY));
            int alarm = c.getInt(c.getColumnIndex(Titles.EXPENSE_TABLE_ALARM));

            String description = c.getString(c.getColumnIndex(Titles.EXPENSE_TABLE_DESCRIPTION));
            expenseTitle.setText(title);
            expenseCategory.setText(category);
            String dateString = new Date(d).toString();

            expenseDate.setText(dateString);
            expensePrice.setText("" + price);
            expenseQuantity.setText("" + quantity);
            expenseDescription.setText(description);
            expenseAlarm.setText(""+alarm);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expense_view_menu,menu);
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
            i.setClass(ExpenseViewActivity.this,ExpenseAddActivity.class);
            startActivityForResult(i,CURRENT_EXPENSE_CODE);

        }

        if(idMenu==R.id.deleteItem) {
            //TODO DELETE THE  Dialouge Box
            SQLiteDatabase db=expenseHelper.getWritableDatabase();
            db.delete(Titles.EXPENSE_TABLE_NAME,EXPENSE_TABLE_ID+"=?",new String[] {""+id});
            Intent i=new Intent();
            i.putExtra("DeleteItem",true);
            i.setClass(ExpenseViewActivity.this,ExpenseListActivity.class);
            startActivity(i);



        }
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent i=new Intent();
        i.setClass(ExpenseViewActivity.this,ExpenseListActivity.class);
        startActivity(i);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == CURRENT_EXPENSE_CODE){
            if(resultCode == Activity.RESULT_OK){
                populateViews();
            }else if(resultCode == Activity.RESULT_CANCELED){
                Log.i("MainActivityTag", "Result Cancelled ");
            }
        }

    }
}
