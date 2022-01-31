package com.example.reminderapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button Time, ViewAll, Add, Date;
    EditText EventName;
    TextView t_date, t_time;
    ArrayAdapter reminderArray;
    String formattedDate, formattedTime, newString;
    int hour, minute, year, month, day, second, k;
    long ok;
    Calendar calendar= Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewAll = (Button) findViewById(R.id.button3);
        Add = (Button) findViewById(R.id.button2);
        Time = (Button) findViewById(R.id.button6);
        EventName = (EditText) findViewById(R.id.editTextTextPersonName);
        t_time  = (TextView) findViewById(R.id.textView2);
        t_date = (TextView) findViewById(R.id.textView3);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ok";
            String desc = "ok";
            NotificationChannel channel = new NotificationChannel("ok",name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    public void Time(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                second = 00;
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.MILLISECOND,000);
                calendar.set(Calendar.SECOND,00);
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.sss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
                formattedTime = sdf2.format(calendar.getTime());
                t_time.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));


            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, onTimeSetListener,hour, minute,true);
        timePickerDialog.setTitle("select time");
        timePickerDialog.show();
    }
    public void Date(View view){
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth) {
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
        if(!t_time.getText().toString().equals("Time")){
            Random random = new Random();
            int y = random.nextInt(2147483647);
            ReminderFormat reminder = new ReminderFormat(-1,EventName.getText().toString(),t_time.getText().toString(), y, t_date.getText().toString(),Long.toString(calendar.getTimeInMillis()));
            Database dataBaseHelper = new Database(MainActivity.this);
            long epoch = System.currentTimeMillis();
            boolean success = dataBaseHelper.addOne(reminder);
            ok = calendar.getTimeInMillis();
            int kk2 = (int) (ok/100000);
            if(success==true){
                Toast.makeText(MainActivity.this,"Added an reminder",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(MainActivity.this, Broadcast.class);
            intent.putExtra("event name",reminder.getEventName());
            intent.putExtra("notif id",reminder.getNotifID());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), y, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, ok, pendingIntent);




        }
        else{
            Toast.makeText(this, "Enter Date and Time", Toast.LENGTH_SHORT).show();
        }
    }

    public void ViewAll(View view){
        Intent intent = new Intent(MainActivity.this,Reminders.class);
        startActivity(intent);

    }


}