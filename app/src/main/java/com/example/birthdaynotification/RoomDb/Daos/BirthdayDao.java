package com.example.birthdaynotification.RoomDb.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.birthdaynotification.RoomDb.Entities.Birthday;

import java.util.List;

@Dao
public interface BirthdayDao {

    @Query("SELECT * FROM birthdays")
    LiveData<List<Birthday>> getAll();

    @Query("SELECT * FROM birthdays WHERE bid IN (:userIds)")
    List<Birthday> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM birthdays WHERE name LIKE :name LIMIT 1")
    Birthday findByName(String name);

    @Insert
    void insertAll(Birthday... birthdays);

    @Delete
    void delete(Birthday birthday);

}
