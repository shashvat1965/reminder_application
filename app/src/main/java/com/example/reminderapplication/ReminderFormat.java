package com.example.reminderapplication;

import java.io.Serializable;

public class ReminderFormat implements Serializable {
    private int id;
    private String eventName;
    private String time;
    private int notifID;
    private String date, broadcast;

    public ReminderFormat(int id, String name, String time, int notifID, String date, String broadcast) {
        this.id = id;
        this.eventName = name;
        this.time= time;
        this.notifID = notifID;
        this.date = date;
        this.broadcast = broadcast;
    }

    public String toString() {
        return "name=" + eventName +"\n"+
                "time=" + time +"\n"+
                "date=" + date;
    }

    public void Customer(){
    }

    public int getId() {
        return id;
    }

    public String getBroadcast(){ return broadcast;}

    public void setBroadcast(String broadcast){ this.broadcast = broadcast;}

    public String getDate() {
        return date;
    }

    public void setDate(String date){ this.date = date; }

    public int getNotifID(){return notifID;}

    public void setNotifID(int id){ this.id = id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }
}


