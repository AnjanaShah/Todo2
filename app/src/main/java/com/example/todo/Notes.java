package com.example.todo;

/**
 * Created by Chhavi on 21-Apr-17.
 */

public class Notes {
    long id,alarm,date;
    String title;
    String subject;
    String description;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Notes(long id, long date, long alarm, String title, String subject, String description) {
        this.id = id;
        this.alarm = alarm;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.date= date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlarm() {
        return alarm;
    }

    public void setAlarm(long alarm) {
        this.alarm = alarm;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
