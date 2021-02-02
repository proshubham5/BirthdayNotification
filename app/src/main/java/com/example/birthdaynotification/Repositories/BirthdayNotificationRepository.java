package com.example.birthdaynotification.Repositories;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

import com.example.birthdaynotification.BroadcastReceivers.BirthdayAlarmReceiver;
import com.example.birthdaynotification.MainActivity;
import com.example.birthdaynotification.R;

import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class BirthdayNotificationRepository {

    private Context mContext;
    private NotificationManager mNotificationManager;

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "birthday_notification_channel";

    public BirthdayNotificationRepository(Context context) {
        this.mContext = context;
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    public void createNotificationChannel() {

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder makeNotificationBuilder(PendingIntent contentPendingIntent) {

        return new NotificationCompat.Builder(mContext, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
    }

    public void deliverNotification() {
        Intent contentIntent = new Intent(mContext, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (mContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = makeNotificationBuilder(contentPendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    public void setNotificationAlarm(String date) {
        Intent notifyIntent = new Intent(mContext, BirthdayAlarmReceiver.class);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(mContext, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(7)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6))); // January has value 0
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(0, 2)));
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 33);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM );

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), notifyPendingIntent);
    }
}
