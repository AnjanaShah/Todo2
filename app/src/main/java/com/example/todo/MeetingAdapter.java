package com.example.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chhavi on 20-Apr-17.
 */

public class MeetingAdapter extends ArrayAdapter <Meeting>{

    Context mContext;
    ArrayList<Meeting> mMeeting;
    public MeetingAdapter(Context context, ArrayList<Meeting> objects) {

        super(context,0,objects);
        mContext=context;
        mMeeting=objects;
    }
    static class MeetingViewHolder {
        TextView meetingTitle;
        TextView meetingDate;

        MeetingViewHolder(TextView meetingTitle, TextView meetingDate){
            this.meetingTitle=meetingTitle;
            this.meetingDate=meetingDate;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.meeting_list_view,null);
            TextView meetingTitle=(TextView) convertView.findViewById(R.id.meetingTitle);
            TextView meetingDate=(TextView) convertView.findViewById(R.id.meetingDate);
            MeetingViewHolder holder=new MeetingViewHolder(meetingTitle,meetingDate);
            convertView.setTag(holder);
        }
        MeetingViewHolder holder= (MeetingViewHolder) convertView.getTag();
        Meeting m=mMeeting.get(position);
        holder.meetingTitle.setText(m.title);
        long date=m.date;
        String dateString = new Date(date).toString();
        holder.meetingDate.setText(dateString);

        return convertView;
    }

    @Nullable
    @Override
    public Meeting getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
