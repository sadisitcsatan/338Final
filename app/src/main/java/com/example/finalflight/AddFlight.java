package com.example.finalflight;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.finalflight.DB.FlightDao;
import com.example.finalflight.DB.LogsDao;
import com.example.finalflight.DB.MainDatabase;

import java.util.Date;

import dalvik.system.InMemoryDexClassLoader;

public class AddFlight extends AppCompatActivity {
    EditText mId;
    EditText mDep;
    EditText mAriv;
    EditText mCost;
    EditText mSeats;
    EditText mDay;
    EditText mMonth;
    EditText mYear;
    EditText mHour;
    EditText mMinute;

    TextView mRez;

    Button addFlight;

    FlightDao mFlightDao;
    LogsDao mLogsDao;

    Flight mFlight;
    Logs mLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
        mId =(EditText) findViewById(R.id.flightId);
        mDep = (EditText) findViewById(R.id.departureLocation);
        mAriv = (EditText) findViewById(R.id.arrivalLocation);
        mCost = (EditText) findViewById(R.id.cost);
        mSeats = (EditText) findViewById(R.id.seats);
        mDay =(EditText) findViewById(R.id.day);
        mMinute = (EditText) findViewById(R.id.minute);
        mMonth = (EditText) findViewById(R.id.month);
        mYear = (EditText) findViewById(R.id.year);
        mHour = (EditText) findViewById(R.id.hour);
        mRez = (TextView) findViewById(R.id.rez);
//        SharedPreferences pref = this.getSharedPreferences("MyPref",0);
//        String cuurrentUser = pref.getString("Username",null);
//        Toast.makeText(this,cuurrentUser,Toast.LENGTH_LONG).show();

        addFlight = (Button) findViewById(R.id.addFlight1);

        mLogsDao = Room.databaseBuilder(this,MainDatabase.class, MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getLogsDao();
        mFlightDao = Room.databaseBuilder(this, MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getFlightDao();
        addFlight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isValid()){
                    addFlight();
                }
            }
        });
        //Id must be available
        //none can be empty
    }

    private void addFlight(){
        int id = Integer.parseInt(mId.getText().toString());
        String dep = mDep.getText().toString();
        String ariv = mAriv.getText().toString();
        float cost = Float.parseFloat(mCost.getText().toString());
        int seats = Integer.parseInt(mSeats.getText().toString());
        int hour =Integer.parseInt(mHour.getText().toString());
        int day = Integer.parseInt(mDay.getText().toString());
        int month = Integer.parseInt(mMonth.getText().toString());
        month = month-1;
        int year = Integer.parseInt(mYear.getText().toString());
        int minute = Integer.parseInt(mMinute.getText().toString());
        Date cDate = new Date(year-1900,month,day,hour,minute);
        SharedPreferences pref = this.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor = pref.edit();
        String currentUser = pref.getString("Username",null);
        final Flight newFlight = new Flight(id,dep,ariv,seats,cost,cDate);
        final Logs loging = new Logs(currentUser,"Added Flight "+ newFlight.getFlightName());
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to Add")
                .setMessage(newFlight.toString())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLogsDao.insert(loging);
                        mFlightDao.insert(newFlight);
                        Toast.makeText(AddFlight.this,"Successfully added Flight",Toast.LENGTH_SHORT);
                        Intent intent = new Intent(AddFlight.this,AdminHome.class);
                        startActivity(intent);
                        return;
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private boolean isValid(){
        boolean res = true;
        if (mId.getText().toString().equals("")) {
            mRez.setText("Invalid Id");
            return false;
        }else if (mDep.getText().toString().equals("")) {
            mRez.setText("Invalid Departure");
            return false;
        }else if (mAriv.getText().toString().equals("")) {
            mRez.setText("Invalid Arrival");
            return false;
        }else if (mCost.getText().toString().equals("") || Float.valueOf(mCost.getText().toString()) <= 0.0) {
            mRez.setText("Invalid Cost");
            mCost.setText("");
            return false;
        }else if (mSeats.getText().toString().equals("") || Integer.parseInt(mSeats.getText().toString().trim()) <= 0){
            mRez.setText("Invalid Seats");
            mSeats.setText("");
            return false;
        }else if(mHour.getText().toString().equals("") || Integer.parseInt(mHour.getText().toString()) < 0 || Integer.parseInt(mHour.getText().toString().trim()) >= 24){
            mRez.setText("Invalid Hour");
            mHour.setText("");
            return false;
        }else if (mMinute.getText().toString().equals("")|| Integer.parseInt(mMinute.getText().toString()) < 0 || Integer.parseInt(mMinute.getText().toString()) >= 60){
            mRez.setText("Invalid Minute");
            mMinute.setText("");
            return false;
        }else if (mDay.getText().toString().equals("")|| Integer.parseInt(mDay.getText().toString()) <= 0 || Integer.parseInt(mDay.getText().toString()) > 31){
            mRez.setText("Invalid Day");
            mDay.setText("");
            return false;
        }else if (mMonth.getText().toString().equals("") || Integer.parseInt(mMonth.getText().toString()) <= 0 || Integer.parseInt(mMonth.getText().toString().trim()) > 12){
            mRez.setText("Invalid Month");
            mMonth.setText("");
            return false;
        }else if (mYear.getText().toString().equals("") || Integer.parseInt(mYear.getText().toString()) <= 2019){
            mRez.setText("Invalid Year");
            mYear.setText("");
            return false;
        }
//        else if (Integer.parseInt(mMonth.getText().toString()) <= 0 || Integer.parseInt(mMonth.getText().toString().trim()) > 12) {
//            mRez.setText("Invalid Month");
//            mMonth.setText("");
//            return false;
//        }else if(Integer.parseInt(mHour.getText().toString()) < 0 || Integer.parseInt(mHour.getText().toString().trim()) >= 24) {
//            mRez.setText("Invalid Hour");
//            mHour.setText("");
//            return false;
//        }else if (Integer.parseInt(mMinute.getText().toString()) < 0 || Integer.parseInt(mMinute.getText().toString()) >= 60) {
//            mRez.setText("Invalid Minute");
//            mMinute.setText("");
//            return false;
//        }else if (Integer.parseInt(mDay.getText().toString()) <= 0 || Integer.parseInt(mDay.getText().toString()) > 31) {
//            mRez.setText("Invalid Day");
//            mDay.setText("");
//            return false;
//        }
        int id = Integer.parseInt(mId.getText().toString());
        if (mFlightDao.getFlight(id) != null) {
            mRez.setText("Id taken");
            res = false;
        }

        return  res;
    }
}
