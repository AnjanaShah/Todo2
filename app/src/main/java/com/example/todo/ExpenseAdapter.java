package com.example.todo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chhavi on 21-Apr-17.
 */

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    Context mContext;
    ArrayList<Expense> mExpenses;


    public ExpenseAdapter(Context context, ArrayList<Expense> objects) {
        super(context,0, objects);
        mContext=context;
        mExpenses=objects;
    }
    static class ExpenseViewHolder{
        TextView titleTextView;
        TextView dateTextView;
        ExpenseViewHolder(TextView titleTextView, TextView dateTextView ){
            this.titleTextView = titleTextView;
            this.dateTextView = dateTextView;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {

            convertView = View.inflate(mContext, R.layout.expense_list_view, null);
            TextView titleText = (TextView) convertView.findViewById(R.id.item_title);
            TextView dateText = (TextView) convertView.findViewById(R.id.do_by_date);
            ExpenseViewHolder holder = new ExpenseViewHolder(titleText, dateText);
            convertView.setTag(holder);
        }

        ExpenseViewHolder holder = (ExpenseViewHolder)convertView.getTag();
        Expense e = mExpenses.get(position);
        holder.titleTextView.setText(e.title);
        Log.i("ExpenseAdapterTag","title:"+e.title);
        long date = e.date;
        String dateString = new Date(date).toString();
        holder.dateTextView.setText(dateString);

        //returning the view
        return  convertView;


    }
    @Nullable
    @Override
    public Expense getItem(int position) {
        return super.getItem(position);
    }


    @Override
    public int getCount() {
        return super.getCount();
    }
}
