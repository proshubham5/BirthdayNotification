package com.example.birthdaynotification.RoomDb.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)

    int nid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "message")
    private String message;

    public Notification(String title, String message) {
        this.nid = 0;
        this.title = title;
        this.message = message;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
