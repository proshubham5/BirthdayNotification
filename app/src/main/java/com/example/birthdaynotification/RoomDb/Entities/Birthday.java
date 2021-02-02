package com.example.birthdaynotification.RoomDb.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "birthdays")
public class Birthday {
    @PrimaryKey (autoGenerate = true)
    private int bid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "date")
    private String date;

    public Birthday( String name, String date) {
        bid = 0;
        this.name = name;
        this.date = date;
    }

    public int getBid() {
        return bid;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
