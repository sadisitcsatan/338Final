package com.example.finalflight;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.finalflight.DB.MainDatabase;

@Entity(tableName = MainDatabase.RESERVATION_TABLE)
public class Reservation {

    @PrimaryKey
    private int mResId;
    private String mFlightName;
    private int mSeats;
    private String mUser;
    private float mTotal;

    public Reservation(String mUser, String mFlightName , int mSeats, float mTotal){
        this.mUser = mUser;
        this.mFlightName = mFlightName;
        this.mSeats = mSeats;
        this.mTotal = mTotal;

    }

    public float getTotal() {
        return mTotal;
    }

    public void setTotal(float mTotal) {
        this.mTotal = mTotal;
    }

    public int getResId() {
        return mResId;
    }

    public void setResId(int mResId) {
        this.mResId = mResId;
    }

    public String getFlightName() {
        return mFlightName;
    }

    public void setFlightName(String mFlightName) {
        this.mFlightName = mFlightName;
    }

    public int getSeats() {
        return mSeats;
    }

    public void setSeats(int mSeats) {
        this.mSeats = mSeats;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    @Override
    public String toString() {
        return "Reservation: \n" +
                "" + mUser + '\'' +
                ",\n Flight: " + mFlightName +
                ",\n Seats='" + mSeats + '\''+
                ",\n Total Cost= $"+ mTotal;
    }
}
