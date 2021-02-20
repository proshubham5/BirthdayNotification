package com.example.birthdaynotification.Repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.birthdaynotification.RoomDb.AppDatabase;
import com.example.birthdaynotification.RoomDb.Daos.BirthdayDao;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BirthdayTableRepository {

    private static final String TAG = "BirthdayTableRepository";

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
            int noOfBirthday = birthdayDao.countNoOfBirthdays();
            birthday.setBid(noOfBirthday + 1);
            birthdayDao.insertAll(birthday);
        });
    }


    public Birthday getBirthdayById(int bid) throws ExecutionException, InterruptedException {

        Callable<Birthday[]> callable = () -> birthdayDao.getBirthdayById(bid);

        /*AppDatabase.databaseWriteExecutor.execute(() -> {
            birthdayDao.getBirthdayById(bid);
        });*/

        Future<Birthday[]> res = AppDatabase.databaseWriteExecutor.submit(callable);
        Log.d(TAG, "getBirthdayById: res is : " + res.get()[0]);
        AppDatabase.databaseWriteExecutor.shutdown();
        return res.get()[0];
    }
}
