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

public class ContactAdapter extends ArrayAdapter<Contact> {

    Context mContext;
    ArrayList<Contact> mContacts;

    public ContactAdapter(Context context, ArrayList<Contact> objects) {

        super(context, 0, objects);
        mContext = context;
        mContacts = objects;
    }

    static class ContactViewHolder {

        TextView titleTextView;
        TextView dateTextView;

        ContactViewHolder(TextView titleTextView, TextView dateTextView) {
            this.titleTextView = titleTextView;
            this.dateTextView = dateTextView;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.contact_list_view, null);
            TextView titleText = (TextView) convertView.findViewById(R.id.contact_title);
            TextView dateText = (TextView) convertView.findViewById(R.id.contact_date);
            ContactAdapter.ContactViewHolder holder = new ContactAdapter.ContactViewHolder(titleText, dateText);
            convertView.setTag(holder);
        }

        ContactAdapter.ContactViewHolder holder = (ContactAdapter.ContactViewHolder) convertView.getTag();
        Contact contact = mContacts.get(position);
        holder.titleTextView.setText(contact.title);
        Log.i("ExpenseAdapterTag", "title:" + contact.title);
        long date = contact.date;
        String dateString = new Date(date).toString();
        holder.dateTextView.setText(dateString);

        //returning the view
        return convertView;

    }
    @Nullable
    @Override
    public Contact getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

}

