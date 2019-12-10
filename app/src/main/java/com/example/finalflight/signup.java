package com.example.finalflight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.finalflight.DB.LogsDao;
import com.example.finalflight.DB.MainDatabase;
import com.example.finalflight.DB.UserDao;


public class signup extends AppCompatActivity {
    EditText mUsername;
    EditText mPassword;
    TextView mResult;

    Button mSubmit;

    UserDao mUserDao;
    LogsDao mLogsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mUsername = findViewById(R.id.user);
        mPassword = findViewById(R.id.pass);
        mSubmit = findViewById(R.id.sign);
        mResult = findViewById(R.id.results);
        mLogsDao = Room.databaseBuilder(this,MainDatabase.class, MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getLogsDao();
        mUserDao = Room.databaseBuilder(this, MainDatabase.class, MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getUserDao();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid()){
                    submitUser();
                    Toast.makeText(signup.this,"Account Successfully Created",Toast.LENGTH_LONG);
                    Intent intent = new Intent(signup.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean valid(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        boolean result = true;
        if (username.equals("") || username.length() < 5){
            result = false;
            mResult.setText("Username is invalid");
        }else if (count(password)){
            result = false;
            mResult.setText("Password requires 3 letters and 3 numbers");
        }else if (mUserDao.getUser(username) != null){
            result = false;
            mResult.setText("Username already taken");
        }
        return result;

    }
    private void submitUser(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        Logs loging = new Logs(username,"Created Account");
        mLogsDao.insert(loging);
        User newUser =new User(username,password);
        mUserDao.insert(newUser);

    }
    private boolean count(String x) {
        char[] ch = x.toCharArray();
        int letter = 0;
        int space = 0;
        int num = 0;
        int other = 0;
        boolean res = true;
        for (int i = 0; i < x.length(); i++) {
            if (Character.isLetter(ch[i])) {
                letter++;
            } else if (Character.isDigit(ch[i])) {
                num++;
            } else if (Character.isSpaceChar(ch[i])) {
                space++;
            } else {
                other++;
            }

        }
        if (letter < 3 || num < 2) {
            res = false;
        } else if (space == 0) {
            res = false;
        }
        return res;
    }
}
