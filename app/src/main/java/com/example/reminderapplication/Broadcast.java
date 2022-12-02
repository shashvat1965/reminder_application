package com.example.reminderapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Broadcast  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        int notificationID= intent.getIntExtra("notification id",0);
        String eventName = intent.getStringExtra("event name");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"ok");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("REMINDER ALERT");
        builder.setContentText(eventName);
        builder.setStyle(new NotificationCompat.BigTextStyle());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        Database database = new Database(context);
        database.deleteTwo(notificationID);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1,builder.build());
    }
}
