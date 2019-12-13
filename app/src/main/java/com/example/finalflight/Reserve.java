package com.example.finalflight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalflight.DB.FlightDao;
import com.example.finalflight.DB.LogsDao;
import com.example.finalflight.DB.MainDatabase;
import com.example.finalflight.DB.ReservationDao;

public class Reserve extends AppCompatActivity {
    Flight reserve;
    String username;
    String mFlightName;
    EditText mSeats;
    FlightDao mFlightDao;

    TextView musernameView;
    TextView mFlightData;
    TextView mWarning;

    Button mConfirm;

    LogsDao mLogDao;
    Logs mLog;

    ReservationDao mReserveDao;
    Reservation mPut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        mSeats =(EditText) findViewById(R.id.passengers);
        musernameView =(TextView) findViewById(R.id.userNameview);
        mFlightData =(TextView) findViewById(R.id.flightInfo);
        mConfirm = (Button) findViewById(R.id.confirm);
        mWarning = (TextView) findViewById(R.id.warning);
        mFlightName = getIntent().getStringExtra("Flight_Name");
        mLogDao = Room.databaseBuilder(this,MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getLogsDao();
        mReserveDao = Room.databaseBuilder(this,MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getReservationDao();
        mFlightDao = Room.databaseBuilder(this, MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getFlightDao();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        username = pref.getString("Username",null);
        musernameView.setText(username);
        reserve = mFlightDao.getFlightByName(mFlightName);
        mFlightData.setText(reserve.toString());
    }
    public void confirm(View view){
        mReserveDao = Room.databaseBuilder(this,MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getReservationDao();
        if (!mSeats.getText().toString().equals("")){
            int seats = Integer.parseInt(mSeats.getText().toString());
            if(seats >= 0 && seats <= reserve.getSeats() && seats <= 7){
                float total = reserve.getCost() * seats;
                Log.d("DEBUG","making reservation");
                mPut = new Reservation(username,reserve.getFlightName(),seats,total);
                Log.d("DEBUG","made reservation");
                Log.d("DEBUG","changing seats");
                reserve.setSeats(reserve.getSeats()-seats);
                Log.d("DEBUG","changed seats");
                Log.d("DEBUG","updating flights");
                Log.d("DEBUG",reserve.toString());
                mFlightDao.update(reserve);
                Log.d("DEBUG","updated flights");
                Log.d("DEBUG","inserting reservation");
                Log.d("DEBUG",mPut.toString());
                mReserveDao.insert(mPut);
                Log.d("DEBUG","inserted");
                Log.d("DEBUG","making Log");
                mLog = new Logs(username,"Reserved "+seats+" for flight "+reserve.getFlightName()+" \n Cost: $"+ total);
                Log.d("DEBUG","made log");
                Log.d("DEBUG","inserting log");
                Log.d("DEBUG",mLog.toString());
                mLogDao.insert(mLog);
                Log.d("DEBUG","inserted");
                new AlertDialog.Builder(this)
                        .setTitle("Reserving "+seats +" from Flight,")
                        .setMessage(reserve.toString()+ " \n Total Cost: $"+ total)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Reserve.this,Flights.class);
                                startActivity(intent);
                                return;
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else {
                mWarning.setText("Invalid amount of seats");
            }
        }else {
            mWarning.setText("Invalid amount of seats");
        }
    }
}
