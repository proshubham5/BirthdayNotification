package com.example.birthdaynotification.ui.notifications;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.birthdaynotification.Repositories.BirthdayTableRepository;
import com.example.birthdaynotification.Repositories.NotificationTableRepository;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;
import com.example.birthdaynotification.RoomDb.Entities.Notification;

import java.util.List;

public class NotificationsViewModel extends AndroidViewModel {

    private NotificationTableRepository notificationTableRepository;
    private LiveData<List<Notification>> notificationListLiveData;

    public NotificationsViewModel(Application application) {
        super(application);
        notificationTableRepository = new NotificationTableRepository(application.getApplicationContext());
        notificationListLiveData = notificationTableRepository.getAllNotifications();
    }

    public LiveData<List<Notification>> getNotificationListLiveData() {
        return notificationListLiveData;
    }

}