package com.example.birthdaynotification.RoomDb.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "birthdays")
public class Birthday {
    @PrimaryKey
    private int bid;

    @ColumnInfo(name = "name")
    private String name;

    public Birthday(int bid, String name) {
        this.bid = bid;
        this.name = name;
    }

    public int getBid() {
        return bid;
    }

    public String getName() {
        return name;
    }
}
