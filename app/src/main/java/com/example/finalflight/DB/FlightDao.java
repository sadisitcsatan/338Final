package com.example.finalflight.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalflight.Flight;

import java.util.List;

@Dao
public interface FlightDao {
    @Insert
    void insert(Flight... flights);

    @Update
    void update(Flight... flights);

    @Delete
    void delete(Flight flight);

    @Query("SELECT * FROM "+ MainDatabase.FLIGHT_TABLE+"")
    List<Flight> getFlights();

    @Query("SELECT * FROM " + MainDatabase.FLIGHT_TABLE +" WHERE mFlightId = :FId")
    Flight getFlight(int FId);

    @Query("SELECT * FROM "+MainDatabase.FLIGHT_TABLE+" WHERE mFlightName = :name")
    Flight getFlightByName(String name);

    @Query("SELECT * FROM "+MainDatabase.FLIGHT_TABLE+" WHERE mFlightDeparture = :depart AND mFlightArrival = :Arrive")
    List<Flight> getFlightsByDA(String depart, String Arrive);

    @Query("SELECT * FROM "+MainDatabase.FLIGHT_TABLE+" WHERE mFlightDeparture = :depart")
    List<Flight> getFlightsByD(String depart);

    @Query("SELECT * FROM "+MainDatabase.FLIGHT_TABLE+" WHERE  mFlightArrival = :Arrive")
    List<Flight> getFlightsByA(String Arrive);
}
