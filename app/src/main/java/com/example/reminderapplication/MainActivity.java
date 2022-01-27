package com.example.reminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.time.Instant;

public class MainActivity extends AppCompatActivity {
    Button Time, ViewAll, Add, Date;
    EditText EventName;
    TextView t_date, t_time;
    ArrayAdapter reminderArray;
    String formattedDate, formattedTime, newString;
    int hour,minute,year,month,day,second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewAll = (Button) findViewById(R.id.button3);
        Add = (Button) findViewById(R.id.button2);
        Time = (Button) findViewById(R.id.button6);
        Date = (Button) findViewById(R.id.button7);
        EventName = (EditText) findViewById(R.id.editTextTextPersonName);
        t_time  = (TextView) findViewById(R.id.textView2);
        t_date = (TextView) findViewById(R.id.textView3);

    }

    public void Time(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                second = 00;
                Calendar calendar = Calendar.getInstance();
                calendar.set(0,0,0,hour,minute,0);
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.sss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
                formattedTime = sdf2.format(calendar.getTime());
                String time = sdf.format(calendar.getTime());
                t_time.setText(formattedTime);

            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, onTimeSetListener,hour, minute,true);
        timePickerDialog.setTitle("select time");
        timePickerDialog.show();
    }

    public void Date(View view){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year2, month2, dayOfMonth);
                year = year2;
                month = month2;
                day = dayOfMonth;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                formattedDate = sdf.format(calendar.getTime());
                t_date.setText(formattedDate);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,onDateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }

    public void Add(View view){
        if(t_time.getText().toString()!="Time" && t_date.getText().toString()!="Date"){
            ReminderFormat reminder = new ReminderFormat(-1,EventName.getText().toString(), t_date.getText().toString(),t_time.getText().toString());
            Database dataBaseHelper = new Database(MainActivity.this);
            long epoch = System.currentTimeMillis();

            boolean success = dataBaseHelper.addOne(reminder);
            if(success==true){
                Toast.makeText(MainActivity.this, "Success = " + success,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Enter Date and Time", Toast.LENGTH_SHORT).show();
        }
    }

    public void ViewAll(View view){
        Intent intent = new Intent(MainActivity.this,Reminders.class);
        startActivity(intent);

    }
    public void trying(View view){
        Calendar calOne = Calendar.getInstance();
        int dayOfYear = calOne.get(Calendar.DAY_OF_YEAR);
        int year = calOne.get(Calendar.YEAR);
        Calendar calTwo = new GregorianCalendar(year, month, day);
        int day = calTwo.get(Calendar.DAY_OF_YEAR);
        int total_days = day - dayOfYear;
    }


}