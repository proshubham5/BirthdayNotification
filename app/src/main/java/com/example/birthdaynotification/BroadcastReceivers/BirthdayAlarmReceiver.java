package com.example.birthdaynotification.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.birthdaynotification.Repositories.BirthdayNotificationRepository;
import com.example.birthdaynotification.RoomDb.AppDatabase;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;
import com.example.birthdaynotification.Utils.DateTimeUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Calendar;

public class BirthdayAlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "BirthdayAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        BirthdayNotificationRepository birthdayNotificationRepository = new BirthdayNotificationRepository(context);
        int NOTIFICATION_ID = intent.getIntExtra("notification_id", 0);
        Log.d(TAG, "onReceive: NOTIFICATION_ID is : " + NOTIFICATION_ID);

        // show the notification
        birthdayNotificationRepository.deliverNotification(NOTIFICATION_ID);

        LocalDate localDate = LocalDate.now();
        localDate.plusYears(1);

        Long NotificationEpochMillis = DateTimeUtils.getEpochMillisFromLocalDate(localDate);

        // set notification alarm for next year
        birthdayNotificationRepository.setNotificationAlarm(NotificationEpochMillis, NOTIFICATION_ID);


    }
}