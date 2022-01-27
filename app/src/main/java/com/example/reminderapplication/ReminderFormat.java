package com.example.reminderapplication;

import java.io.Serializable;

public class ReminderFormat implements Serializable {
    private int id;
    private String eventName;
    private String date;
    private String time;

    public ReminderFormat(int id, String name, String date, String time) {
        this.id = id;
        this.eventName = name;
        this.date = date;
        this.time= time;
    }

    public String toString() {
        return "name=" + eventName +"\n"+
                "date=" + date + "\n" +
                "time=" + time ;
    }

    public void Customer(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }
}

