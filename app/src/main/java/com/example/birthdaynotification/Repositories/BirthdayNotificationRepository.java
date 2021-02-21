package com.example.birthdaynotification.Repositories;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.birthdaynotification.BroadcastReceivers.BirthdayAlarmReceiver;
import com.example.birthdaynotification.MainActivity;
import com.example.birthdaynotification.R;
import com.example.birthdaynotification.RoomDb.AppDatabase;
import com.example.birthdaynotification.RoomDb.Daos.NotificationDao;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;
import com.example.birthdaynotification.RoomDb.Entities.Notification;

import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class BirthdayNotificationRepository {

    private static final String TAG = "BirthdayNotificationRep";

    private Context mContext;
    private NotificationManager mNotificationManager;

    private static final String PRIMARY_CHANNEL_ID = "birthday_notification_channel";

    public BirthdayNotificationRepository(Context context) {
        this.mContext = context;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
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

    private NotificationCompat.Builder makeNotificationBuilder(PendingIntent contentPendingIntent, int bid) {

        String title = String.valueOf(bid);
        String message = String.valueOf(bid);

        try {
            BirthdayTableRepository birthdayTableRepository = new BirthdayTableRepository(mContext);
            Birthday birthday = birthdayTableRepository.getBirthdayById(bid);
            title = "Today is " + birthday.getName() + "'s birthday!" ;
            message = "Wish him or her with full joy.";

        } catch (Exception e) {
            Log.e(TAG, "makeNotificationBuilder: notification builder failed : ", e);
        }

        return new NotificationCompat.Builder(mContext, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
    }

    public void deliverNotification(int NOTIFICATION_ID) {
        Intent contentIntent = new Intent(mContext, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (mContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = makeNotificationBuilder(contentPendingIntent, NOTIFICATION_ID);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

        try {
            BirthdayTableRepository birthdayTableRepository = new BirthdayTableRepository(mContext);
            Birthday birthday = birthdayTableRepository.getBirthdayById(NOTIFICATION_ID);
            String title = "Today is " + birthday.getName() + "'s birthday!";
            String message = "Wish him or her with full joy.";

            NotificationTableRepository notificationTableRepository = new NotificationTableRepository(mContext);
            notificationTableRepository.insert(new Notification(title, message));

        } catch (Exception e) {
            Log.e(TAG, "makeNotificationBuilder: notification builder failed : ", e);
        }

    }

    public void setNotificationAlarm(Long notifyDate, int NOTIFICATION_ID) {
        Intent notifyIntent = new Intent(mContext, BirthdayAlarmReceiver.class);
        notifyIntent.putExtra("notification_id", NOTIFICATION_ID);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(mContext, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, notifyDate, notifyPendingIntent);
    }

}
