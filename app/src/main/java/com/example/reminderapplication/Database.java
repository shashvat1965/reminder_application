package com.example.reminderapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String REMINDER_TABLE = "REMINDER_TABLE";
    public static final String COLUMN_EVENT_NAME = "EVENT_NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_NOTIF_ID = "NOTIF_ID";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_BROADCAST_ID = "BROADCAST_ID";
    public Database(@Nullable Context context) {
        super(context, "reminder.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement= "CREATE TABLE " + REMINDER_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVENT_NAME + " TEXT, " + COLUMN_TIME + " TEXT, NOTIF_ID INTEGER, DATE TEXT, BROADCAST_ID TEXT)" ;
        db.execSQL(createTableStatement);
    }

    public boolean addOne(ReminderFormat Reminder){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EVENT_NAME, Reminder.getEventName());
        cv.put(COLUMN_TIME, Reminder.getTime() );
        cv.put(COLUMN_NOTIF_ID,Reminder.getNotifID());
        cv.put(COLUMN_DATE,Reminder.getDate());
        cv.put(COLUMN_BROADCAST_ID,Reminder.getBroadcast());
        int kk = Reminder.getNotifID();
        long insert = db.insert(REMINDER_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<ReminderFormat> getEveryone(){
        List<ReminderFormat> returnList = new ArrayList<ReminderFormat>();
        String query= "SELECT * FROM " + REMINDER_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String time = cursor.getString(2);
                int notif_id = cursor.getInt(3);
                String date = cursor.getString(4);
                String broadcast = cursor.getString(5);

                ReminderFormat reminder = new ReminderFormat(id, name, time, notif_id, date, broadcast);
                returnList.add(reminder);
            } while(cursor.moveToNext());
        }
        else{
            // get rekt
        }

        cursor.close();
        db.close();
        return returnList;
    }
    public String getColumnEventName(String broadcast){
        String query= "SELECT EVENT_NAME FROM " + REMINDER_TABLE +" WHERE BROADCAST_ID ="+"\""+broadcast+"\"";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String eventName = cursor.getString(0);
        return eventName;
    }
    public String getColumnEventName2(int notifID){
        String query= "SELECT EVENT_NAME FROM " + REMINDER_TABLE +" WHERE NOTIF_ID =" + notifID;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String eventName = cursor.getString(1);
        return eventName;
    }
    public int getColumnNotifId(String broadcast){
        String query= "SELECT NOTIF_ID FROM " + REMINDER_TABLE +" WHERE BROADCAST_ID ="+broadcast;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int notifID = cursor.getInt(0);
        return notifID;
    }
    public boolean deleteOne(ReminderFormat reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + REMINDER_TABLE + " WHERE " + COLUMN_ID + " =" + reminder.getId();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteTwo(int notif_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + REMINDER_TABLE + " WHERE " + COLUMN_NOTIF_ID + " =" + notif_id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ REMINDER_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return  false;
        }
    }

    public void editDate(ReminderFormat reminder, String newDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, newDate);
        String id = String.valueOf(reminder.getId());
        db.update(REMINDER_TABLE, cv, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }


    public void editTime(ReminderFormat reminder, String newTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TIME, newTime);
        String id = String.valueOf(reminder.getId());
        db.update(REMINDER_TABLE, cv, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }
}
