package com.example.finalflight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.finalflight.DB.FlightDao;
import com.example.finalflight.DB.MainDatabase;
import com.example.finalflight.DB.UserDao;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button login;
    Button maintain;
    TextView signup;

    UserDao muserDelete;
    FlightDao mFlightDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = this.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        login = findViewById(R.id.login);
        maintain =findViewById(R.id.maintain);
        signup = findViewById(R.id.sign);
        muserDelete = Room.databaseBuilder(this,MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getUserDao();
        if (muserDelete.getUsers().isEmpty()){
            //Generating users based on empty user table ie first launch
            User admin = new User("admin2","admin2");
            User u1 = new User("alice5","csumb100");
            User u2 = new User("brian77","123ABC");
            User u3 = new User("chris21","CHRIS21");
            muserDelete.insert(u1);
            muserDelete.insert(u2);
            muserDelete.insert(u3);
            muserDelete.insert(admin);
        }
        if (muserDelete.getUser("admin2") != null){
            User getAdmin = muserDelete.getUser("admin2");
            if (!getAdmin.isAdmin()) {
                getAdmin.setAdmin(true);
                muserDelete.update(getAdmin);
            }
        }
        mFlightDao = Room.databaseBuilder(this,MainDatabase.class,MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getFlightDao();
        if (mFlightDao.getFlights().isEmpty()){
            //generate stock flights
            Date temp = new Date();
            temp.setMonth(12);
            temp.setYear(2019);
            temp.setDate(16);
            temp.setHours(10);
            temp.setMinutes(00);
            Flight one = new Flight(101,"Monterey","Los Angeles",10,150,temp);
            temp.setHours(13);
            temp.setMinutes(0);
            Flight two = new Flight(102,"Los Angeles","Monterey",10,150,temp);
            temp.setHours(11);
            Flight three = new Flight(201,"Monterey","Seattle",5,(float)200.5,temp);
            temp.setHours(15);
            Flight four = new Flight(205,"Monterey","Seattle",15,150,temp);
            temp.setHours(14);
            Flight five = new Flight(202,"Seattle","Monterey",5,(float)200.50,temp);
            mFlightDao.insert(one);
            mFlightDao.insert(two);
            mFlightDao.insert(three);
            mFlightDao.insert(four);
            mFlightDao.insert(five);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                return;
            }
        });
        maintain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,adminLogin.class);
                startActivity(intent);
                return;
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,signup.class);
                startActivity(intent);
                return;
            }
        });
    }
}
