package com.example.finalflight;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.finalflight.DB.LogsDao;
import com.example.finalflight.DB.MainDatabase;

import java.util.List;

public class AdminHome extends AppCompatActivity {
    TextView mMainLog;

    Button mAddFlight;

    List<Logs> mLogs;
    LogsDao mLogDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        mMainLog = (TextView) findViewById(R.id.mainLog);
        mAddFlight = (Button) findViewById(R.id.addFlight);
        mMainLog.setMovementMethod(new ScrollingMovementMethod());
        mLogDao = Room.databaseBuilder(this, MainDatabase.class, MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getLogsDao();
        refreshDisplay();
        mAddFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this,AddFlight.class);
                startActivity(intent);
                return;
            }
        });

    }
    private void refreshDisplay(){
        mLogs = mLogDao.getLogs();
        if (!mLogs.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for (Logs log : mLogs){
                stringBuilder.append(log);
                stringBuilder.append('\n');
                stringBuilder.append('\n');
            }
            mMainLog.setText(stringBuilder.toString());
        }else {
            mMainLog.setText("No records");
        }
    }
}
