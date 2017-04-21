package com.example.todo;

/**
 * Created by Chhavi on 21-Apr-17.
 */

public class Contact {
    long id,alarm,date;
    String title;
    String contactDetail;
    String subject;
    String description;

    public Contact(long id, long alarm, long date, String title, String contactDetail, String subject, String description) {
        this.id = id;
        this.alarm = alarm;
        this.date = date;
        this.title = title;
        this.contactDetail = contactDetail;
        this.subject = subject;
        this.description = description;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
