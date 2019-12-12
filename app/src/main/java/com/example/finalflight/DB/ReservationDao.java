package com.example.finalflight.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalflight.Reservation;

import java.util.List;


@Dao
public interface ReservationDao {
    @Insert
    void insert(Reservation... reservations);

    @Update
    void update(Reservation... reservations);

    @Delete
    void delete(Reservation reservation);

    @Query("SELECT * FROM "+ MainDatabase.RESERVATION_TABLE+"")
    List<Reservation> getReservations();
    @Query("SELECT * FROM "+MainDatabase.RESERVATION_TABLE+" WHERE mUser = :user")
    List<Reservation> getUsersReservations(String user);
    @Query("SELECT * FROM "+MainDatabase.RESERVATION_TABLE+" WHERE mUser = :user AND mFlightName = :flight")
    Reservation getUserResrvation(String user,String flight);
}
