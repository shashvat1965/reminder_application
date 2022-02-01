package com.example.reminderapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Reminders extends AppCompatActivity {
    ListView reminders;
    Database databaseHelper;
    private AlertDialog.Builder dialogBuilder ;
    private AlertDialog dialog ;
    Button time2, date2, save, back, deleteAll;
    TextView t_time2, t_date2;
    ArrayAdapter<ReminderFormat> reminderArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        reminders = (ListView) findViewById(R.id.ReminderList);
        ArrayAdapter reminderArray;
        back = (Button) findViewById(R.id.button11);
        databaseHelper = new Database(Reminders.this);
        showAllReminders(databaseHelper);
        deleteAll = findViewById(R.id.button10);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Louis_George_Cafe.otf");
        back.setTypeface(customFont);
        deleteAll.setTypeface(customFont);


        reminders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ReminderFormat clicked = (ReminderFormat) parent.getItemAtPosition(position);
                Intent intent = new Intent(Reminders.this,Editing.class);
                intent.putExtra("reminder", clicked);
                startActivity(intent);
                showAllReminders(databaseHelper);
                finish();
            }
        });

    }
    public void showAllReminders(Database databaseHelper){
        reminderArray = new ArrayAdapter<ReminderFormat>(Reminders.this, R.layout.list_view,databaseHelper.getEveryone());
        reminders.setAdapter(reminderArray);
    }
    public void back(View view){
        Intent i2 = new Intent(Reminders.this, MainActivity.class);
        startActivity(i2);
    }

    public void deleteAll(View view){
        databaseHelper.deleteAll();
        showAllReminders(databaseHelper);
        Toast.makeText(this, "deleted all reminders", Toast.LENGTH_SHORT).show();
    }
}