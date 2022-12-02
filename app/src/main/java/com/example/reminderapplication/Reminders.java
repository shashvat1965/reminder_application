package com.example.reminderapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Reminders extends AppCompatActivity {
    ListView reminders;
    Database databaseHelper;
    Button back, deleteAll;
    ArrayAdapter<ReminderFormat> reminderArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        reminders = findViewById(R.id.ReminderList);
        back = findViewById(R.id.button11);
        databaseHelper = new Database(Reminders.this);
        showAllReminders(databaseHelper);
        deleteAll = findViewById(R.id.button10);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Louis_George_Cafe.otf");
        back.setTypeface(customFont);
        deleteAll.setTypeface(customFont);


        reminders.setOnItemClickListener((parent, view, position, id) -> {

            ReminderFormat clicked = (ReminderFormat) parent.getItemAtPosition(position);
            Intent intent = new Intent(Reminders.this,Editing.class);
            intent.putExtra("reminder", clicked);
            startActivity(intent);
            showAllReminders(databaseHelper);
            finish();
        });

    }
    public void showAllReminders(Database databaseHelper){
        reminderArray = new ArrayAdapter<>(Reminders.this, R.layout.list_view, databaseHelper.getEveryone());
        reminders.setAdapter(reminderArray);
    }

    public void back(View view){
        Intent i2 = new Intent(Reminders.this, MainActivity.class);
        startActivity(i2);
    }

    public void deleteAll(View view){
        boolean success = databaseHelper.deleteAll();
        if(success){
            Toast.makeText(Reminders.this, "All reminders deleted", Toast.LENGTH_SHORT).show();
            showAllReminders(databaseHelper);
        }
        else{
            Toast.makeText(Reminders.this, "Error deleting reminders", Toast.LENGTH_SHORT).show();
        }
    }
}