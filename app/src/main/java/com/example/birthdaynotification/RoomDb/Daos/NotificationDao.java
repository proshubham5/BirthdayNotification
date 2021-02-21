package com.example.birthdaynotification.RoomDb.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.birthdaynotification.RoomDb.Entities.Notification;

import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notifications")
    LiveData<List<Notification>> getAll();

    @Query("SELECT * FROM notifications WHERE nid IN (:ids)")
    List<Notification> loadAllByIds(int[] ids);

    @Query("SELECT * FROM notifications WHERE nid = :id")
    Notification[] getNotificationById(int id);

    @Query("SELECT count(*) FROM notifications")
    int countNoOfNotifications();

    @Insert
    void insertAll(Notification... notification);

    @Delete
    void delete(Notification notification);
}
