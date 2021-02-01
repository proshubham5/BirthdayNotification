package com.example.birthdaynotification.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.birthdaynotification.RoomDb.AppDatabase;
import com.example.birthdaynotification.RoomDb.Daos.BirthdayDao;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;

import java.util.List;

public class BirthdayTableRepository {

    private BirthdayDao birthdayDao;
    private LiveData<List<Birthday>> allBirthdays;

    public BirthdayTableRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        birthdayDao = db.birthdayDao();
        allBirthdays = birthdayDao.getAll();
    }

    public LiveData<List<Birthday>> getAllBirthdays() {
        return allBirthdays;
    }

    public void insert(Birthday birthday) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            birthdayDao.insertAll(birthday);
        });
    }


}
