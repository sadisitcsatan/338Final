package com.example.finalflight.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.finalflight.DB.typeConverter.DateTpyeConverter;
import com.example.finalflight.Flight;
import com.example.finalflight.Logs;
import com.example.finalflight.Reservation;
import com.example.finalflight.User;

@Database(entities = { Flight.class, User.class, Logs.class, Reservation.class}, version = 2)
@TypeConverters(DateTpyeConverter.class)
public abstract class MainDatabase extends RoomDatabase{
    public static final String dbName="DB-airlines";
    public static final String USER_TABLE="Users";
    public static final String FLIGHT_TABLE="Flights";
    public static final String LOG_TABLE="Logs";
    public static final String RESERVATION_TABLE="Reservations";

    public abstract UserDao getUserDao();
    public abstract FlightDao getFlightDao();
    public abstract LogsDao getLogsDao();
    public abstract ReservationDao getReservationDao();
}
