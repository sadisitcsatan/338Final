package com.example.finalflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalflight.DB.FlightDao;
import com.example.finalflight.DB.MainDatabase;
import com.example.finalflight.DB.ReservationDao;

import java.util.List;

public class Flights extends AppCompatActivity {
    TextView mFlightLog;
    TextView mSearchres;

    EditText mDepartue;
    EditText mArrival;
    EditText mResname;

    Button mSearch;
    Button madd;
    Button mCancell;

    ReservationDao mReservDao;
    List<Reservation> mReserve;

    FlightDao mFlightDao;
    List<Flight> mFlights;
    Flight resFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        mSearch = findViewById(R.id.search);
        madd = findViewById(R.id.reserve);
        mCancell = findViewById(R.id.cancel);
        mDepartue = findViewById(R.id.depart);
        mArrival = findViewById(R.id.arrive);
        mResname = findViewById(R.id.flightName);
        mFlightLog = findViewById(R.id.flighLog);
        mSearchres = findViewById(R.id.searchRes);
        mReservDao = Room.databaseBuilder(this,MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getReservationDao();
        mFlightDao = Room.databaseBuilder(this, MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getFlightDao();
        refreshDisplay();
        checkReserves();

    }
    private void refreshDisplay(){
        mFlights = mFlightDao.getFlights();
        if (!mFlights.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for (Flight flight : mFlights){
                stringBuilder.append(flight);
                stringBuilder.append('\n');
                stringBuilder.append('\n');
            }
            mFlightLog.setText(stringBuilder.toString());
        }else {
            mFlightLog.setText("No Flights");
        }
    }
    public void checkReserves(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        if (pref.getString("Username",null) != null){
            String user = pref.getString("Username",null);
            mReserve = mReservDao.getUsersReservations(user);

            if (mReserve.isEmpty()){
                mCancell.setVisibility(View.GONE);
            }else {
                mSearchres.setText("Not empty");
                StringBuilder stringBuilder = new StringBuilder();
                for (Reservation reservation : mReserve){
                    stringBuilder.append(reservation);
                    stringBuilder.append('\n');
                    stringBuilder.append('\n');
                }
                mSearchres.setText(stringBuilder.toString());
            }
            return;
        }
        return;
    }
    public void search(View view){
        String depart = mDepartue.getText().toString();
        String ariv = mArrival.getText().toString();
        String print;
        if (!depart.equals("") || !ariv.equals("")){
            if (!depart.equals("") && !ariv.equals("")){
              mFlights = mFlightDao.getFlightsByDA(depart,ariv);
                StringBuilder stringBuilder = new StringBuilder();
                for (Flight flight : mFlights){
                    stringBuilder.append(flight);
                    stringBuilder.append('\n');
                }
                print = stringBuilder.toString();
                if (stringBuilder.toString().equals("")){
                    print = "No Flights";
                }
                mFlightLog.setText(print);
            }else if (!depart.equals("")){
                mFlights = mFlightDao.getFlightsByD(depart);
                StringBuilder stringBuilder = new StringBuilder();
                for (Flight flight : mFlights){
                    stringBuilder.append(flight);
                    stringBuilder.append('\n');
                }
                print = stringBuilder.toString();
                if (stringBuilder.toString().equals("")){
                    print = "No Flights";
                }
                mFlightLog.setText(print);
            }else {
                mFlights = mFlightDao.getFlightsByA(ariv);
                StringBuilder stringBuilder = new StringBuilder();
                for (Flight flight : mFlights){
                    stringBuilder.append(flight);
                    stringBuilder.append('\n');
                }
                print = stringBuilder.toString();
                if (stringBuilder.toString().equals("")){
                    print = "No Flights";
                }
                mFlightLog.setText(print);
            }
        }else {
            mFlightLog.setText("No Flights");
        }

    }
    public void tryReserve(View view){
        mFlightDao= Room.databaseBuilder(this,MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getFlightDao();
        if (!mResname.getText().toString().equals("")){
            mSearchres.setText("not empty");
            String test = mResname.getText().toString();
            mSearchres.setText(test);
            resFlight = mFlightDao.getFlightByName(test);
            //mSearchres.setText(resFlight.toString());
            if (resFlight != null){
                mSearchres.setText("got result");
                Intent intent = new Intent(Flights.this,Reserve.class);
                intent.putExtra("Flight_Name",test);
                startActivity(intent);
            }else {
                mSearchres.setText("got null from that name");
            }
        }
    }
}
