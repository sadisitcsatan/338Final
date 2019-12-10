package com.example.finalflight;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.finalflight.DB.MainDatabase;

@Entity(tableName = MainDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String mUsername;
    private String mPassword;
    private boolean admin;

    public User(String mUsername, String mPassword) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.admin = false;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
