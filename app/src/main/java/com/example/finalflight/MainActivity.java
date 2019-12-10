package com.example.finalflight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.finalflight.DB.MainDatabase;
import com.example.finalflight.DB.UserDao;

public class MainActivity extends AppCompatActivity {
    Button login;
    Button maintain;
    TextView signup;

    UserDao muserDelete;

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
