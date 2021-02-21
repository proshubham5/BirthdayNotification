package com.example.birthdaynotification.Repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.birthdaynotification.RoomDb.AppDatabase;
import com.example.birthdaynotification.RoomDb.Daos.NotificationDao;
import com.example.birthdaynotification.RoomDb.Entities.Notification;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NotificationTableRepository {
    private static final String TAG = "BirthdayTableRepository";

    private NotificationDao notificationDao;
    private LiveData<List<Notification>> allNotification;

    public NotificationTableRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        notificationDao = db.notificationDao();
        allNotification = notificationDao.getAll();
    }

    public LiveData<List<Notification>> getAllNotifications() {
        return allNotification;
    }

    public void insert(Notification notification) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            int noOfNotifications = notificationDao.countNoOfNotifications();
            notification.setNid(noOfNotifications + 1);
            notificationDao.insertAll(notification);
        });
    }


    public Notification getNotificationById(int nid) throws ExecutionException, InterruptedException {

        Callable<Notification[]> callable = () -> notificationDao.getNotificationById(nid);

        Future<Notification[]> res = AppDatabase.databaseWriteExecutor.submit(callable);
        Log.d(TAG, "getBirthdayById: res is : " + res.get()[0]);
        AppDatabase.databaseWriteExecutor.shutdown();
        return res.get()[0];
    }
}
