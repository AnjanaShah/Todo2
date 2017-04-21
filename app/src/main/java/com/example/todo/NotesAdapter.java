package com.example.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chhavi on 21-Apr-17.
 */

public class NotesAdapter extends ArrayAdapter <Notes> {
    Context mContext;
    ArrayList<Notes> mNotes;
    public NotesAdapter(Context context, ArrayList<Notes> objects) {

        super(context,0,objects);
        mContext=context;
        mNotes=objects;
    }
    static class NotesViewHolder {
        TextView notesTitle;
        TextView notesDate;

        NotesViewHolder(TextView notesTitle, TextView notesDate){
            this.notesTitle=notesTitle;
            this.notesDate=notesDate;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.notes_list_view,null);
            TextView notesTitle=(TextView) convertView.findViewById(R.id.notesTitle);
            TextView notesDate=(TextView) convertView.findViewById(R.id.notesDate);
            NotesViewHolder holder=new NotesViewHolder(notesTitle,notesDate);
            convertView.setTag(holder);
        }
        NotesViewHolder holder= (NotesViewHolder) convertView.getTag();
        Notes m=mNotes.get(position);
        holder.notesTitle.setText(m.title);
        long date=m.date;
        String dateString = new Date(date).toString();
        holder.notesDate.setText(dateString);

        return convertView;
    }

    @Nullable
    @Override
    public Notes getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
