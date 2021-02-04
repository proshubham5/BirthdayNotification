package com.example.birthdaynotification.ui.AddBirthday;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.birthdaynotification.Repositories.BirthdayNotificationRepository;
import com.example.birthdaynotification.Repositories.BirthdayTableRepository;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;
import com.example.birthdaynotification.Utils.DateTimeUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class AddBirthdayViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private BirthdayTableRepository birthdayTableRepository;
    private BirthdayNotificationRepository birthdayNotificationRepository;
    private MutableLiveData<String> nameLiveData = new MutableLiveData<>("");

    public AddBirthdayViewModel(Application application) {
        super(application);
        //..
        birthdayTableRepository = new BirthdayTableRepository(application.getApplicationContext());
        birthdayNotificationRepository = new BirthdayNotificationRepository(application.getApplicationContext());
    }

    public void insert(Birthday birthday) {
        birthdayTableRepository.insert(birthday);
    }

    public void setNameLiveData(String nameLiveData) {
        this.nameLiveData.setValue(nameLiveData);
    }

    public LiveData<String> getNameLiveData() {
        return nameLiveData;
    }

    public void setNotificationAlarm(Birthday birthday) {

        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        LocalDate birthDate = DateTimeUtils.convertFromEpochMillisToLocalDate(birthday.getDate());
        LocalDate notifyDate = now;

        LocalDate changedBirthDate = birthDate.withYear(now.getYear());
        int dateComparison = now.compareTo(changedBirthDate);

        if(dateComparison < 0) {
            //... now < changedBirthDate
            notifyDate = changedBirthDate;

        } else if(dateComparison > 0) {
            //... now > changedBirthDate
            notifyDate = changedBirthDate.withYear(now.getYear() + 1);

        }

        Long notifyDateInMillis = DateTimeUtils.getEpochMillisFromLocalDate(notifyDate);

        birthdayNotificationRepository.setNotificationAlarm(notifyDateInMillis, birthday.getBid());
    }
}