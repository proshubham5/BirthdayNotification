package com.example.birthdaynotification.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.birthdaynotification.Repositories.BirthdayTableRepository;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private BirthdayTableRepository birthdayTableRepository;

    private LiveData<List<Birthday>> birthdayListLiveData;

    public HomeViewModel(Application application) {
        super(application);
        birthdayTableRepository = new BirthdayTableRepository(application.getApplicationContext());
        birthdayListLiveData = birthdayTableRepository.getAllBirthdays();
    }

    public LiveData<List<Birthday>> getBirthdayListLiveData() {
        return birthdayListLiveData;
    }
}