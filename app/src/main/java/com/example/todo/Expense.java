package com.example.todo;

/**
 * Created by Chhavi on 21-Apr-17.
 */

public class Expense {
    long id;
    String title;
    long date;
    long alarm;
    String category;
    String description;
    double price;
    int quantity;

    public Expense(long id,String title,String category,long date,double price,int quantity,String description,long alarm)
    {
        this.id=id;
        this.title=title;
        this.category=category;
        this.date=date;
        this.price=price;
        this.quantity=quantity;
        this.description=description;
        this.alarm=alarm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getAlarm() {
        return alarm;
    }

    public void setAlarm(long alarm) {
        this.alarm = alarm;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
