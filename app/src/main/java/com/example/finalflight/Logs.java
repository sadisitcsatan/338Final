package com.example.finalflight;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.finalflight.DB.MainDatabase;

import java.util.Date;

@Entity(tableName = MainDatabase.LOG_TABLE)
public class Logs {
    @PrimaryKey(autoGenerate = true)
    private int mId;

    private Date mTime;
    private String mUser;
    private String mAction;

    public Logs(String mUser, String mAction) {
        this.mTime = new Date();
        this.mUser = mUser;
        this.mAction = mAction;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    @Override
    public String toString() {
        return "" + mTime +
                ",\n User='" + mUser + '\'' +
                ", '" + mAction + '\'';
    }
}
