package com.example.reminderapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Broadcast  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        int notifID= intent.getIntExtra("notif id",0);
        String eventName = intent.getStringExtra("event name");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"ok");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("REMINDER ALERT");
        builder.setContentText(eventName);
        builder.setStyle(new NotificationCompat.BigTextStyle());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        Database database = new Database(context);
        database.deleteTwo(notifID);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1,builder.build());

    }
}
