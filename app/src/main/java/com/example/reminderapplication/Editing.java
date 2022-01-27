package com.example.reminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

public class Editing extends AppCompatActivity {
    Button delete, save_time, time, date, back, save_date;
    TextView showTime, showDate;
    Database databaseHelper;
    int year,month,day,hour2,minute2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        showDate = (TextView) findViewById(R.id.textView4);
        showTime = (TextView) findViewById(R.id.textView3);
        databaseHelper = new Database(Editing.this);
    }

    public void delete(View v){
        Intent intent = getIntent();
        ReminderFormat reminder = (ReminderFormat) intent.getSerializableExtra("reminder");
        databaseHelper.deleteOne(reminder);
        Toast.makeText(Editing.this, "deleted", Toast.LENGTH_SHORT).show();
    }

    public void back(View v){
        Intent i = new Intent(Editing.this,Reminders.class);
        startActivity(i);
    }


    public void Date2(View view) {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(calendar.YEAR);
        month = calendar.get(calendar.MONTH);
        day = calendar.get(calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Editing.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                showDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
        datePickerDialog.show();
    }


    public void Time2(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour2, int selectedMinute2) {
                hour2 = selectedHour2;
                showTime = (TextView) findViewById(R.id.textView3);
                minute2 = selectedMinute2;
                showTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour2,minute2));

            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, onTimeSetListener,hour2, minute2,true);
        timePickerDialog.setTitle("select time");
        timePickerDialog.show();
    }
    public void timeSave(View view){
        if(!showTime.getText().toString().equals("Time")){
            String newTime = showTime.getText().toString();
            Intent intent = getIntent();
            ReminderFormat reminder = (ReminderFormat) intent.getSerializableExtra("reminder");
            databaseHelper.editTime(reminder,newTime);
            Toast.makeText(this,"working",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Wrong input",Toast.LENGTH_SHORT).show();
        }
    }
    public void dateSave(View view){
        if(!showDate.getText().toString().equals("Date")){
            String newDate = showDate.getText().toString();
            Intent intent = getIntent();
            ReminderFormat reminder = (ReminderFormat) intent.getSerializableExtra("reminder");
            databaseHelper.editDate(reminder,newDate);
            Toast.makeText(this,"working",Toast.LENGTH_SHORT).show();
        }
    }

}
