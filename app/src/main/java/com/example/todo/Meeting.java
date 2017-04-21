package com.example.todo;

/**
 * Created by Chhavi on 20-Apr-17.
 */

public class Meeting {
    long id,alarm,date;
    String title;
    String client;
    String description;
    String place;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Meeting(long id, long date, long alarm, String title, String client, String description, String place) {
        this.id = id;
        this.alarm = alarm;
        this.title = title;
        this.client = client;
        this.description = description;
        this.place = place;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }


}
