package com.example.finalflight;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.finalflight.DB.MainDatabase;

import java.util.Date;

@Entity(tableName = MainDatabase.FLIGHT_TABLE)
public class Flight {

    @PrimaryKey
    private int mFlightId;
    private String mFlightName;
    private String mFlightDeparture;
    private String mFlightArrival;
    private int mSeats;
    private float mCost;
    private Date mDepartureTime;

    public Flight(int mFlightId, String mFlightDeparture, String mFlightArrival, int mSeats, float mCost, Date mDepartureTime) {
        this.mFlightId = mFlightId;
        this.mFlightName = "Otter"+mFlightId;
        this.mFlightDeparture = mFlightDeparture;
        this.mFlightArrival = mFlightArrival;
        this.mSeats = mSeats;
        this.mCost = mCost;
        this.mDepartureTime = mDepartureTime;
    }

    public int getFlightId() {
        return mFlightId;
    }

    public String getFlightName() {
        return mFlightName;
    }

    public void setFlightName(String mFlightName) {
        this.mFlightName = mFlightName;
    }

    public void setFlightId(int mFlightId) {
        this.mFlightId = mFlightId;
    }

    public String getFlightDeparture() {
        return mFlightDeparture;
    }

    public void setFlightDeparture(String mFlightDeparture) {
        this.mFlightDeparture = mFlightDeparture;
    }

    public String getFlightArrival() {
        return mFlightArrival;
    }

    public void setFlightArrival(String mFlightArrical) {
        this.mFlightArrival = mFlightArrical;
    }

    public int getSeats() {
        return mSeats;
    }

    public void setSeats(int mSeats) {
        this.mSeats = mSeats;
    }

    public float getCost() {
        return mCost;
    }

    public void setCost(int mCost) {
        this.mCost = mCost;
    }

    public Date getDepartureTime() {
        return mDepartureTime;
    }

    public void setDepartureTime(Date mDepartureTime) {
        this.mDepartureTime = mDepartureTime;
    }

    @Override
    public String toString() {
        return " Flight" +
                " " + mFlightName +
                ",\n Flight Departure='" + mFlightDeparture + '\'' +
                ",\n Flight Arrival='" + mFlightArrival + '\'' +
                ",\n Seats=" + mSeats +
                ",\n Cost=" + mCost +
                ",\n DepartureTime=" + mDepartureTime +
                ' ';
    }
}
