

package com.example.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivityTag", "inAcitivity ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivityTag", "layout set ");

        Button expense,contact, meetings, noteIt;
        expense=(Button)findViewById(R.id.expense);
        meetings=(Button)findViewById((R.id.meetings));
        contact=(Button)findViewById((R.id.contact));
        noteIt=(Button)findViewById(R.id.notes);
        //setting main activity as a listener
        expense.setOnClickListener(this);
        meetings.setOnClickListener(this);
        contact.setOnClickListener(this);
        noteIt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.expense)
        {
            Intent i=new Intent();
            i.setClass(MainActivity.this,ExpenseListActivity.class  );
            startActivity(i);

        }
        if(id==R.id.meetings)
        {
            Intent i=new Intent();
            i.setClass(MainActivity.this,MeetingListActivity.class  );
            startActivity(i);

        }
        if(id==R.id.notes)
        {
            Intent i=new Intent();
            i.setClass(MainActivity.this,NotesListActivity.class  );
            startActivity(i);

        }
        if(id==R.id.contact)
        {
            Intent i=new Intent();
            i.setClass(MainActivity.this,ContactListActivity.class  );
            startActivity(i);

        }
    }

}

