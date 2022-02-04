package com.example.reminderapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Editing extends AppCompatActivity {
    Button delete, saveEdits, time, date, back;
    TextView showTime, showDate;
    Database databaseHelper;
    int year,month,day,hour2,minute2;
    String formattedDate2;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editing);
        showTime = (TextView) findViewById(R.id.textView3);
        showDate = (TextView) findViewById(R.id.textView);
        delete = (Button) findViewById(R.id.button5);
        saveEdits = (Button) findViewById(R.id.button8);
        back = (Button) findViewById(R.id.button9);
        time = findViewById(R.id.button);
        date = findViewById(R.id.button4);
        databaseHelper = new Database(Editing.this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Louis_George_Cafe.otf");
        showTime.setTypeface(customFont);
        showDate.setTypeface(customFont);
        delete.setTypeface(customFont);
        time.setTypeface(customFont);
        back.setTypeface(customFont);
        saveEdits.setTypeface(customFont);
        date.setTypeface(customFont);
    }

    public void delete(View v){
        Intent intent = getIntent();
        ReminderFormat reminder = (ReminderFormat) intent.getSerializableExtra("reminder");
        databaseHelper.deleteOne(reminder);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), Broadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), reminder.getNotifID(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(Editing.this, "deleted reminder", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Editing.this, Reminders.class);
        startActivity(i);
    }

    public void back(View v){
        Intent i = new Intent(Editing.this, Reminders.class);
        startActivity(i);
    }


    public void Date2(View view) {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth) {
                calendar.set(year2, month2, dayOfMonth);
                year = year2;
                month = month2;
                day = dayOfMonth;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                formattedDate2 = sdf.format(calendar.getTime());
                showDate.setText(formattedDate2);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,onDateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }


    public void Time2(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour2, int selectedMinute2) {
                hour2 = selectedHour2;
                showTime = (TextView) findViewById(R.id.textView3);
                minute2 = selectedMinute2;
                int seconds2 = 00;
                int millis2 = 000;
                calendar.set(Calendar.HOUR_OF_DAY,selectedHour2);
                calendar.set(Calendar.MINUTE,selectedMinute2);
                calendar.set(Calendar.MILLISECOND,000);
                calendar.set(Calendar.SECOND,00);
                Intent i = getIntent();
                SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
                String formattedTime = sdf2.format(calendar.getTime());
                showTime.setText(formattedTime);
                //showTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour2,minute2));

            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, onTimeSetListener,hour2, minute2,true);
        timePickerDialog.setTitle("select time");
        timePickerDialog.show();
    }
    public void saveEdit(View view){
        if(!showTime.getText().toString().equals("Time") && !showDate.getText().toString().equals("Date")){
            String newTime = showTime.getText().toString();
            String newDate = showDate.getText().toString();
            Intent intent = getIntent();
            ReminderFormat reminder = (ReminderFormat) intent.getSerializableExtra("reminder");
            databaseHelper.editTime(reminder,newTime);
            databaseHelper.editDate(reminder,newDate);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(getApplicationContext(), Broadcast.class);
            myIntent.putExtra("event name",reminder.getEventName());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), reminder.getNotifID(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
            Intent i2 = new Intent(getApplicationContext(),Broadcast.class);
            i2.putExtra("notif id", reminder.getNotifID());
            i2.putExtra("event name",reminder.getEventName());
            AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), reminder.getId(),i2,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager1.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent1);
            Toast.makeText(this,"saved edits",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Wrong input",Toast.LENGTH_SHORT).show();
        }
    }

}
