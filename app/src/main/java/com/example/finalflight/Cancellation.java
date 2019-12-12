package com.example.finalflight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalflight.DB.FlightDao;
import com.example.finalflight.DB.LogsDao;
import com.example.finalflight.DB.MainDatabase;
import com.example.finalflight.DB.ReservationDao;

import java.util.List;

public class Cancellation extends AppCompatActivity {
    TextView mReserveLog;
    TextView mResult;

    EditText mNameFlight;

    Button mSearch;
    Button mCancel;

    FlightDao mFlightDao;
    ReservationDao mReserveDao;
    LogsDao mLogDao;

    List<Reservation> mReserves;

    Logs mPut;
    Flight mChange;
    Reservation mReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);
        mReserveLog =(TextView) findViewById(R.id.cacnelLog);
        mResult = (TextView) findViewById(R.id.cancelRes);
        mNameFlight = (EditText) findViewById(R.id.namedeFlight);
        mSearch = (Button) findViewById(R.id.seaRch);
        mCancel = (Button) findViewById(R.id.cancer);
        mFlightDao = Room.databaseBuilder(this, MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getFlightDao();
        mReserveDao = Room.databaseBuilder(this, MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getReservationDao();
        mLogDao = Room.databaseBuilder(this, MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getLogsDao();
        checkReserves();
    }
    public void checkReserves(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        if (pref.getString("Username",null) != null){
            String user = pref.getString("Username",null);
            mReserves = mReserveDao.getUsersReservations(user);

            if (mReserves.isEmpty()){
                mResult.setText("No Reservations");
            }else {
                StringBuilder stringBuilder = new StringBuilder();
                for (Reservation reservation : mReserves){
                    stringBuilder.append(reservation);
                    stringBuilder.append('\n');
                    stringBuilder.append('\n');
                }
                mReserveLog.setText(stringBuilder.toString());
            }
            return;
        }
        return;
    }
    public void searchReservation(View view){
        if (!mNameFlight.getText().toString().equals("")){
            String nameCheck = mNameFlight.getText().toString();
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
            String user = pref.getString("Username",null);
            mReserve= mReserveDao.getUserResrvation(user,nameCheck);
            if (mReserve != null){
                StringBuilder stringBuilder = new StringBuilder();
                for (Reservation reservation : mReserves){
                    stringBuilder.append(reservation);
                    stringBuilder.append('\n');
                    stringBuilder.append('\n');
                }
                mReserveLog.setText(stringBuilder.toString());
            }else {
                mReserveLog.setText("No Flight Reservation With that Name");
            }
        }else {
            mResult.setText("Flight Name Required");
        }
    }
    public void cancelReservation(View view){
        if (!mNameFlight.getText().toString().equals("")){
            final String nameChec = mNameFlight.getText().toString();
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
            final String userN = pref.getString("Username",null);
            mReserve= mReserveDao.getUserResrvation(userN,nameChec);
            if (mReserve != null){
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure you want to cancel Reservation")
                        .setMessage(mReserve.toString())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mChange = mFlightDao.getFlightByName(nameChec);
                                mChange.setSeats(mChange.getSeats() + mReserve.getSeats());
                                mFlightDao.update(mChange);
                                mPut = new Logs(userN,"Canceled Reservation \n "+ mReserve.toString());
                                mReserveDao.delete(mReserve);
                                mLogDao.insert(mPut);
                                Intent intent = new Intent(Cancellation.this,Flights.class);
                                startActivity(intent);
                                return;
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else {
                mResult.setText("No Flight Reservation With that Name");
            }
        }else {
            mResult.setText("Flight Name Required");
        }
    }
}
