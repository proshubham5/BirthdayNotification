package com.example.birthdaynotification.ui.AddBirthday;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.birthdaynotification.Repositories.BirthdayTableRepository;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;

public class AddBirthdayViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private BirthdayTableRepository birthdayTableRepository;
    private MutableLiveData<String> nameLiveData = new MutableLiveData<>("");

    public AddBirthdayViewModel(Application application) {
        super(application);
        //..
        birthdayTableRepository = new BirthdayTableRepository(application.getApplicationContext());
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
}