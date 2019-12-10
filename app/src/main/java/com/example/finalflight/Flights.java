package com.example.finalflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.widget.TextView;

import com.example.finalflight.DB.FlightDao;
import com.example.finalflight.DB.MainDatabase;

import java.util.List;

public class Flights extends AppCompatActivity {
    TextView mFlightLog;

    FlightDao mFlightDao;
    List<Flight> mFlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        mFlightLog = findViewById(R.id.flighLog);
        mFlightDao = Room.databaseBuilder(this, MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getFlightDao();
        refreshDisplay();
    }
    private void refreshDisplay(){
        mFlights = mFlightDao.getFlights();
        if (!mFlights.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for (Flight flight : mFlights){
                stringBuilder.append(flight);
            }
            mFlightLog.setText(stringBuilder.toString());
        }else {
            mFlightLog.setText("No Flights");
        }
    }
}
